package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.RatingStandard;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村书记评级标准 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RatingStandardServiceTest {

    /** 村书记评级标准 service */
    @Autowired
    private RatingStandardService ratingStandardService;

    @Test
    public void saveTest() {
        RatingStandard ratingStandard = new RatingStandard();
        ratingStandard = ratingStandardService.save(ratingStandard);
        assertNotNull(ratingStandard.getId());
    }

}