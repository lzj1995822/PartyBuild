package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.CadreTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村书记模块任务 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CadreTaskServiceTest {

    /** 村书记模块任务 service */
    @Autowired
    private CadreTaskService cadreTaskService;

    @Test
    public void saveTest() {
        CadreTask cadreTask = new CadreTask();
        cadreTask = cadreTaskService.save(cadreTask);
        assertNotNull(cadreTask.getId());
    }

}