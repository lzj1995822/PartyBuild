package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.RewardInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 报酬情况 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RewardInfoServiceTest {

    /** 报酬情况 service */
    @Autowired
    private RewardInfoService rewardInfoService;

    @Test
    public void saveTest() {
        RewardInfo rewardInfo = new RewardInfo();
        rewardInfo = rewardInfoService.save(rewardInfo);
        assertNotNull(rewardInfo.getId());
    }

}