package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.TrainingInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 专职村书记培训情况 repository 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TrainingInfoRepositoryTest {

    /** 专职村书记培训情况 repository */
    @Autowired
    private TrainingInfoRepository trainingInfoRepository;

    @Test
    public void saveTest() {
        TrainingInfo trainingInfo = new TrainingInfo();
        trainingInfo = trainingInfoRepository.save(trainingInfo);
        assertNotNull(trainingInfo.getId());
    }

}