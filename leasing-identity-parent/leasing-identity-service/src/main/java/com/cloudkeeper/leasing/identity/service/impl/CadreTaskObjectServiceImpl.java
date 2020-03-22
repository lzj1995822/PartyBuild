package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.CadreTask;
import com.cloudkeeper.leasing.identity.domain.CadreTaskObject;
import com.cloudkeeper.leasing.identity.repository.CadreTaskObjectRepository;
import com.cloudkeeper.leasing.identity.service.CadreTaskObjectService;
import com.cloudkeeper.leasing.identity.vo.FinishRatioVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 村书记模块发布任务对象记录 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadreTaskObjectServiceImpl extends BaseServiceImpl<CadreTaskObject> implements CadreTaskObjectService {

    /** 村书记模块发布任务对象记录 repository */
    private final CadreTaskObjectRepository cadreTaskObjectRepository;

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
        String sql = "select count(case WHEN vc.state = '3' THEN 1 ELSE NULL END) as finish, count(case WHEN vc.state != '3' THEN 1 ELSE NULL END) as unfinish, count(*) as total FROM village_cadres vc WHERE vc.parentDistrictId = '" + objectId + "'";
        FinishRatioVO finishRatioVO = findBySql(FinishRatioVO.class, sql);
        BigDecimal divide = new BigDecimal(finishRatioVO.getFinish()).divide(new BigDecimal(finishRatioVO.getTotal()), 2, BigDecimal.ROUND_FLOOR);
        if (divide.equals(BigDecimal.ONE)) {
            cadreTaskObject.setStatus("0");
        }
        cadreTaskObject.setCurrentPercent(divide.toString());

        // 更新总进度
        String totalSql = "select count(case WHEN vc.state = '3' THEN 1 ELSE NULL END) as finish, count(case WHEN vc.state != '3' THEN 1 ELSE NULL END) as unfinish, count(*) as total FROM village_cadres vc WHERE vc.parentDistrictId like '01%'";
        FinishRatioVO finishRatioVO1 = findBySql(FinishRatioVO.class, totalSql);
        BigDecimal divide1 = new BigDecimal(finishRatioVO1.getFinish()).divide(new BigDecimal(finishRatioVO1.getTotal()), 2, BigDecimal.ROUND_FLOOR);
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
}
