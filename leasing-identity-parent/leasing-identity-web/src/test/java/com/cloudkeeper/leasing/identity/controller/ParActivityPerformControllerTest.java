package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.ParActivityPerform;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 任务执行记录 controller 测试
 * @author lxw
 */
public class ParActivityPerformControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        ParActivityPerform parActivityPerform = new ParActivityPerform();
        HttpEntity<ParActivityPerform> httpEntity = new HttpEntity<>(parActivityPerform, httpHeaders);
        ParameterizedTypeReference<Result<ParActivityPerform>> type = new ParameterizedTypeReference<Result<ParActivityPerform>>() {};
        ResponseEntity<Result<ParActivityPerform>> responseEntity = restTemplate.exchange("/parActivityPerform/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}