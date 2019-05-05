package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.SysUser;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 系统用户 controller 测试
 * @author asher
 */
public class SysUserControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        SysUser sysUser = new SysUser();
        HttpEntity<SysUser> httpEntity = new HttpEntity<>(sysUser, httpHeaders);
        ParameterizedTypeReference<Result<SysUser>> type = new ParameterizedTypeReference<Result<SysUser>>() {};
        ResponseEntity<Result<SysUser>> responseEntity = restTemplate.exchange("/sysUser/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}