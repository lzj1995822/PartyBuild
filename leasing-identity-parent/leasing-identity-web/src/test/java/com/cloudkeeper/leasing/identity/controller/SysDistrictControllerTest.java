package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 组织 controller 测试
 * @author lxw
 */
public class SysDistrictControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        SysDistrict sysDistrict = new SysDistrict();
        HttpEntity<SysDistrict> httpEntity = new HttpEntity<>(sysDistrict, httpHeaders);
        ParameterizedTypeReference<Result<SysDistrict>> type = new ParameterizedTypeReference<Result<SysDistrict>>() {};
        ResponseEntity<Result<SysDistrict>> responseEntity = restTemplate.exchange("/sysDistrict/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}