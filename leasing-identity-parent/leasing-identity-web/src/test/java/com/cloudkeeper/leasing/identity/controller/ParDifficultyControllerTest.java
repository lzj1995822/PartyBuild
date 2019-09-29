package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.ParDifficulty;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 困难党员 controller 测试
 * @author cqh
 */
public class ParDifficultyControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        ParDifficulty parDifficulty = new ParDifficulty();
        HttpEntity<ParDifficulty> httpEntity = new HttpEntity<>(parDifficulty, httpHeaders);
        ParameterizedTypeReference<Result<ParDifficulty>> type = new ParameterizedTypeReference<Result<ParDifficulty>>() {};
        ResponseEntity<Result<ParDifficulty>> responseEntity = restTemplate.exchange("/parDifficulty/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}