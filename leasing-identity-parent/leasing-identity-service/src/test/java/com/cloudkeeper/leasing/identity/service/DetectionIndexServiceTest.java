package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.DetectionIndex;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 监测指标 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DetectionIndexServiceTest {

    /** 监测指标 service */
    @Autowired
    private DetectionIndexService detectionIndexService;

    @Test
    public void saveTest() {
        DetectionIndex detectionIndex = new DetectionIndex();
        detectionIndex = detectionIndexService.save(detectionIndex);
        assertNotNull(detectionIndex.getId());
    }

}