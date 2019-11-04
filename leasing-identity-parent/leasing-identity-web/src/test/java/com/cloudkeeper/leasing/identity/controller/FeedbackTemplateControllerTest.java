package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.FeedbackTemplate;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 反馈配置模板 controller 测试
 * @author asher
 */
public class FeedbackTemplateControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        FeedbackTemplate feedbackTemplate = new FeedbackTemplate();
        HttpEntity<FeedbackTemplate> httpEntity = new HttpEntity<>(feedbackTemplate, httpHeaders);
        ParameterizedTypeReference<Result<FeedbackTemplate>> type = new ParameterizedTypeReference<Result<FeedbackTemplate>>() {};
        ResponseEntity<Result<FeedbackTemplate>> responseEntity = restTemplate.exchange("/feedbackTemplate/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}