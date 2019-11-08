package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ActivityOfficeProgress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 机关活动进度记录 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ActivityOfficeProgressRepositoryTest {

    /** 机关活动进度记录 repository */
    @Autowired
    private ActivityOfficeProgressRepository activityOfficeProgressRepository;

    @Test
    public void saveTest() {
        ActivityOfficeProgress activityOfficeProgress = new ActivityOfficeProgress();
        activityOfficeProgress = activityOfficeProgressRepository.save(activityOfficeProgress);
        assertNotNull(activityOfficeProgress.getId());
    }

}