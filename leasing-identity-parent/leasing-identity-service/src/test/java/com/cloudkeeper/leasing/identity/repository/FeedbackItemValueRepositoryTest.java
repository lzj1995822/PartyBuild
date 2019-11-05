package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.FeedbackItemValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 反馈配置项值 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FeedbackItemValueRepositoryTest {

    /** 反馈配置项值 repository */
    @Autowired
    private FeedbackItemValueRepository feedbackItemValueRepository;

    @Test
    public void saveTest() {
        FeedbackItemValue feedbackItemValue = new FeedbackItemValue();
        feedbackItemValue = feedbackItemValueRepository.save(feedbackItemValue);
        assertNotNull(feedbackItemValue.getId());
    }

}