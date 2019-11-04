package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.FeedbackTemplateItem;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 反馈配置项 controller 测试
 * @author asher
 */
public class FeedbackTemplateItemControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        FeedbackTemplateItem feedbackTemplateItem = new FeedbackTemplateItem();
        HttpEntity<FeedbackTemplateItem> httpEntity = new HttpEntity<>(feedbackTemplateItem, httpHeaders);
        ParameterizedTypeReference<Result<FeedbackTemplateItem>> type = new ParameterizedTypeReference<Result<FeedbackTemplateItem>>() {};
        ResponseEntity<Result<FeedbackTemplateItem>> responseEntity = restTemplate.exchange("/feedbackTemplateItem/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}