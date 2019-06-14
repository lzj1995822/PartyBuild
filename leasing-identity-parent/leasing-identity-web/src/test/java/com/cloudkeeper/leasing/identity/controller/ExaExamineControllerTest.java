package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.ExaExamine;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 考核审核 controller 测试
 * @author lxw
 */
public class ExaExamineControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        ExaExamine exaExamine = new ExaExamine();
        HttpEntity<ExaExamine> httpEntity = new HttpEntity<>(exaExamine, httpHeaders);
        ParameterizedTypeReference<Result<ExaExamine>> type = new ParameterizedTypeReference<Result<ExaExamine>>() {};
        ResponseEntity<Result<ExaExamine>> responseEntity = restTemplate.exchange("/exaExamine/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}