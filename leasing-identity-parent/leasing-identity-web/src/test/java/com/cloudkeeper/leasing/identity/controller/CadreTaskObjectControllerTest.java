package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.CadreTaskObject;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 村书记模块发布任务对象记录 controller 测试
 * @author asher
 */
public class CadreTaskObjectControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        CadreTaskObject cadreTaskObject = new CadreTaskObject();
        HttpEntity<CadreTaskObject> httpEntity = new HttpEntity<>(cadreTaskObject, httpHeaders);
        ParameterizedTypeReference<Result<CadreTaskObject>> type = new ParameterizedTypeReference<Result<CadreTaskObject>>() {};
        ResponseEntity<Result<CadreTaskObject>> responseEntity = restTemplate.exchange("/cadreTaskObject/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}