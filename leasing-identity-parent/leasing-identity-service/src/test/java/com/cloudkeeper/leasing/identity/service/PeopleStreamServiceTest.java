package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.PeopleStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 阵地人流量 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PeopleStreamServiceTest {

    /** 阵地人流量 service */
    @Autowired
    private PeopleStreamService peopleStreamService;

    @Test
    public void saveTest() {
        PeopleStream peopleStream = new PeopleStream();
        peopleStream = peopleStreamService.save(peopleStream);
        assertNotNull(peopleStream.getId());
    }

}