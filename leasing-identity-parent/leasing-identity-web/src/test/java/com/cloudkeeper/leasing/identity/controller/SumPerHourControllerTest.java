package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.SumPerHour;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 每小时人流量 controller 测试
 * @author asher
 */
public class SumPerHourControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        SumPerHour sumPerHour = new SumPerHour();
        HttpEntity<SumPerHour> httpEntity = new HttpEntity<>(sumPerHour, httpHeaders);
        ParameterizedTypeReference<Result<SumPerHour>> type = new ParameterizedTypeReference<Result<SumPerHour>>() {};
        ResponseEntity<Result<SumPerHour>> responseEntity = restTemplate.exchange("/sumPerHour/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}