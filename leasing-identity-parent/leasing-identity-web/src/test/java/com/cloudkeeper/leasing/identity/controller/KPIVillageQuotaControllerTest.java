package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.KPIVillageQuota;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 村考核指标 controller 测试
 * @author yujian
 */
public class KPIVillageQuotaControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        KPIVillageQuota kPIVillageQuota = new KPIVillageQuota();
        HttpEntity<KPIVillageQuota> httpEntity = new HttpEntity<>(kPIVillageQuota, httpHeaders);
        ParameterizedTypeReference<Result<KPIVillageQuota>> type = new ParameterizedTypeReference<Result<KPIVillageQuota>>() {};
        ResponseEntity<Result<KPIVillageQuota>> responseEntity = restTemplate.exchange("/kPIVillageQuota/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}