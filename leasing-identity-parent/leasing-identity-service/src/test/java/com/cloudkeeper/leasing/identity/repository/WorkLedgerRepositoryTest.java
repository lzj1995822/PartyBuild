package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.WorkLedger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 工作台账 repository 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WorkLedgerRepositoryTest {

    /** 工作台账 repository */
    @Autowired
    private WorkLedgerRepository workLedgerRepository;

    @Test
    public void saveTest() {
        WorkLedger workLedger = new WorkLedger();
        workLedger = workLedgerRepository.save(workLedger);
        assertNotNull(workLedger.getId());
    }

}