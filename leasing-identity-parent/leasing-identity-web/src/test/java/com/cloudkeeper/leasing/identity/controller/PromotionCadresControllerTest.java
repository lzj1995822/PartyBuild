package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.PromotionCadres;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 村书记拟晋升名单 controller 测试
 * @author asher
 */
public class PromotionCadresControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        PromotionCadres promotionCadres = new PromotionCadres();
        HttpEntity<PromotionCadres> httpEntity = new HttpEntity<>(promotionCadres, httpHeaders);
        ParameterizedTypeReference<Result<PromotionCadres>> type = new ParameterizedTypeReference<Result<PromotionCadres>>() {};
        ResponseEntity<Result<PromotionCadres>> responseEntity = restTemplate.exchange("/promotionCadres/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}