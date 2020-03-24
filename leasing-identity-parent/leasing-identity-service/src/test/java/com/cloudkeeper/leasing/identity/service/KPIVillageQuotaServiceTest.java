package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.KPIVillageQuota;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村考核指标 service 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KPIVillageQuotaServiceTest {

    /** 村考核指标 service */
    @Autowired
    private KPIVillageQuotaService kPIVillageQuotaService;

    @Test
    public void saveTest() {
        KPIVillageQuota kPIVillageQuota = new KPIVillageQuota();
        kPIVillageQuota = kPIVillageQuotaService.save(kPIVillageQuota);
        assertNotNull(kPIVillageQuota.getId());
    }

}