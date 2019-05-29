package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.SysLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 系统日志 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SysLogRepositoryTest {

    /** 系统日志 repository */
    @Autowired
    private SysLogRepository sysLogRepository;

    @Test
    public void saveTest() {
        SysLog sysLog = new SysLog();
        sysLog = sysLogRepository.save(sysLog);
        assertNotNull(sysLog.getId());
    }

}