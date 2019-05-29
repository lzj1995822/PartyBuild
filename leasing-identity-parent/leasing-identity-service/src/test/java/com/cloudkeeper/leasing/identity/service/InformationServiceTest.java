package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.Information;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 消息通知 service 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class InformationServiceTest {

    /** 消息通知 service */
    @Autowired
    private InformationService informationService;

    @Test
    public void saveTest() {
        Information information = new Information();
        information = informationService.save(information);
        assertNotNull(information.getId());
    }

}