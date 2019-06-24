package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.SysConfiguration;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 系统属性配置 controller 测试
 * @author cqh
 */
public class SysConfigurationControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        SysConfiguration sysConfiguration = new SysConfiguration();
        HttpEntity<SysConfiguration> httpEntity = new HttpEntity<>(sysConfiguration, httpHeaders);
        ParameterizedTypeReference<Result<SysConfiguration>> type = new ParameterizedTypeReference<Result<SysConfiguration>>() {};
        ResponseEntity<Result<SysConfiguration>> responseEntity = restTemplate.exchange("/sysConfiguration/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}