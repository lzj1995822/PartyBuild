package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParActivityPerform;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 任务执行记录 service 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ParActivityPerformServiceTest {

    /** 任务执行记录 service */
    @Autowired
    private ParActivityPerformService parActivityPerformService;

    @Test
    public void saveTest() {
        ParActivityPerform parActivityPerform = new ParActivityPerform();
        parActivityPerform = parActivityPerformService.save(parActivityPerform);
        assertNotNull(parActivityPerform.getId());
    }

}