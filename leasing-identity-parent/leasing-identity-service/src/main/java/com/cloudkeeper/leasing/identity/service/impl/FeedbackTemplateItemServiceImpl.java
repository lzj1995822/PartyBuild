package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.FeedbackTemplateItem;
import com.cloudkeeper.leasing.identity.repository.FeedbackTemplateItemRepository;
import com.cloudkeeper.leasing.identity.service.FeedbackTemplateItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 反馈配置项 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FeedbackTemplateItemServiceImpl extends BaseServiceImpl<FeedbackTemplateItem> implements FeedbackTemplateItemService {

    /** 反馈配置项 repository */
    private final FeedbackTemplateItemRepository feedbackTemplateItemRepository;

    @Override
    protected BaseRepository<FeedbackTemplateItem> getBaseRepository() {
        return feedbackTemplateItemRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("code", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("templateId", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}