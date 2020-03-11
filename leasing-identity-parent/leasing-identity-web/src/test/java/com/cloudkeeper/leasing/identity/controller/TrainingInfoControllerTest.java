package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.TrainingInfo;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 专职村书记培训情况 controller 测试
 * @author yujian
 */
public class TrainingInfoControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        TrainingInfo trainingInfo = new TrainingInfo();
        HttpEntity<TrainingInfo> httpEntity = new HttpEntity<>(trainingInfo, httpHeaders);
        ParameterizedTypeReference<Result<TrainingInfo>> type = new ParameterizedTypeReference<Result<TrainingInfo>>() {};
        ResponseEntity<Result<TrainingInfo>> responseEntity = restTemplate.exchange("/trainingInfo/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}