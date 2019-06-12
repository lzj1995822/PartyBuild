package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParActivityFeedback;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 移动端执行记录 service 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ParActivityFeedbackServiceTest {

    /** 移动端执行记录 service */
    @Autowired
    private ParActivityFeedbackService parActivityFeedbackService;

    @Test
    public void saveTest() {
        ParActivityFeedback parActivityFeedback = new ParActivityFeedback();
        parActivityFeedback = parActivityFeedbackService.save(parActivityFeedback);
        assertNotNull(parActivityFeedback.getId());
    }

}