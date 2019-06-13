package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParActivityExamine;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 活动考核记录 service 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ParActivityExamineServiceTest {

    /** 活动考核记录 service */
    @Autowired
    private ParActivityExamineService parActivityExamineService;

    @Test
    public void saveTest() {
        ParActivityExamine parActivityExamine = new ParActivityExamine();
        parActivityExamine = parActivityExamineService.save(parActivityExamine);
        assertNotNull(parActivityExamine.getId());
    }

}