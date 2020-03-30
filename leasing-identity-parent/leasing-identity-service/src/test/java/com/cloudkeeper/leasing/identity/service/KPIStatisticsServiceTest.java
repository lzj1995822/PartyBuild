package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.KPIStatistics;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 双向印证 service 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KPIStatisticsServiceTest {

    /** 双向印证 service */
    @Autowired
    private KPIStatisticsService kPIStatisticsService;

    @Test
    public void saveTest() {
        KPIStatistics kPIStatistics = new KPIStatistics();
        kPIStatistics = kPIStatisticsService.save(kPIStatistics);
        assertNotNull(kPIStatistics.getId());
    }

}