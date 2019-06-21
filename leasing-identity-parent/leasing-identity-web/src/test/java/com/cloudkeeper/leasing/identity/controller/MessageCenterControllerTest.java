package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.MessageCenter;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 消息中心 controller 测试
 * @author cqh
 */
public class MessageCenterControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        MessageCenter messageCenter = new MessageCenter();
        HttpEntity<MessageCenter> httpEntity = new HttpEntity<>(messageCenter, httpHeaders);
        ParameterizedTypeReference<Result<MessageCenter>> type = new ParameterizedTypeReference<Result<MessageCenter>>() {};
        ResponseEntity<Result<MessageCenter>> responseEntity = restTemplate.exchange("/messageCenter/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}