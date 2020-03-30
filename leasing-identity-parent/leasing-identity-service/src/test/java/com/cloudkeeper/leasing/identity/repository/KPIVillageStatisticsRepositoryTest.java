package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.KPIVillageStatistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村一级指标统计 repository 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class KPIVillageStatisticsRepositoryTest {

    /** 村一级指标统计 repository */
    @Autowired
    private KPIVillageStatisticsRepository kPIVillageStatisticsRepository;

    @Test
    public void saveTest() {
        KPIVillageStatistics kPIVillageStatistics = new KPIVillageStatistics();
        kPIVillageStatistics = kPIVillageStatisticsRepository.save(kPIVillageStatistics);
        assertNotNull(kPIVillageStatistics.getId());
    }

}