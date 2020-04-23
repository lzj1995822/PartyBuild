package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.KPIStatistics;
import com.cloudkeeper.leasing.identity.repository.KPIStatisticsRepository;
import com.cloudkeeper.leasing.identity.service.KPIStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 双向印证 service
 * @author yujian
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KPIStatisticsServiceImpl extends BaseServiceImpl<KPIStatistics> implements KPIStatisticsService {

    /** 双向印证 repository */
    private final KPIStatisticsRepository kPIStatisticsRepository;

    @Override
    protected BaseRepository<KPIStatistics> getBaseRepository() {
        return kPIStatisticsRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("districtName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("villagePerformance", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("monitoringIndex", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("dvm", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("abilityJudgement", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("routine", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("comprehensiveEvaluation", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("comprehensiveEvaluationABC", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("satisfactionDegree", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtId", ExampleMatcher.GenericPropertyMatchers.startsWith());
    }

}