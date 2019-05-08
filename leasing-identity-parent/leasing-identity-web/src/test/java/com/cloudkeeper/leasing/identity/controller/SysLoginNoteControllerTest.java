package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.SysLoginNote;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 系统登录日志 controller 测试
 * @author cqh
 */
public class SysLoginNoteControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        SysLoginNote sysLoginNote = new SysLoginNote();
        HttpEntity<SysLoginNote> httpEntity = new HttpEntity<>(sysLoginNote, httpHeaders);
        ParameterizedTypeReference<Result<SysLoginNote>> type = new ParameterizedTypeReference<Result<SysLoginNote>>() {};
        ResponseEntity<Result<SysLoginNote>> responseEntity = restTemplate.exchange("/sysLoginNote/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}