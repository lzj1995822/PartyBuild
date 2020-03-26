package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.KPITownQuota;
import com.cloudkeeper.leasing.identity.repository.KPITownQuotaRepository;
import com.cloudkeeper.leasing.identity.service.KPITownQuotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 镇考核指标 service
 * @author yujian
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KPITownQuotaServiceImpl extends BaseServiceImpl<KPITownQuota> implements KPITownQuotaService {

    /** 镇考核指标 repository */
    private final KPITownQuotaRepository kPITownQuotaRepository;

    @Override
    protected BaseRepository<KPITownQuota> getBaseRepository() {
        return kPITownQuotaRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("districtName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("quotaName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("parentQuotaName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("score", ExampleMatcher.GenericPropertyMatchers.contains());
    }


    @Override
    public void deleteAllByDistrictIdAndParentQuotaId(String districtId, String parentQuotaId) {
        kPITownQuotaRepository.deleteAllByDistrictIdAndParentQuotaId(districtId,parentQuotaId);
    }
}