package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.VillageCadres;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村干部管理 repository 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VillageCadresRepositoryTest {

    /** 村干部管理 repository */
    @Autowired
    private VillageCadresRepository villageCadresRepository;

    @Test
    public void saveTest() {
        VillageCadres villageCadres = new VillageCadres();
        villageCadres = villageCadresRepository.save(villageCadres);
        assertNotNull(villageCadres.getId());
    }

}