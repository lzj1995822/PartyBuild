package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.FamilyWorkInfo;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 专职村书记家庭工作情况 controller 测试
 * @author yujian
 */
public class FamilyWorkInfoControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        FamilyWorkInfo familyWorkInfo = new FamilyWorkInfo();
        HttpEntity<FamilyWorkInfo> httpEntity = new HttpEntity<>(familyWorkInfo, httpHeaders);
        ParameterizedTypeReference<Result<FamilyWorkInfo>> type = new ParameterizedTypeReference<Result<FamilyWorkInfo>>() {};
        ResponseEntity<Result<FamilyWorkInfo>> responseEntity = restTemplate.exchange("/familyWorkInfo/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}