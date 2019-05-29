package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ParActivityPerform;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 任务执行记录 repository 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParActivityPerformRepositoryTest {

    /** 任务执行记录 repository */
    @Autowired
    private ParActivityPerformRepository parActivityPerformRepository;

    @Test
    public void saveTest() {
        ParActivityPerform parActivityPerform = new ParActivityPerform();
        parActivityPerform = parActivityPerformRepository.save(parActivityPerform);
        assertNotNull(parActivityPerform.getId());
    }

}