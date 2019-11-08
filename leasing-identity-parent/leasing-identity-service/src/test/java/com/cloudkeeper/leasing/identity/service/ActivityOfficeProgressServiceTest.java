package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ActivityOfficeProgress;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 机关活动进度记录 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ActivityOfficeProgressServiceTest {

    /** 机关活动进度记录 service */
    @Autowired
    private ActivityOfficeProgressService activityOfficeProgressService;

    @Test
    public void saveTest() {
        ActivityOfficeProgress activityOfficeProgress = new ActivityOfficeProgress();
        activityOfficeProgress = activityOfficeProgressService.save(activityOfficeProgress);
        assertNotNull(activityOfficeProgress.getId());
    }

}