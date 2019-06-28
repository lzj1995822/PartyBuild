package com.cloudkeeper.leasing.identity.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

/**
 * EasyNVR service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EasyNVRServiceTest {

    /** 系统附件 service */
    @Autowired
    private EasyNVRService easyNVRService;

//    @Test
//    public void catchTest() {
//        String s = easyNVRService.catchPic("02987617140001550");
//        System.out.println(s);
//        assertNotNull(s);
//    }

}
