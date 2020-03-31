package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.CadreTask;
import com.cloudkeeper.leasing.identity.domain.CadreTaskObject;
import com.cloudkeeper.leasing.identity.domain.InformationAudit;
import com.cloudkeeper.leasing.identity.repository.CadreTaskObjectRepository;
import com.cloudkeeper.leasing.identity.repository.CadreTaskRepository;
import com.cloudkeeper.leasing.identity.service.CadreTaskObjectService;
import com.cloudkeeper.leasing.identity.service.InformationAuditService;
import com.cloudkeeper.leasing.identity.vo.FinishRatioVO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 村书记模块发布任务对象记录 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadreTaskObjectServiceImpl extends BaseServiceImpl<CadreTaskObject> implements CadreTaskObjectService {

    /** 村书记模块发布任务对象记录 repository */
    private final CadreTaskObjectRepository cadreTaskObjectRepository;

    private final InformationAuditService informationAuditService;

    private final CadreTaskRepository cadreTaskRepository;

    private static final String LEVEL_JUDGE_TASK = "职级评定";

    private final StringRedisTemplate template;
    @Override
    protected BaseRepository<CadreTaskObject> getBaseRepository() {
        return cadreTaskObjectRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("objectType", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("note", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("townName", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public CadreTaskObject updateStatusByTaskIdAndObjectId(String taskId, String objectId) {
        CadreTaskObject cadreTaskObject = cadreTaskObjectRepository.findByTaskIdAndAndObjectId(taskId, objectId);
        if (cadreTaskObject == null) {
            return null;
        }
        // 更新镇进度
        String sql = "select count(case WHEN vc.state = '3' THEN 1 ELSE NULL END) as finish, count(case WHEN vc.state != '3' THEN 1 ELSE NULL END) as unfinish, count(*) as total FROM village_cadres vc WHERE vc.hasRetire = '0' and  vc.parentDistrictId = '" + objectId + "'";
        FinishRatioVO finishRatioVO = findBySql(FinishRatioVO.class, sql);
        String finishSql = "SELECT count(1) as finish from Information_Audit audit JOIN village_cadres cadres on audit.villageId = cadres.id and audit.status = '3' and cadres.hasRetire = '0' and  cadres.parentDistrictId = '" + objectId + "' and audit.taskId = '" + taskId + "'";
        FinishRatioVO finishVO = findBySql(FinishRatioVO.class, finishSql);
        BigDecimal divide = new BigDecimal(finishVO.getFinish()).divide(new BigDecimal(finishRatioVO.getTotal()), 2, BigDecimal.ROUND_FLOOR);
        if (divide.equals(BigDecimal.ONE)) {
            cadreTaskObject.setStatus("0");
        }
        cadreTaskObject.setCurrentPercent(divide.toString());

        // 更新总进度
        String totalSql = "select count(case WHEN vc.state = '3' THEN 1 ELSE NULL END) as finish, count(case WHEN vc.state != '3' THEN 1 ELSE NULL END) as unfinish, count(*) as total FROM village_cadres vc WHERE vc.hasRetire = '0' and  vc.parentDistrictId like '01%'";
        String totalFinishSql = "SELECT count(1) as finish from Information_Audit audit JOIN village_cadres cadres on audit.villageId = cadres.id and audit.status = '3' and cadres.parentDistrictId  like '01%' and cadres.hasRetire = '0' and audit.taskId = '" + taskId + "'";
        FinishRatioVO finishRatioVO1 = findBySql(FinishRatioVO.class, totalSql);
        FinishRatioVO totalFinishVO = findBySql(FinishRatioVO.class, totalFinishSql);
        BigDecimal divide1 = new BigDecimal(totalFinishVO.getFinish()).divide(new BigDecimal(finishRatioVO1.getTotal()), 2, BigDecimal.ROUND_FLOOR);
        CadreTask cadreTask = cadreTaskObject.getCadreTask();
        if (cadreTask == null) {
            return null;
        }
        cadreTask.setCurrentPercent(divide1.toString());

        return save(cadreTaskObject);
    }

    @Override
    public void deleteByTaskId(String taskId) {
        cadreTaskObjectRepository.deleteAllByTaskId(taskId);
    }

    @Override
    public List<CadreTaskObject> findAllByTaskId(String taskId) {
        return cadreTaskObjectRepository.findAllByTaskId(taskId);
    }

    @Override
    public CadreTaskObject submit(String taskObjectId, String isSuccess, String auditor, String auditorAdvice) {
        Optional<CadreTaskObject> byId = findOptionalById(taskObjectId);
        if (!byId.isPresent()) {
            return null;
        }
        CadreTaskObject cadreTaskObject = byId.get();
        Integer status = Integer.valueOf(cadreTaskObject.getStatus());
        if ("SUCCESS".equals(isSuccess)) {
            status++;
        } else {
            status--;
        }
        if (status == 2) {
            updateTotalProgress(cadreTaskObject.getCadreTask());
            if ("考核内容实施".equals(cadreTaskObject.getCadreTask().getType())){
                //如果是考核内容实施完成去检测是否所有项都完成,使用redis消息队列
                //template.convertAndSend("checkHasCompleted",cadreTaskObject.getTaskId());
            }
        }
        cadreTaskObject.setLastestAuditor(auditor);
        cadreTaskObject.setLastestAdvice(auditorAdvice);
        cadreTaskObject.setStatus(String.valueOf(status));
        InformationAudit informationAudit = new InformationAudit();
        informationAudit.setVillageId(cadreTaskObject.getObjectId());
        informationAudit.setAuditor(auditor);
        informationAudit.setAuditAdvice(auditorAdvice);
        informationAudit.setTaskId(cadreTaskObject.getTaskId());
        informationAudit.setProcessType(cadreTaskObject.getCadreTask().getType());
        informationAuditService.save(informationAudit);
        return save(cadreTaskObject);
    }

    private void updateTotalProgress(@NonNull CadreTask cadreTask) {
        String finalStatus = "2";
        if (LEVEL_JUDGE_TASK.equals(cadreTask.getType())) {
            finalStatus = "4";
        }
        String sql = "select count(case when cto.status = '" +finalStatus+ "' then 1 else null end) as finish, " +
                "count(case when cto.status != '" +finalStatus+ "' then 1 else null end) as unfinish, count(*) as total" +
                " from cadres_task_object cto where cto.taskId = '" + cadreTask.getId() + "'";
        FinishRatioVO bySql = findBySql(FinishRatioVO.class, sql);
        if (bySql != null) {
            BigDecimal divide1 = new BigDecimal(bySql.getFinish()).divide(new BigDecimal(bySql.getTotal()), 2, BigDecimal.ROUND_FLOOR);
            cadreTask.setCurrentPercent(divide1.toString());
            cadreTaskRepository.save(cadreTask);
        }
    }


}
