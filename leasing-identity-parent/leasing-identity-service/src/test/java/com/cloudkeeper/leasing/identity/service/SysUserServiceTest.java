package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 系统用户 service 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysUserServiceTest {

    /** 系统用户 service */
    @Autowired
    private SysUserService sysUserService;

    @Test
    public void saveTest() {
        SysUser sysUser = new SysUser();
        sysUser = sysUserService.save(sysUser);
        assertNotNull(sysUser.getId());
    }

}