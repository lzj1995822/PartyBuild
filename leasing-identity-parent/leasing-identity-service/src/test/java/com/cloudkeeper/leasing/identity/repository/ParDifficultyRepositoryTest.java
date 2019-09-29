package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ParDifficulty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 困难党员 repository 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParDifficultyRepositoryTest {

    /** 困难党员 repository */
    @Autowired
    private ParDifficultyRepository parDifficultyRepository;

    @Test
    public void saveTest() {
        ParDifficulty parDifficulty = new ParDifficulty();
        parDifficulty = parDifficultyRepository.save(parDifficulty);
        assertNotNull(parDifficulty.getId());
    }

}