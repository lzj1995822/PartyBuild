package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.FamilyInfo;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 专职村书记家庭情况 controller 测试
 * @author yujian
 */
public class FamilyInfoControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        FamilyInfo familyInfo = new FamilyInfo();
        HttpEntity<FamilyInfo> httpEntity = new HttpEntity<>(familyInfo, httpHeaders);
        ParameterizedTypeReference<Result<FamilyInfo>> type = new ParameterizedTypeReference<Result<FamilyInfo>>() {};
        ResponseEntity<Result<FamilyInfo>> responseEntity = restTemplate.exchange("/familyInfo/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}