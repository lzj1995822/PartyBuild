package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.PositionInformation;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 阵地信息 service 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PositionInformationServiceTest {

    /** 阵地信息 service */
    @Autowired
    private PositionInformationService positionInformationService;

    @Test
    public void saveTest() {
        PositionInformation positionInformation = new PositionInformation();
        positionInformation = positionInformationService.save(positionInformation);
        assertNotNull(positionInformation.getId());
    }

}