package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.MessageCenter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 消息中心 service 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MessageCenterServiceTest {

    /** 消息中心 service */
    @Autowired
    private MessageCenterService messageCenterService;

    @Test
    public void saveTest() {
        MessageCenter messageCenter = new MessageCenter();
        messageCenter = messageCenterService.save(messageCenter);
        assertNotNull(messageCenter.getId());
    }

}