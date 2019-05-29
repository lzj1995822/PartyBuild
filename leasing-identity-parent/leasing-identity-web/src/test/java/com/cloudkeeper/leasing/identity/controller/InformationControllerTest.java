package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.Information;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 消息通知 controller 测试
 * @author cqh
 */
public class InformationControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        Information information = new Information();
        HttpEntity<Information> httpEntity = new HttpEntity<>(information, httpHeaders);
        ParameterizedTypeReference<Result<Information>> type = new ParameterizedTypeReference<Result<Information>>() {};
        ResponseEntity<Result<Information>> responseEntity = restTemplate.exchange("/information/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}