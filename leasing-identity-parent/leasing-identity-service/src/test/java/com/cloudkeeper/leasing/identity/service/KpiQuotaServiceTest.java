package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.KpiQuota;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村主任考核指标 service 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KpiQuotaServiceTest {

    /** 村主任考核指标 service */
    @Autowired
    private KpiQuotaService kpiQuotaService;

    @Test
    public void saveTest() {
        KpiQuota kpiQuota = new KpiQuota();
        kpiQuota = kpiQuotaService.save(kpiQuota);
        assertNotNull(kpiQuota.getId());
    }

}