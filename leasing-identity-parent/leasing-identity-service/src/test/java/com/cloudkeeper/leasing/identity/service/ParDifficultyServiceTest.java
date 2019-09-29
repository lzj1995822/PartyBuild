package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParDifficulty;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 困难党员 service 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ParDifficultyServiceTest {

    /** 困难党员 service */
    @Autowired
    private ParDifficultyService parDifficultyService;

    @Test
    public void saveTest() {
        ParDifficulty parDifficulty = new ParDifficulty();
        parDifficulty = parDifficultyService.save(parDifficulty);
        assertNotNull(parDifficulty.getId());
    }

}