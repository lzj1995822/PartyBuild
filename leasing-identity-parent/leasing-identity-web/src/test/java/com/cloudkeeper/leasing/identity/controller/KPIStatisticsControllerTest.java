package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.KPIStatistics;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 双向印证 controller 测试
 * @author yujian
 */
public class KPIStatisticsControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        KPIStatistics kPIStatistics = new KPIStatistics();
        HttpEntity<KPIStatistics> httpEntity = new HttpEntity<>(kPIStatistics, httpHeaders);
        ParameterizedTypeReference<Result<KPIStatistics>> type = new ParameterizedTypeReference<Result<KPIStatistics>>() {};
        ResponseEntity<Result<KPIStatistics>> responseEntity = restTemplate.exchange("/kPIStatistics/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}