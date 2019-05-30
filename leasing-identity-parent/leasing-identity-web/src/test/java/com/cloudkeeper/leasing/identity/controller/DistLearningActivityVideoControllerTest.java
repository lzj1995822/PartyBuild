package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.DistLearningActivityVideo;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 远教视频 controller 测试
 * @author lxw
 */
public class DistLearningActivityVideoControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        DistLearningActivityVideo distLearningActivityVideo = new DistLearningActivityVideo();
        HttpEntity<DistLearningActivityVideo> httpEntity = new HttpEntity<>(distLearningActivityVideo, httpHeaders);
        ParameterizedTypeReference<Result<DistLearningActivityVideo>> type = new ParameterizedTypeReference<Result<DistLearningActivityVideo>>() {};
        ResponseEntity<Result<DistLearningActivityVideo>> responseEntity = restTemplate.exchange("/distLearningActivityVideo/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}