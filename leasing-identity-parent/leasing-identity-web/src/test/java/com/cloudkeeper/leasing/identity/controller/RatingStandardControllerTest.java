package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.RatingStandard;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 村书记评级标准 controller 测试
 * @author asher
 */
public class RatingStandardControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        RatingStandard ratingStandard = new RatingStandard();
        HttpEntity<RatingStandard> httpEntity = new HttpEntity<>(ratingStandard, httpHeaders);
        ParameterizedTypeReference<Result<RatingStandard>> type = new ParameterizedTypeReference<Result<RatingStandard>>() {};
        ResponseEntity<Result<RatingStandard>> responseEntity = restTemplate.exchange("/ratingStandard/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}