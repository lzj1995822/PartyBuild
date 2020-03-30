package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.KPIVillageStatistics;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村一级指标统计 service 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KPIVillageStatisticsServiceTest {

    /** 村一级指标统计 service */
    @Autowired
    private KPIVillageStatisticsService kPIVillageStatisticsService;

    @Test
    public void saveTest() {
        KPIVillageStatistics kPIVillageStatistics = new KPIVillageStatistics();
        kPIVillageStatistics = kPIVillageStatisticsService.save(kPIVillageStatistics);
        assertNotNull(kPIVillageStatistics.getId());
    }

}