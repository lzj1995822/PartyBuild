package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.SumPerHour;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 每小时人流量 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SumPerHourServiceTest {

    /** 每小时人流量 service */
    @Autowired
    private SumPerHourService sumPerHourService;

    @Test
    public void saveTest() {
        SumPerHour sumPerHour = new SumPerHour();
        sumPerHour = sumPerHourService.save(sumPerHour);
        assertNotNull(sumPerHour.getId());
    }

}