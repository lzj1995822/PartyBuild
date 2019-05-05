package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.ParActivity;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 活动 controller 测试
 * @author lxw
 */
public class ParActivityControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        ParActivity parActivity = new ParActivity();
        HttpEntity<ParActivity> httpEntity = new HttpEntity<>(parActivity, httpHeaders);
        ParameterizedTypeReference<Result<ParActivity>> type = new ParameterizedTypeReference<Result<ParActivity>>() {};
        ResponseEntity<Result<ParActivity>> responseEntity = restTemplate.exchange("/parActivity/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}