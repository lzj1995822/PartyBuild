package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.PositionInformation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 阵地信息 repository 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PositionInformationRepositoryTest {

    /** 阵地信息 repository */
    @Autowired
    private PositionInformationRepository positionInformationRepository;

    @Test
    public void saveTest() {
        PositionInformation positionInformation = new PositionInformation();
        positionInformation = positionInformationRepository.save(positionInformation);
        assertNotNull(positionInformation.getId());
    }

}