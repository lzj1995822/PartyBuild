package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.CadreTask;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 村书记模块任务 controller 测试
 * @author asher
 */
public class CadreTaskControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        CadreTask cadreTask = new CadreTask();
        HttpEntity<CadreTask> httpEntity = new HttpEntity<>(cadreTask, httpHeaders);
        ParameterizedTypeReference<Result<CadreTask>> type = new ParameterizedTypeReference<Result<CadreTask>>() {};
        ResponseEntity<Result<CadreTask>> responseEntity = restTemplate.exchange("/cadreTask/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}