package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.Reserve;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 后备人才 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ReserveServiceTest {

    /** 后备人才 service */
    @Autowired
    private ReserveService reserveService;

    @Test
    public void saveTest() {
        Reserve reserve = new Reserve();
        reserve = reserveService.save(reserve);
        assertNotNull(reserve.getId());
    }

}