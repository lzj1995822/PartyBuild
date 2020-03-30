package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.KPIVillageStatistics;
import com.cloudkeeper.leasing.identity.repository.KPIVillageStatisticsRepository;
import com.cloudkeeper.leasing.identity.service.KPIVillageStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 村一级指标统计 service
 * @author yujian
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KPIVillageStatisticsServiceImpl extends BaseServiceImpl<KPIVillageStatistics> implements KPIVillageStatisticsService {

    /** 村一级指标统计 repository */
    private final KPIVillageStatisticsRepository kPIVillageStatisticsRepository;

    @Override
    protected BaseRepository<KPIVillageStatistics> getBaseRepository() {
        return kPIVillageStatisticsRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("districtName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("parentDistrictName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("quotaName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("parentQuotaName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("score", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("weight", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("quarter", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public void addStatistics(List<KPIVillageStatistics> kpiVillageStatistics) {
        kPIVillageStatisticsRepository.saveAll(kpiVillageStatistics);
        kPIVillageStatisticsRepository.flush();
    }
}
