package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.ParActivityObject;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 任务对象 controller 测试
 * @author lxw
 */
public class ParActivityObjectControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        ParActivityObject parActivityObject = new ParActivityObject();
        HttpEntity<ParActivityObject> httpEntity = new HttpEntity<>(parActivityObject, httpHeaders);
        ParameterizedTypeReference<Result<ParActivityObject>> type = new ParameterizedTypeReference<Result<ParActivityObject>>() {};
        ResponseEntity<Result<ParActivityObject>> responseEntity = restTemplate.exchange("/parActivityObject/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}