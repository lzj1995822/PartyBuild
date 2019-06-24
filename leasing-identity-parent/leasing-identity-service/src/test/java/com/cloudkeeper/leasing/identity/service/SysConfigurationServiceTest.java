package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.SysConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 系统属性配置 service 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysConfigurationServiceTest {

    /** 系统属性配置 service */
    @Autowired
    private SysConfigurationService sysConfigurationService;

    @Test
    public void saveTest() {
        SysConfiguration sysConfiguration = new SysConfiguration();
        sysConfiguration = sysConfigurationService.save(sysConfiguration);
        assertNotNull(sysConfiguration.getId());
    }

}