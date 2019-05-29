package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.Volunteer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 志愿者 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VolunteerRepositoryTest {

    /** 志愿者 repository */
    @Autowired
    private VolunteerRepository volunteerRepository;

    @Test
    public void saveTest() {
        Volunteer volunteer = new Volunteer();
        volunteer = volunteerRepository.save(volunteer);
        assertNotNull(volunteer.getId());
    }

}