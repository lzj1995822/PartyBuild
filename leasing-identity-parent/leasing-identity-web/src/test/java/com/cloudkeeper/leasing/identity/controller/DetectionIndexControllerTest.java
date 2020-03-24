package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.DetectionIndex;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 监测指标 controller 测试
 * @author asher
 */
public class DetectionIndexControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        DetectionIndex detectionIndex = new DetectionIndex();
        HttpEntity<DetectionIndex> httpEntity = new HttpEntity<>(detectionIndex, httpHeaders);
        ParameterizedTypeReference<Result<DetectionIndex>> type = new ParameterizedTypeReference<Result<DetectionIndex>>() {};
        ResponseEntity<Result<DetectionIndex>> responseEntity = restTemplate.exchange("/detectionIndex/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}