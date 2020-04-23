package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.KPIVillageQuota;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 村考核指标 repository
 * @author yujian
 */
@Repository
public interface KPIVillageQuotaRepository extends BaseRepository<KPIVillageQuota> {

    void deleteAllByParentDistrictIdAndParentQuotaId(String parentDistrictId,String parentQuotaId);

}
