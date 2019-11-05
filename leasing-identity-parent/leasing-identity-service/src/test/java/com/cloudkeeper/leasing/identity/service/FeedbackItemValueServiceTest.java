package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.FeedbackItemValue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 反馈配置项值 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FeedbackItemValueServiceTest {

    /** 反馈配置项值 service */
    @Autowired
    private FeedbackItemValueService feedbackItemValueService;

    @Test
    public void saveTest() {
        FeedbackItemValue feedbackItemValue = new FeedbackItemValue();
        feedbackItemValue = feedbackItemValueService.save(feedbackItemValue);
        assertNotNull(feedbackItemValue.getId());
    }

}