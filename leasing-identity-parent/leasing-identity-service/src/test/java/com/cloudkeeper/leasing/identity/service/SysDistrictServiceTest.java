package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 组织 service 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysDistrictServiceTest {

    /** 组织 service */
    @Autowired
    private SysDistrictService sysDistrictService;

    @Test
    public void saveTest() {
        SysDistrict sysDistrict = new SysDistrict();
        sysDistrict = sysDistrictService.save(sysDistrict);
        assertNotNull(sysDistrict.getId());
    }

}