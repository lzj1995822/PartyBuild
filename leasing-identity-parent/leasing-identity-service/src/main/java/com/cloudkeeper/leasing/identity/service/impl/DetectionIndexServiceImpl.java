package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.DetectionIndex;
import com.cloudkeeper.leasing.identity.repository.DetectionIndexRepository;
import com.cloudkeeper.leasing.identity.service.DetectionIndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 监测指标 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DetectionIndexServiceImpl extends BaseServiceImpl<DetectionIndex> implements DetectionIndexService {

    /** 监测指标 repository */
    private final DetectionIndexRepository detectionIndexRepository;

    @Override
    protected BaseRepository<DetectionIndex> getBaseRepository() {
        return detectionIndexRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("currentYearEconomicIncome", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("lastYearEconomicIncome", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("incomeSupportDoc", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("partyActivityFinishRatio", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("cadrePosts", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("cadreBelowThirtyFive", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("cadreBetweenThirtyFiveToFifty", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("cadreOverFifty", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("cleanFinishRatio", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("petitionLetterAmount", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("skipPetitionLetterAmount", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("defusePetitionLetterAmount", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("population", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("petitionLetterSupportDoc", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("projectAmount", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("projectAmountSupportDoc", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("hasPass", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("avgScore", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("environmentSupportDoc", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtId",  ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("taskId",  ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("taskName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtName",  ExampleMatcher.GenericPropertyMatchers.contains());
    }

}