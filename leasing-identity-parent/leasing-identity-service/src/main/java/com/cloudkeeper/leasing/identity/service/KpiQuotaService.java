package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.KpiQuota;
import com.cloudkeeper.leasing.base.service.BaseService;

import java.util.List;

/**
 * 村主任考核指标 service
 * @author yujian
 */
public interface KpiQuotaService extends BaseService<KpiQuota> {
    
    List<KpiQuota> findAllByParentQuotaIdOrderByQuotaIdAsc(String parentQuotaId);
}