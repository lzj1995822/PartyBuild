package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParActivityObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 任务对象 service 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ParActivityObjectServiceTest {

    /** 任务对象 service */
    @Autowired
    private ParActivityObjectService parActivityObjectService;

    @Test
    public void saveTest() {
        ParActivityObject parActivityObject = new ParActivityObject();
        parActivityObject = parActivityObjectService.save(parActivityObject);
        assertNotNull(parActivityObject.getId());
    }

}