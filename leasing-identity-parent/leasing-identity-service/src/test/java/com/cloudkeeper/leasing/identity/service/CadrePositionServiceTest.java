package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.CadrePosition;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 岗位管理 service 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CadrePositionServiceTest {

    /** 岗位管理 service */
    @Autowired
    private CadrePositionService cadrePositionService;

    @Test
    public void saveTest() {
        CadrePosition cadrePosition = new CadrePosition();
        cadrePosition = cadrePositionService.save(cadrePosition);
        assertNotNull(cadrePosition.getId());
    }

}