package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.SumPerHour;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 每小时人流量 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SumPerHourRepositoryTest {

    /** 每小时人流量 repository */
    @Autowired
    private SumPerHourRepository sumPerHourRepository;

    @Test
    public void saveTest() {
        SumPerHour sumPerHour = new SumPerHour();
        sumPerHour = sumPerHourRepository.save(sumPerHour);
        assertNotNull(sumPerHour.getId());
    }

}