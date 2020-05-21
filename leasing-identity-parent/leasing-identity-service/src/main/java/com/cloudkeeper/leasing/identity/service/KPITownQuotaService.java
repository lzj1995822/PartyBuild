package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.KPITownQuota;
import com.cloudkeeper.leasing.base.service.BaseService;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 镇考核指标 service
 * @author yujian
 */
public interface KPITownQuotaService extends BaseService<KPITownQuota> {


    void deleteAllByDistrictIdAndParentQuotaId(String districtId,String parentQuotaId);

    List<KPITownQuota> findAllByDistrictIdAndParentQuotaIdStartingWithAndTaskId(String districtId, String parentQuotaId, String taskId);

    List<KPITownQuota> findAllByParentQuotaId(String parentQuotaId);

    void deleteAllByIdIn(List<String> ids);

    List<KPITownQuota> findAllByQuotaName(String quotaName);

    List<KPITownQuota> findAllByTaskId(@Nonnull String taskId);
}
