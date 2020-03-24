package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.KPITownQuota;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 镇考核指标 service 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KPITownQuotaServiceTest {

    /** 镇考核指标 service */
    @Autowired
    private KPITownQuotaService kPITownQuotaService;

    @Test
    public void saveTest() {
        KPITownQuota kPITownQuota = new KPITownQuota();
        kPITownQuota = kPITownQuotaService.save(kPITownQuota);
        assertNotNull(kPITownQuota.getId());
    }

}