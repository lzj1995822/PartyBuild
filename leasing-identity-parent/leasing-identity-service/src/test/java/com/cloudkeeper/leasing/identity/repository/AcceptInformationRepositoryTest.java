package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.AcceptInformation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 接收公告 repository 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AcceptInformationRepositoryTest {

    /** 接收公告 repository */
    @Autowired
    private AcceptInformationRepository acceptInformationRepository;

    @Test
    public void saveTest() {
        AcceptInformation acceptInformation = new AcceptInformation();
        acceptInformation = acceptInformationRepository.save(acceptInformation);
        assertNotNull(acceptInformation.getId());
    }

}