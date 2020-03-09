package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.RewardInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 报酬情况 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RewardInfoRepositoryTest {

    /** 报酬情况 repository */
    @Autowired
    private RewardInfoRepository rewardInfoRepository;

    @Test
    public void saveTest() {
        RewardInfo rewardInfo = new RewardInfo();
        rewardInfo = rewardInfoRepository.save(rewardInfo);
        assertNotNull(rewardInfo.getId());
    }

}