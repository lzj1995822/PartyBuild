package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.HonourInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 表彰情况 repository 测试
 * @author asher
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HonourInfoRepositoryTest {

    /** 表彰情况 repository */
    @Autowired
    private HonourInfoRepository honourInfoRepository;

    @Test
    public void saveTest() {
        HonourInfo honourInfo = new HonourInfo();
        honourInfo = honourInfoRepository.save(honourInfo);
        assertNotNull(honourInfo.getId());
    }

}