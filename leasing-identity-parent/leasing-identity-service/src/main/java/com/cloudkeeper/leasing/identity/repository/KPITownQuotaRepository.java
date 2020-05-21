package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.KPITownQuota;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 镇考核指标 repository
 * @author yujian
 */
@Repository
public interface KPITownQuotaRepository extends BaseRepository<KPITownQuota> {


    void deleteAllByDistrictIdAndParentQuotaId(String districtId,String parentQuotaId);

    List<KPITownQuota> findAllByDistrictIdAndParentQuotaIdStartingWithAndTaskId(String districtId, String parentQuotaId, String taskId);

    List<KPITownQuota> findAllByParentQuotaId(String parentQuotaId);

    void deleteAllByIdIn(List<String> ids);

    List<KPITownQuota> findAllByQuotaName(String quotaName);

    List<KPITownQuota> findAllByTaskId(String taskId);
}
