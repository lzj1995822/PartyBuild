package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.VillageCadresAnnex;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 村主任信息附件 controller 测试
 * @author yujian
 */
public class VillageCadresAnnexControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        VillageCadresAnnex villageCadresAnnex = new VillageCadresAnnex();
        HttpEntity<VillageCadresAnnex> httpEntity = new HttpEntity<>(villageCadresAnnex, httpHeaders);
        ParameterizedTypeReference<Result<VillageCadresAnnex>> type = new ParameterizedTypeReference<Result<VillageCadresAnnex>>() {};
        ResponseEntity<Result<VillageCadresAnnex>> responseEntity = restTemplate.exchange("/villageCadresAnnex/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}