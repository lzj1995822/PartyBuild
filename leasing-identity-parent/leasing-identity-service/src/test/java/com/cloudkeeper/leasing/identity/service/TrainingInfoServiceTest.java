package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.TrainingInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 专职村书记培训情况 service 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TrainingInfoServiceTest {

    /** 专职村书记培训情况 service */
    @Autowired
    private TrainingInfoService trainingInfoService;

    @Test
    public void saveTest() {
        TrainingInfo trainingInfo = new TrainingInfo();
        trainingInfo = trainingInfoService.save(trainingInfo);
        assertNotNull(trainingInfo.getId());
    }

}