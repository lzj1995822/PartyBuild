package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParActivity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 活动 service 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ParActivityServiceTest {

    /** 活动 service */
    @Autowired
    private ParActivityService parActivityService;

    @Test
    public void saveTest() {
        ParActivity parActivity = new ParActivity();
        parActivity = parActivityService.save(parActivity);
        assertNotNull(parActivity.getId());
    }

}