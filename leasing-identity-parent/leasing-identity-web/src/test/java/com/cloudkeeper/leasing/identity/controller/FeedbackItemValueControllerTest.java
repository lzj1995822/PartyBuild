package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.FeedbackItemValue;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 反馈配置项值 controller 测试
 * @author asher
 */
public class FeedbackItemValueControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        FeedbackItemValue feedbackItemValue = new FeedbackItemValue();
        HttpEntity<FeedbackItemValue> httpEntity = new HttpEntity<>(feedbackItemValue, httpHeaders);
        ParameterizedTypeReference<Result<FeedbackItemValue>> type = new ParameterizedTypeReference<Result<FeedbackItemValue>>() {};
        ResponseEntity<Result<FeedbackItemValue>> responseEntity = restTemplate.exchange("/feedbackItemValue/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}