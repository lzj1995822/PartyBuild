package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.KpiQuota;
import com.cloudkeeper.leasing.identity.repository.KpiQuotaRepository;
import com.cloudkeeper.leasing.identity.service.KpiQuotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * 村主任考核指标 service
 * @author yujian
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KpiQuotaServiceImpl extends BaseServiceImpl<KpiQuota> implements KpiQuotaService {

    /** 村主任考核指标 repository */
    private final KpiQuotaRepository kpiQuotaRepository;

    @Override
    protected BaseRepository<KpiQuota> getBaseRepository() {
        return kpiQuotaRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("quotaName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("quotaLevel", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("quotaYear", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("quotaMakeDepartId", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("parentQuotaId", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public List<KpiQuota> findAllByParentQuotaIdOrderByQuotaIdAsc(String parentQuotaId) {
        return kpiQuotaRepository.findAllByParentQuotaIdOrderByQuotaIdAsc(parentQuotaId);
    }

    @Override
    public KpiQuota findByQuotaId(String quotaId) {
        return kpiQuotaRepository.findByQuotaId(quotaId);
    }

    @Override
    public void deleteAllByQuotaYear(@Nonnull String quotaYear) {
        kpiQuotaRepository.deleteAllByQuotaYear(quotaYear);
    }
}