package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.KPIEvaluation;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 综合评议 controller 测试
 * @author yujian
 */
public class KPIEvaluationControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        KPIEvaluation kPIEvaluation = new KPIEvaluation();
        HttpEntity<KPIEvaluation> httpEntity = new HttpEntity<>(kPIEvaluation, httpHeaders);
        ParameterizedTypeReference<Result<KPIEvaluation>> type = new ParameterizedTypeReference<Result<KPIEvaluation>>() {};
        ResponseEntity<Result<KPIEvaluation>> responseEntity = restTemplate.exchange("/kPIEvaluation/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}