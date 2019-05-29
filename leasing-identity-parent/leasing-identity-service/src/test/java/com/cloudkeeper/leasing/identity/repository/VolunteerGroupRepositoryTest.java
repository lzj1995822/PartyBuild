package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.VolunteerGroup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 志愿者服务队伍 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VolunteerGroupRepositoryTest {

    /** 志愿者服务队伍 repository */
    @Autowired
    private VolunteerGroupRepository volunteerGroupRepository;

    @Test
    public void saveTest() {
        VolunteerGroup volunteerGroup = new VolunteerGroup();
        volunteerGroup = volunteerGroupRepository.save(volunteerGroup);
        assertNotNull(volunteerGroup.getId());
    }

}