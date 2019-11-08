package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.ActivityOfficeProgress;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 机关活动进度记录 controller 测试
 * @author asher
 */
public class ActivityOfficeProgressControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        ActivityOfficeProgress activityOfficeProgress = new ActivityOfficeProgress();
        HttpEntity<ActivityOfficeProgress> httpEntity = new HttpEntity<>(activityOfficeProgress, httpHeaders);
        ParameterizedTypeReference<Result<ActivityOfficeProgress>> type = new ParameterizedTypeReference<Result<ActivityOfficeProgress>>() {};
        ResponseEntity<Result<ActivityOfficeProgress>> responseEntity = restTemplate.exchange("/activityOfficeProgress/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}