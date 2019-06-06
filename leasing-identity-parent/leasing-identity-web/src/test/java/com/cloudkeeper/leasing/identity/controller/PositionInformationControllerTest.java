package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.PositionInformation;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 阵地信息 controller 测试
 * @author cqh
 */
public class PositionInformationControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        PositionInformation positionInformation = new PositionInformation();
        HttpEntity<PositionInformation> httpEntity = new HttpEntity<>(positionInformation, httpHeaders);
        ParameterizedTypeReference<Result<PositionInformation>> type = new ParameterizedTypeReference<Result<PositionInformation>>() {};
        ResponseEntity<Result<PositionInformation>> responseEntity = restTemplate.exchange("/positionInformation/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}