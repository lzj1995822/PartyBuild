package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.VillageCadres;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 村干部管理 controller 测试
 * @author cqh
 */
public class VillageCadresControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        VillageCadres villageCadres = new VillageCadres();
        HttpEntity<VillageCadres> httpEntity = new HttpEntity<>(villageCadres, httpHeaders);
        ParameterizedTypeReference<Result<VillageCadres>> type = new ParameterizedTypeReference<Result<VillageCadres>>() {};
        ResponseEntity<Result<VillageCadres>> responseEntity = restTemplate.exchange("/villageCadres/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}