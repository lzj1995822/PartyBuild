package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.CadreTaskObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村书记模块发布任务对象记录 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CadreTaskObjectServiceTest {

    /** 村书记模块发布任务对象记录 service */
    @Autowired
    private CadreTaskObjectService cadreTaskObjectService;

    @Test
    public void saveTest() {
        CadreTaskObject cadreTaskObject = new CadreTaskObject();
        cadreTaskObject = cadreTaskObjectService.save(cadreTaskObject);
        assertNotNull(cadreTaskObject.getId());
    }

}