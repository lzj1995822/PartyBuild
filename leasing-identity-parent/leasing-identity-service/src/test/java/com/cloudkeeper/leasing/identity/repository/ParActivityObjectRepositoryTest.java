package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ParActivityObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 任务对象 repository 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParActivityObjectRepositoryTest {

    /** 任务对象 repository */
    @Autowired
    private ParActivityObjectRepository parActivityObjectRepository;

    @Test
    public void saveTest() {
        ParActivityObject parActivityObject = new ParActivityObject();
        parActivityObject = parActivityObjectRepository.save(parActivityObject);
        assertNotNull(parActivityObject.getId());
    }

}