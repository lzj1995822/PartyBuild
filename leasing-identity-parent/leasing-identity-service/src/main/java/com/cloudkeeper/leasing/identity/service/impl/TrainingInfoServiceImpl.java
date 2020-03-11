package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.TrainingInfo;
import com.cloudkeeper.leasing.identity.repository.TrainingInfoRepository;
import com.cloudkeeper.leasing.identity.service.TrainingInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 专职村书记培训情况 service
 * @author yujian
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TrainingInfoServiceImpl extends BaseServiceImpl<TrainingInfo> implements TrainingInfoService {

    /** 专职村书记培训情况 repository */
    private final TrainingInfoRepository trainingInfoRepository;

    @Override
    protected BaseRepository<TrainingInfo> getBaseRepository() {
        return trainingInfoRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("trainingName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("trainingType", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("content", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("supportDoc", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("trainingResults", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("trainingYear", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}