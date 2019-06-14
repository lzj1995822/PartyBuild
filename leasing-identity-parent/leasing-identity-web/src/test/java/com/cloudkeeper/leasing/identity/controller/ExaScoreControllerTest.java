package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.ExaScore;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 考核积分 controller 测试
 * @author lxw
 */
public class ExaScoreControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        ExaScore exaScore = new ExaScore();
        HttpEntity<ExaScore> httpEntity = new HttpEntity<>(exaScore, httpHeaders);
        ParameterizedTypeReference<Result<ExaScore>> type = new ParameterizedTypeReference<Result<ExaScore>>() {};
        ResponseEntity<Result<ExaScore>> responseEntity = restTemplate.exchange("/exaScore/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}