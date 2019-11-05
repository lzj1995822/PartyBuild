package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.FeedbackItemValue;
import com.cloudkeeper.leasing.identity.repository.FeedbackItemValueRepository;
import com.cloudkeeper.leasing.identity.service.FeedbackItemValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 反馈配置项值 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FeedbackItemValueServiceImpl extends BaseServiceImpl<FeedbackItemValue> implements FeedbackItemValueService {

    /** 反馈配置项值 repository */
    private final FeedbackItemValueRepository feedbackItemValueRepository;

    @Override
    protected BaseRepository<FeedbackItemValue> getBaseRepository() {
        return feedbackItemValueRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("value", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}