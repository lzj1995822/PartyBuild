package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.SysClassProperty;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 类属性配置 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysClassPropertyServiceTest {

    /** 类属性配置 service */
    @Autowired
    private SysClassPropertyService sysClassPropertyService;

    @Test
    public void saveTest() {
        SysClassProperty sysClassProperty = new SysClassProperty();
        sysClassProperty = sysClassPropertyService.save(sysClassProperty);
        assertNotNull(sysClassProperty.getId());
    }

}