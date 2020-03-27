package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.KPIEvaluation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 综合评议 repository 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class KPIEvaluationRepositoryTest {

    /** 综合评议 repository */
    @Autowired
    private KPIEvaluationRepository kPIEvaluationRepository;

    @Test
    public void saveTest() {
        KPIEvaluation kPIEvaluation = new KPIEvaluation();
        kPIEvaluation = kPIEvaluationRepository.save(kPIEvaluation);
        assertNotNull(kPIEvaluation.getId());
    }

}