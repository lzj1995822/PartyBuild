package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.ParCamera;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 监控信息 controller 测试
 * @author cqh
 */
public class ParCameraControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        ParCamera parCamera = new ParCamera();
        HttpEntity<ParCamera> httpEntity = new HttpEntity<>(parCamera, httpHeaders);
        ParameterizedTypeReference<Result<ParCamera>> type = new ParameterizedTypeReference<Result<ParCamera>>() {};
        ResponseEntity<Result<ParCamera>> responseEntity = restTemplate.exchange("/parCamera/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}