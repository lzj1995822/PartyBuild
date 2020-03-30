package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.KPIStatistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 双向印证 repository 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class KPIStatisticsRepositoryTest {

    /** 双向印证 repository */
    @Autowired
    private KPIStatisticsRepository kPIStatisticsRepository;

    @Test
    public void saveTest() {
        KPIStatistics kPIStatistics = new KPIStatistics();
        kPIStatistics = kPIStatisticsRepository.save(kPIStatistics);
        assertNotNull(kPIStatistics.getId());
    }

}