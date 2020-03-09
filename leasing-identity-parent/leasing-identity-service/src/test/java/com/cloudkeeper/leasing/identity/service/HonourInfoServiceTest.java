package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.HonourInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 表彰情况 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HonourInfoServiceTest {

    /** 表彰情况 service */
    @Autowired
    private HonourInfoService honourInfoService;

    @Test
    public void saveTest() {
        HonourInfo honourInfo = new HonourInfo();
        honourInfo = honourInfoService.save(honourInfo);
        assertNotNull(honourInfo.getId());
    }

}