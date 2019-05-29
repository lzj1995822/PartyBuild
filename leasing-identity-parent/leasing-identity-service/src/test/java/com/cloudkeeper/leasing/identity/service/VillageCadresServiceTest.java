package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.VillageCadres;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村干部管理 service 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class VillageCadresServiceTest {

    /** 村干部管理 service */
    @Autowired
    private VillageCadresService villageCadresService;

    @Test
    public void saveTest() {
        VillageCadres villageCadres = new VillageCadres();
        villageCadres = villageCadresService.save(villageCadres);
        assertNotNull(villageCadres.getId());
    }

}