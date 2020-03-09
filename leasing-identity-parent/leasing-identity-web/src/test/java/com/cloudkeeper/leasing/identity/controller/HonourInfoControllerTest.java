package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.HonourInfo;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 表彰情况 controller 测试
 * @author asher
 */
public class HonourInfoControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        HonourInfo honourInfo = new HonourInfo();
        HttpEntity<HonourInfo> httpEntity = new HttpEntity<>(honourInfo, httpHeaders);
        ParameterizedTypeReference<Result<HonourInfo>> type = new ParameterizedTypeReference<Result<HonourInfo>>() {};
        ResponseEntity<Result<HonourInfo>> responseEntity = restTemplate.exchange("/honourInfo/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}