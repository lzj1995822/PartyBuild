package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.VillageCadresTerm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村主任任期信息 repository 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VillageCadresTermRepositoryTest {

    /** 村主任任期信息 repository */
    @Autowired
    private VillageCadresTermRepository villageCadresTermRepository;

    @Test
    public void saveTest() {
        VillageCadresTerm villageCadresTerm = new VillageCadresTerm();
        villageCadresTerm = villageCadresTermRepository.save(villageCadresTerm);
        assertNotNull(villageCadresTerm.getId());
    }

}