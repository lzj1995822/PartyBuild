package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.FeedbackTemplateItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 反馈配置项 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FeedbackTemplateItemRepositoryTest {

    /** 反馈配置项 repository */
    @Autowired
    private FeedbackTemplateItemRepository feedbackTemplateItemRepository;

    @Test
    public void saveTest() {
        FeedbackTemplateItem feedbackTemplateItem = new FeedbackTemplateItem();
        feedbackTemplateItem = feedbackTemplateItemRepository.save(feedbackTemplateItem);
        assertNotNull(feedbackTemplateItem.getId());
    }

}