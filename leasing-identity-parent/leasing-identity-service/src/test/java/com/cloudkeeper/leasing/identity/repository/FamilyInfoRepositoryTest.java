package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.FamilyInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 专职村书记家庭情况 repository 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FamilyInfoRepositoryTest {

    /** 专职村书记家庭情况 repository */
    @Autowired
    private FamilyInfoRepository familyInfoRepository;

    @Test
    public void saveTest() {
        FamilyInfo familyInfo = new FamilyInfo();
        familyInfo = familyInfoRepository.save(familyInfo);
        assertNotNull(familyInfo.getId());
    }

}