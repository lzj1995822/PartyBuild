package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.VolunteerGroup;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 志愿者服务队伍 controller 测试
 * @author asher
 */
public class VolunteerGroupControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        VolunteerGroup volunteerGroup = new VolunteerGroup();
        HttpEntity<VolunteerGroup> httpEntity = new HttpEntity<>(volunteerGroup, httpHeaders);
        ParameterizedTypeReference<Result<VolunteerGroup>> type = new ParameterizedTypeReference<Result<VolunteerGroup>>() {};
        ResponseEntity<Result<VolunteerGroup>> responseEntity = restTemplate.exchange("/volunteerGroup/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}