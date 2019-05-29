package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.Reserve;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 后备人才 controller 测试
 * @author asher
 */
public class ReserveControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        Reserve reserve = new Reserve();
        HttpEntity<Reserve> httpEntity = new HttpEntity<>(reserve, httpHeaders);
        ParameterizedTypeReference<Result<Reserve>> type = new ParameterizedTypeReference<Result<Reserve>>() {};
        ResponseEntity<Result<Reserve>> responseEntity = restTemplate.exchange("/reserve/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}