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
import com.cloudkeeper.leasing.identity.vo.CadreTaskObjectVO;
import com.cloudkeeper.leasing.identity.vo.FinishRatioVO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .withMatcher("townName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("taskId", ExampleMatcher.GenericPropertyMatchers.contains());
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
        return cadreTaskObjectRepository.findAllByTaskIdOrderByObjectIdAsc(taskId);
    }

    @Override
    @Transactional
    public CadreTaskObjectVO submit(String taskObjectId, String isSuccess, String auditor, String auditorAdvice) {
        Optional<CadreTaskObject> byId = findOptionalById(taskObjectId);
        if (!byId.isPresent()) {
            return null;
        }
        CadreTaskObject cadreTaskObject = byId.get();
        Integer status = Integer.valueOf(cadreTaskObject.getStatus());
        if ("SUCCESS".equals(isSuccess)) {
            status++;
            cadreTaskObject.setIsRejected("0");
        } else {
            status--;
            cadreTaskObject.setIsRejected("1");
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
        CadreTaskObject save = save(cadreTaskObject);
        CadreTaskObjectVO convert = save.convert(CadreTaskObjectVO.class);
        // 更新进度并将是否可以生成考核结果标志位返回
        if (status == 2) {
            convert.setGenerateResultEnable(updateTotalProgress(cadreTaskObject.getCadreTask()));
        }
        return convert;
    }

    // 返回是否都已经完成
    private String updateTotalProgress(@NonNull CadreTask cadreTask) {
        String finalStatus = "2";
        if (LEVEL_JUDGE_TASK.equals(cadreTask.getType())) {
            finalStatus = "4";
        }
        String sql = "select count(case when cto.status = '" +finalStatus+ "' then 1 else null end) as finish, " +
                "count(case when cto.status != '" +finalStatus+ "' then 1 else null end) as unfinish, count(*) as total" +
                " from cadres_task_object cto where cto.taskId = '" + cadreTask.getId() + "'";
        FinishRatioVO bySql = findBySql(FinishRatioVO.class, sql);
        String isAllFinish = "0";
        if (bySql != null) {
            BigDecimal divide1 = new BigDecimal(bySql.getFinish()).divide(new BigDecimal(bySql.getTotal()), 2, BigDecimal.ROUND_FLOOR);
            cadreTask.setCurrentPercent(divide1.toString());
            if (divide1.intValue() == 1) {
                isAllFinish = "1";
            }
            cadreTask.setGenerateResultEnable(isAllFinish);
            cadreTaskRepository.save(cadreTask);
        }
        return isAllFinish;
    }


}
