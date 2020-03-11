package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.CadreTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村书记模块任务 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CadreTaskRepositoryTest {

    /** 村书记模块任务 repository */
    @Autowired
    private CadreTaskRepository cadreTaskRepository;

    @Test
    public void saveTest() {
        CadreTask cadreTask = new CadreTask();
        cadreTask = cadreTaskRepository.save(cadreTask);
        assertNotNull(cadreTask.getId());
    }

}