package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ParRepresentative;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 党代表 repository 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParRepresentativeRepositoryTest {

    /** 党代表 repository */
    @Autowired
    private ParRepresentativeRepository parRepresentativeRepository;

    @Test
    public void saveTest() {
        ParRepresentative parRepresentative = new ParRepresentative();
        parRepresentative = parRepresentativeRepository.save(parRepresentative);
        assertNotNull(parRepresentative.getId());
    }

}