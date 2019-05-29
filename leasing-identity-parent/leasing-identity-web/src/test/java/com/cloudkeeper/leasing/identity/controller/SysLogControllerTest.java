package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.SysLog;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 系统日志 controller 测试
 * @author asher
 */
public class SysLogControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        SysLog sysLog = new SysLog();
        HttpEntity<SysLog> httpEntity = new HttpEntity<>(sysLog, httpHeaders);
        ParameterizedTypeReference<Result<SysLog>> type = new ParameterizedTypeReference<Result<SysLog>>() {};
        ResponseEntity<Result<SysLog>> responseEntity = restTemplate.exchange("/sysLog/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}