package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.RewardInfo;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 报酬情况 controller 测试
 * @author asher
 */
public class RewardInfoControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        RewardInfo rewardInfo = new RewardInfo();
        HttpEntity<RewardInfo> httpEntity = new HttpEntity<>(rewardInfo, httpHeaders);
        ParameterizedTypeReference<Result<RewardInfo>> type = new ParameterizedTypeReference<Result<RewardInfo>>() {};
        ResponseEntity<Result<RewardInfo>> responseEntity = restTemplate.exchange("/rewardInfo/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}