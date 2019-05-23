package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 组织 repository 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SysDistrictRepositoryTest {

    /** 组织 repository */
    @Autowired
    private SysDistrictRepository sysDistrictRepository;

    @Test
    public void saveTest() {
        SysDistrict sysDistrict = new SysDistrict();
        sysDistrict = sysDistrictRepository.save(sysDistrict);
        assertNotNull(sysDistrict.getId());
    }

}