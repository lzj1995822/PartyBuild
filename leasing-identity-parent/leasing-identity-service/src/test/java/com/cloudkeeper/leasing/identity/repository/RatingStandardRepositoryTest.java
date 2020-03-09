package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.RatingStandard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村书记评级标准 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RatingStandardRepositoryTest {

    /** 村书记评级标准 repository */
    @Autowired
    private RatingStandardRepository ratingStandardRepository;

    @Test
    public void saveTest() {
        RatingStandard ratingStandard = new RatingStandard();
        ratingStandard = ratingStandardRepository.save(ratingStandard);
        assertNotNull(ratingStandard.getId());
    }

}