package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.KPIEvaluation;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 综合评议 service 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KPIEvaluationServiceTest {

    /** 综合评议 service */
    @Autowired
    private KPIEvaluationService kPIEvaluationService;

    @Test
    public void saveTest() {
        KPIEvaluation kPIEvaluation = new KPIEvaluation();
        kPIEvaluation = kPIEvaluationService.save(kPIEvaluation);
        assertNotNull(kPIEvaluation.getId());
    }

}