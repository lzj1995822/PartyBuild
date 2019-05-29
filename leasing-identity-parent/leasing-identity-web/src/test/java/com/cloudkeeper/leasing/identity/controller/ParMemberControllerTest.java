package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.ParMember;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 党员管理 controller 测试
 * @author cqh
 */
public class ParMemberControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        ParMember parMember = new ParMember();
        HttpEntity<ParMember> httpEntity = new HttpEntity<>(parMember, httpHeaders);
        ParameterizedTypeReference<Result<ParMember>> type = new ParameterizedTypeReference<Result<ParMember>>() {};
        ResponseEntity<Result<ParMember>> responseEntity = restTemplate.exchange("/parMember/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}