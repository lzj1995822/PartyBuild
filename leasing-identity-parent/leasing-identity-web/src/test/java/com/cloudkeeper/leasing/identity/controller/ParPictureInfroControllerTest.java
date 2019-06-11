package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.ParPictureInfro;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 电视截图 controller 测试
 * @author lxw
 */
public class ParPictureInfroControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        ParPictureInfro parPictureInfro = new ParPictureInfro();
        HttpEntity<ParPictureInfro> httpEntity = new HttpEntity<>(parPictureInfro, httpHeaders);
        ParameterizedTypeReference<Result<ParPictureInfro>> type = new ParameterizedTypeReference<Result<ParPictureInfro>>() {};
        ResponseEntity<Result<ParPictureInfro>> responseEntity = restTemplate.exchange("/parPictureInfro/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}