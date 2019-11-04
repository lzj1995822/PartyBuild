package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.FeedbackTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 反馈配置模板 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FeedbackTemplateRepositoryTest {

    /** 反馈配置模板 repository */
    @Autowired
    private FeedbackTemplateRepository feedbackTemplateRepository;

    @Test
    public void saveTest() {
        FeedbackTemplate feedbackTemplate = new FeedbackTemplate();
        feedbackTemplate = feedbackTemplateRepository.save(feedbackTemplate);
        assertNotNull(feedbackTemplate.getId());
    }

}