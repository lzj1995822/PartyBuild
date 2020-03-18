package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.VillageCadresTerm;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 村主任任期信息 controller 测试
 * @author yujian
 */
public class VillageCadresTermControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        VillageCadresTerm villageCadresTerm = new VillageCadresTerm();
        HttpEntity<VillageCadresTerm> httpEntity = new HttpEntity<>(villageCadresTerm, httpHeaders);
        ParameterizedTypeReference<Result<VillageCadresTerm>> type = new ParameterizedTypeReference<Result<VillageCadresTerm>>() {};
        ResponseEntity<Result<VillageCadresTerm>> responseEntity = restTemplate.exchange("/villageCadresTerm/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}