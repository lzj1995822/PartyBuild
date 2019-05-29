package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 系统日志 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysLogServiceTest {

    /** 系统日志 service */
    @Autowired
    private SysLogService sysLogService;

    @Test
    public void saveTest() {
        SysLog sysLog = new SysLog();
        sysLog = sysLogService.save(sysLog);
        assertNotNull(sysLog.getId());
    }

}