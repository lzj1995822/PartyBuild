package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.Volunteer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 志愿者 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class VolunteerServiceTest {

    /** 志愿者 service */
    @Autowired
    private VolunteerService volunteerService;

    @Test
    public void saveTest() {
        Volunteer volunteer = new Volunteer();
        volunteer = volunteerService.save(volunteer);
        assertNotNull(volunteer.getId());
    }

}