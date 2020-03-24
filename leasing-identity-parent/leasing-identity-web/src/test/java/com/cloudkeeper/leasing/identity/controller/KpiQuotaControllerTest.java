package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.KpiQuota;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 村主任考核指标 controller 测试
 * @author yujian
 */
public class KpiQuotaControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        KpiQuota kpiQuota = new KpiQuota();
        HttpEntity<KpiQuota> httpEntity = new HttpEntity<>(kpiQuota, httpHeaders);
        ParameterizedTypeReference<Result<KpiQuota>> type = new ParameterizedTypeReference<Result<KpiQuota>>() {};
        ResponseEntity<Result<KpiQuota>> responseEntity = restTemplate.exchange("/kpiQuota/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}