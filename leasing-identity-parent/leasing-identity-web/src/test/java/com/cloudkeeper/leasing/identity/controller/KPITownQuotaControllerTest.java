package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.KPITownQuota;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 镇考核指标 controller 测试
 * @author yujian
 */
public class KPITownQuotaControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        KPITownQuota kPITownQuota = new KPITownQuota();
        HttpEntity<KPITownQuota> httpEntity = new HttpEntity<>(kPITownQuota, httpHeaders);
        ParameterizedTypeReference<Result<KPITownQuota>> type = new ParameterizedTypeReference<Result<KPITownQuota>>() {};
        ResponseEntity<Result<KPITownQuota>> responseEntity = restTemplate.exchange("/kPITownQuota/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}