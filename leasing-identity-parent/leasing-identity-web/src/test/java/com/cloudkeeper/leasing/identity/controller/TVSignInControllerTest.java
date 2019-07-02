package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.TVSignIn;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 远教视频签到记录 controller 测试
 * @author cqh
 */
public class TVSignInControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        TVSignIn tVSignIn = new TVSignIn();
        HttpEntity<TVSignIn> httpEntity = new HttpEntity<>(tVSignIn, httpHeaders);
        ParameterizedTypeReference<Result<TVSignIn>> type = new ParameterizedTypeReference<Result<TVSignIn>>() {};
        ResponseEntity<Result<TVSignIn>> responseEntity = restTemplate.exchange("/tVSignIn/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}