package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.ParRepresentative;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 党代表 controller 测试
 * @author cqh
 */
public class ParRepresentativeControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        ParRepresentative parRepresentative = new ParRepresentative();
        HttpEntity<ParRepresentative> httpEntity = new HttpEntity<>(parRepresentative, httpHeaders);
        ParameterizedTypeReference<Result<ParRepresentative>> type = new ParameterizedTypeReference<Result<ParRepresentative>>() {};
        ResponseEntity<Result<ParRepresentative>> responseEntity = restTemplate.exchange("/parRepresentative/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}