package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.VolunteerGroup;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 志愿者服务队伍 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class VolunteerGroupServiceTest {

    /** 志愿者服务队伍 service */
    @Autowired
    private VolunteerGroupService volunteerGroupService;

    @Test
    public void saveTest() {
        VolunteerGroup volunteerGroup = new VolunteerGroup();
        volunteerGroup = volunteerGroupService.save(volunteerGroup);
        assertNotNull(volunteerGroup.getId());
    }

}