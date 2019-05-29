package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.Volunteer;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 志愿者 controller 测试
 * @author asher
 */
public class VolunteerControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        Volunteer volunteer = new Volunteer();
        HttpEntity<Volunteer> httpEntity = new HttpEntity<>(volunteer, httpHeaders);
        ParameterizedTypeReference<Result<Volunteer>> type = new ParameterizedTypeReference<Result<Volunteer>>() {};
        ResponseEntity<Result<Volunteer>> responseEntity = restTemplate.exchange("/volunteer/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}