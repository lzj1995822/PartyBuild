package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.FamilyWorkInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 专职村书记家庭工作情况 service 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FamilyWorkInfoServiceTest {

    /** 专职村书记家庭工作情况 service */
    @Autowired
    private FamilyWorkInfoService familyWorkInfoService;

    @Test
    public void saveTest() {
        FamilyWorkInfo familyWorkInfo = new FamilyWorkInfo();
        familyWorkInfo = familyWorkInfoService.save(familyWorkInfo);
        assertNotNull(familyWorkInfo.getId());
    }

}