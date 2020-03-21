package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.VillageCadresAnnex;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村主任信息附件 service 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class VillageCadresAnnexServiceTest {

    /** 村主任信息附件 service */
    @Autowired
    private VillageCadresAnnexService villageCadresAnnexService;

    @Test
    public void saveTest() {
        VillageCadresAnnex villageCadresAnnex = new VillageCadresAnnex();
        villageCadresAnnex = villageCadresAnnexService.save(villageCadresAnnex);
        assertNotNull(villageCadresAnnex.getId());
    }

}