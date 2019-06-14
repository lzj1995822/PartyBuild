package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ExaScore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 考核积分 repository 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExaScoreRepositoryTest {

    /** 考核积分 repository */
    @Autowired
    private ExaScoreRepository exaScoreRepository;

    @Test
    public void saveTest() {
        ExaScore exaScore = new ExaScore();
        exaScore = exaScoreRepository.save(exaScore);
        assertNotNull(exaScore.getId());
    }

}