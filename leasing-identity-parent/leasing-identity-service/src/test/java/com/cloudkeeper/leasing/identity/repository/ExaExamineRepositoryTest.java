package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ExaExamine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 考核审核 repository 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExaExamineRepositoryTest {

    /** 考核审核 repository */
    @Autowired
    private ExaExamineRepository exaExamineRepository;

    @Test
    public void saveTest() {
        ExaExamine exaExamine = new ExaExamine();
        exaExamine = exaExamineRepository.save(exaExamine);
        assertNotNull(exaExamine.getId());
    }

}