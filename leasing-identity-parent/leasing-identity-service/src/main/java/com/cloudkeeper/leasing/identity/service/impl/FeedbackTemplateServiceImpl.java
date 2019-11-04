package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.FeedbackTemplate;
import com.cloudkeeper.leasing.identity.repository.FeedbackTemplateRepository;
import com.cloudkeeper.leasing.identity.service.FeedbackTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 反馈配置模板 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FeedbackTemplateServiceImpl extends BaseServiceImpl<FeedbackTemplate> implements FeedbackTemplateService {

    /** 反馈配置模板 repository */
    private final FeedbackTemplateRepository feedbackTemplateRepository;

    @Override
    protected BaseRepository<FeedbackTemplate> getBaseRepository() {
        return feedbackTemplateRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}