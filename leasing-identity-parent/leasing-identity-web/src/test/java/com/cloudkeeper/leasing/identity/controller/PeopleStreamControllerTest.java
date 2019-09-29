package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.PeopleStream;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 阵地人流量 controller 测试
 * @author asher
 */
public class PeopleStreamControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        PeopleStream peopleStream = new PeopleStream();
        HttpEntity<PeopleStream> httpEntity = new HttpEntity<>(peopleStream, httpHeaders);
        ParameterizedTypeReference<Result<PeopleStream>> type = new ParameterizedTypeReference<Result<PeopleStream>>() {};
        ResponseEntity<Result<PeopleStream>> responseEntity = restTemplate.exchange("/peopleStream/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}