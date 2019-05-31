package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.WorkLedger;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 工作台账 service 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WorkLedgerServiceTest {

    /** 工作台账 service */
    @Autowired
    private WorkLedgerService workLedgerService;

    @Test
    public void saveTest() {
        WorkLedger workLedger = new WorkLedger();
        workLedger = workLedgerService.save(workLedger);
        assertNotNull(workLedger.getId());
    }

}