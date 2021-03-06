package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ExaScore;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 考核积分 service 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ExaScoreServiceTest {

    /** 考核积分 service */
    @Autowired
    private ExaScoreService exaScoreService;

    @Test
    public void saveTest() {
        ExaScore exaScore = new ExaScore();
        exaScore = exaScoreService.save(exaScore);
        assertNotNull(exaScore.getId());
    }

}