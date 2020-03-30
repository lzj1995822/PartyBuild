package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.KPIVillageStatistics;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 村一级指标统计 controller 测试
 * @author yujian
 */
public class KPIVillageStatisticsControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        KPIVillageStatistics kPIVillageStatistics = new KPIVillageStatistics();
        HttpEntity<KPIVillageStatistics> httpEntity = new HttpEntity<>(kPIVillageStatistics, httpHeaders);
        ParameterizedTypeReference<Result<KPIVillageStatistics>> type = new ParameterizedTypeReference<Result<KPIVillageStatistics>>() {};
        ResponseEntity<Result<KPIVillageStatistics>> responseEntity = restTemplate.exchange("/kPIVillageStatistics/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}