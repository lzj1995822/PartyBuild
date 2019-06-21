package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.MessageCenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 消息中心 repository 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MessageCenterRepositoryTest {

    /** 消息中心 repository */
    @Autowired
    private MessageCenterRepository messageCenterRepository;

    @Test
    public void saveTest() {
        MessageCenter messageCenter = new MessageCenter();
        messageCenter = messageCenterRepository.save(messageCenter);
        assertNotNull(messageCenter.getId());
    }

}