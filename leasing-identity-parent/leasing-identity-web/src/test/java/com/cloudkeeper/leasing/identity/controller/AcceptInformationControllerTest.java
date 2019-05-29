package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.AcceptInformation;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 接收公告 controller 测试
 * @author cqh
 */
public class AcceptInformationControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        AcceptInformation acceptInformation = new AcceptInformation();
        HttpEntity<AcceptInformation> httpEntity = new HttpEntity<>(acceptInformation, httpHeaders);
        ParameterizedTypeReference<Result<AcceptInformation>> type = new ParameterizedTypeReference<Result<AcceptInformation>>() {};
        ResponseEntity<Result<AcceptInformation>> responseEntity = restTemplate.exchange("/acceptInformation/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}