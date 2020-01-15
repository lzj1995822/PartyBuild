package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.AuthorizationToken;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 访问权限 controller 测试
 * @author cqh
 */
public class AuthorizationTokenControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        AuthorizationToken authorizationToken = new AuthorizationToken();
        HttpEntity<AuthorizationToken> httpEntity = new HttpEntity<>(authorizationToken, httpHeaders);
        ParameterizedTypeReference<Result<AuthorizationToken>> type = new ParameterizedTypeReference<Result<AuthorizationToken>>() {};
        ResponseEntity<Result<AuthorizationToken>> responseEntity = restTemplate.exchange("/authorizationToken/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}