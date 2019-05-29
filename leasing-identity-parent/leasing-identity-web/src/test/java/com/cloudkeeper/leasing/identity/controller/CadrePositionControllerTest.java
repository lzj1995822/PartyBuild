package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.CadrePosition;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 岗位管理 controller 测试
 * @author cqh
 */
public class CadrePositionControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        CadrePosition cadrePosition = new CadrePosition();
        HttpEntity<CadrePosition> httpEntity = new HttpEntity<>(cadrePosition, httpHeaders);
        ParameterizedTypeReference<Result<CadrePosition>> type = new ParameterizedTypeReference<Result<CadrePosition>>() {};
        ResponseEntity<Result<CadrePosition>> responseEntity = restTemplate.exchange("/cadrePosition/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}