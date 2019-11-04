package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.FeedbackTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 反馈配置模板 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FeedbackTemplateServiceTest {

    /** 反馈配置模板 service */
    @Autowired
    private FeedbackTemplateService feedbackTemplateService;

    @Test
    public void saveTest() {
        FeedbackTemplate feedbackTemplate = new FeedbackTemplate();
        feedbackTemplate = feedbackTemplateService.save(feedbackTemplate);
        assertNotNull(feedbackTemplate.getId());
    }

}