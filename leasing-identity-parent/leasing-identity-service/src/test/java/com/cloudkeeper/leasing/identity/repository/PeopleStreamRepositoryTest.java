package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.PeopleStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 阵地人流量 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PeopleStreamRepositoryTest {

    /** 阵地人流量 repository */
    @Autowired
    private PeopleStreamRepository peopleStreamRepository;

    @Test
    public void saveTest() {
        PeopleStream peopleStream = new PeopleStream();
        peopleStream = peopleStreamRepository.save(peopleStream);
        assertNotNull(peopleStream.getId());
    }

}