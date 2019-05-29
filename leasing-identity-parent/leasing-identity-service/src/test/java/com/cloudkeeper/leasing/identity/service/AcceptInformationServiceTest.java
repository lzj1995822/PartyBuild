package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.AcceptInformation;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 接收公告 service 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AcceptInformationServiceTest {

    /** 接收公告 service */
    @Autowired
    private AcceptInformationService acceptInformationService;

    @Test
    public void saveTest() {
        AcceptInformation acceptInformation = new AcceptInformation();
        acceptInformation = acceptInformationService.save(acceptInformation);
        assertNotNull(acceptInformation.getId());
    }

}