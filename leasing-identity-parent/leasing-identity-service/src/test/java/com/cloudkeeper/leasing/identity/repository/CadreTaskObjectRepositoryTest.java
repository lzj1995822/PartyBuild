package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.CadreTaskObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村书记模块发布任务对象记录 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CadreTaskObjectRepositoryTest {

    /** 村书记模块发布任务对象记录 repository */
    @Autowired
    private CadreTaskObjectRepository cadreTaskObjectRepository;

    @Test
    public void saveTest() {
        CadreTaskObject cadreTaskObject = new CadreTaskObject();
        cadreTaskObject = cadreTaskObjectRepository.save(cadreTaskObject);
        assertNotNull(cadreTaskObject.getId());
    }

}