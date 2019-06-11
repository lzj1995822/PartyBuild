package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.ParActivityFeedback;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 移动端执行记录 controller 测试
 * @author lxw
 */
public class ParActivityFeedbackControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        ParActivityFeedback parActivityFeedback = new ParActivityFeedback();
        HttpEntity<ParActivityFeedback> httpEntity = new HttpEntity<>(parActivityFeedback, httpHeaders);
        ParameterizedTypeReference<Result<ParActivityFeedback>> type = new ParameterizedTypeReference<Result<ParActivityFeedback>>() {};
        ResponseEntity<Result<ParActivityFeedback>> responseEntity = restTemplate.exchange("/parActivityFeedback/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}