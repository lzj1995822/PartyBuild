package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.KPIVillageQuota;
import com.cloudkeeper.leasing.base.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 村考核指标 service
 * @author yujian
 */
public interface KPIVillageQuotaService extends BaseService<KPIVillageQuota> {

    void deleteAllByParentDistrictIdAndParentQuotaId(String parentDistrictId,String parentQuotaId);

    List<Map<String, Object>> buildCommonWorkData(String districtId, String taskId);

    List<Map<String, Object>> buildCommonData(String districtId, String taskId, String rootQuotaId);

    List<Map<String, Object>> buildWatchQuotaData(String districtId, String taskId);

    List<Map<String, Object>> buildCommentQuotaData(String districtId, String taskId);
}
