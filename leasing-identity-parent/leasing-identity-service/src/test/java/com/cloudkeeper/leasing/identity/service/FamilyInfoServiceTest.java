package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.FamilyInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 专职村书记家庭情况 service 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FamilyInfoServiceTest {

    /** 专职村书记家庭情况 service */
    @Autowired
    private FamilyInfoService familyInfoService;

    @Test
    public void saveTest() {
        FamilyInfo familyInfo = new FamilyInfo();
        familyInfo = familyInfoService.save(familyInfo);
        assertNotNull(familyInfo.getId());
    }

}