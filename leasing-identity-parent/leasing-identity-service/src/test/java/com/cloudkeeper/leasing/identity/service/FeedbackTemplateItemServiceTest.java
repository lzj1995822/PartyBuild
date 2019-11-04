package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.FeedbackTemplateItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 反馈配置项 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FeedbackTemplateItemServiceTest {

    /** 反馈配置项 service */
    @Autowired
    private FeedbackTemplateItemService feedbackTemplateItemService;

    @Test
    public void saveTest() {
        FeedbackTemplateItem feedbackTemplateItem = new FeedbackTemplateItem();
        feedbackTemplateItem = feedbackTemplateItemService.save(feedbackTemplateItem);
        assertNotNull(feedbackTemplateItem.getId());
    }

}