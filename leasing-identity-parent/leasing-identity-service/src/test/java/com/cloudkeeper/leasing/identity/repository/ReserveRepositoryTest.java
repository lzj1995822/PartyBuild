package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.Reserve;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 后备人才 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReserveRepositoryTest {

    /** 后备人才 repository */
    @Autowired
    private ReserveRepository reserveRepository;

    @Test
    public void saveTest() {
        Reserve reserve = new Reserve();
        reserve = reserveRepository.save(reserve);
        assertNotNull(reserve.getId());
    }

}