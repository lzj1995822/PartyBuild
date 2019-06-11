package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.ParActivityPicture;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 手机截图 controller 测试
 * @author lxw
 */
public class ParActivityPictureControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        ParActivityPicture parActivityPicture = new ParActivityPicture();
        HttpEntity<ParActivityPicture> httpEntity = new HttpEntity<>(parActivityPicture, httpHeaders);
        ParameterizedTypeReference<Result<ParActivityPicture>> type = new ParameterizedTypeReference<Result<ParActivityPicture>>() {};
        ResponseEntity<Result<ParActivityPicture>> responseEntity = restTemplate.exchange("/parActivityPicture/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}