package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.DetectionIndex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 监测指标 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DetectionIndexRepositoryTest {

    /** 监测指标 repository */
    @Autowired
    private DetectionIndexRepository detectionIndexRepository;

    @Test
    public void saveTest() {
        DetectionIndex detectionIndex = new DetectionIndex();
        detectionIndex = detectionIndexRepository.save(detectionIndex);
        assertNotNull(detectionIndex.getId());
    }

}