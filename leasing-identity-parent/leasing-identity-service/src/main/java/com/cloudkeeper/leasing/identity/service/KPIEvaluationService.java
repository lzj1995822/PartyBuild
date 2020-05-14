package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.KPIEvaluation;
import com.cloudkeeper.leasing.base.service.BaseService;

import java.util.List;

/**
 * 综合评议 service
 * @author yujian
 */
public interface KPIEvaluationService extends BaseService<KPIEvaluation> {

    void deleteByTypeAndTaskIdAndDistrictId(String type,String taskId, String districtId);

    List<KPIEvaluation> findAllByVillageQuotaId(String villageQuotaId);
}
