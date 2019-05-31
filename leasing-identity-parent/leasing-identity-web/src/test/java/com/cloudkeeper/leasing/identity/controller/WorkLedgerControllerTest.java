package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.WorkLedger;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 工作台账 controller 测试
 * @author cqh
 */
public class WorkLedgerControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        WorkLedger workLedger = new WorkLedger();
        HttpEntity<WorkLedger> httpEntity = new HttpEntity<>(workLedger, httpHeaders);
        ParameterizedTypeReference<Result<WorkLedger>> type = new ParameterizedTypeReference<Result<WorkLedger>>() {};
        ResponseEntity<Result<WorkLedger>> responseEntity = restTemplate.exchange("/workLedger/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}