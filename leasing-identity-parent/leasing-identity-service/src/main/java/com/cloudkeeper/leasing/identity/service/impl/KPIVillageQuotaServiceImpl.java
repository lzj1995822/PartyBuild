package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.KPIVillageQuota;
import com.cloudkeeper.leasing.identity.repository.KPIVillageQuotaRepository;
import com.cloudkeeper.leasing.identity.service.KPIVillageQuotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 村考核指标 service
 * @author yujian
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KPIVillageQuotaServiceImpl extends BaseServiceImpl<KPIVillageQuota> implements KPIVillageQuotaService {

    /** 村考核指标 repository */
    private final KPIVillageQuotaRepository kPIVillageQuotaRepository;

    @Override
    protected BaseRepository<KPIVillageQuota> getBaseRepository() {
        return kPIVillageQuotaRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("weight", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtName", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}