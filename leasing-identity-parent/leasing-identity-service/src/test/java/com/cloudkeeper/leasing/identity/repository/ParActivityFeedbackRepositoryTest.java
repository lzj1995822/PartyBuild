package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ParActivityFeedback;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 移动端执行记录 repository 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParActivityFeedbackRepositoryTest {

    /** 移动端执行记录 repository */
    @Autowired
    private ParActivityFeedbackRepository parActivityFeedbackRepository;

    @Test
    public void saveTest() {
        ParActivityFeedback parActivityFeedback = new ParActivityFeedback();
        parActivityFeedback = parActivityFeedbackRepository.save(parActivityFeedback);
        assertNotNull(parActivityFeedback.getId());
    }

}