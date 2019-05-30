package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParRepresentative;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 党代表 service 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ParRepresentativeServiceTest {

    /** 党代表 service */
    @Autowired
    private ParRepresentativeService parRepresentativeService;

    @Test
    public void saveTest() {
        ParRepresentative parRepresentative = new ParRepresentative();
        parRepresentative = parRepresentativeService.save(parRepresentative);
        assertNotNull(parRepresentative.getId());
    }

}