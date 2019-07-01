package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.TVSignIn;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 远教视频签到记录 service 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TVSignInServiceTest {

    /** 远教视频签到记录 service */
    @Autowired
    private TVSignInService tVSignInService;

    @Test
    public void saveTest() {
        TVSignIn tVSignIn = new TVSignIn();
        tVSignIn = tVSignInService.save(tVSignIn);
        assertNotNull(tVSignIn.getId());
    }

}