package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.ParActivityExamine;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 活动考核记录 controller 测试
 * @author lxw
 */
public class ParActivityExamineControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        ParActivityExamine parActivityExamine = new ParActivityExamine();
        HttpEntity<ParActivityExamine> httpEntity = new HttpEntity<>(parActivityExamine, httpHeaders);
        ParameterizedTypeReference<Result<ParActivityExamine>> type = new ParameterizedTypeReference<Result<ParActivityExamine>>() {};
        ResponseEntity<Result<ParActivityExamine>> responseEntity = restTemplate.exchange("/parActivityExamine/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}