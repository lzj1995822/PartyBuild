package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.ParActivityReleaseFile;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 发布任务上传文件 controller 测试
 * @author lxw
 */
public class ParActivityReleaseFileControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        ParActivityReleaseFile parActivityReleaseFile = new ParActivityReleaseFile();
        HttpEntity<ParActivityReleaseFile> httpEntity = new HttpEntity<>(parActivityReleaseFile, httpHeaders);
        ParameterizedTypeReference<Result<ParActivityReleaseFile>> type = new ParameterizedTypeReference<Result<ParActivityReleaseFile>>() {};
        ResponseEntity<Result<ParActivityReleaseFile>> responseEntity = restTemplate.exchange("/parActivityReleaseFile/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}