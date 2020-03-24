package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.KpiQuota;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 村主任考核指标 repository 测试
 * @author yujian
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class KpiQuotaRepositoryTest {

    /** 村主任考核指标 repository */
    @Autowired
    private KpiQuotaRepository kpiQuotaRepository;

    @Test
    public void saveTest() {
        KpiQuota kpiQuota = new KpiQuota();
        kpiQuota = kpiQuotaRepository.save(kpiQuota);
        assertNotNull(kpiQuota.getId());
    }

}