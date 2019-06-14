package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ExaExamine;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 考核审核 service 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ExaExamineServiceTest {

    /** 考核审核 service */
    @Autowired
    private ExaExamineService exaExamineService;

    @Test
    public void saveTest() {
        ExaExamine exaExamine = new ExaExamine();
        exaExamine = exaExamineService.save(exaExamine);
        assertNotNull(exaExamine.getId());
    }

}