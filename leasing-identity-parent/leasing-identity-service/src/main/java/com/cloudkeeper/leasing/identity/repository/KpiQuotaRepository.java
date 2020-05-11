package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.KpiQuota;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 村主任考核指标 repository
 * @author yujian
 */
@Repository
public interface KpiQuotaRepository extends BaseRepository<KpiQuota> {

    List<KpiQuota> findAllByParentQuotaIdOrderByQuotaIdAsc(String parentQuotaId);

    KpiQuota findByQuotaId(String quotaId);
}