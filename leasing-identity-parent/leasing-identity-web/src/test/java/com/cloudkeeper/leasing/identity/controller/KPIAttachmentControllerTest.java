package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.KPIAttachment;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * 考核实施佐证材料 controller 测试
 * @author asher
 */
public class KPIAttachmentControllerTest extends BaseControllerTest {

    @Test
    public void add() {
        KPIAttachment kPIAttachment = new KPIAttachment();
        HttpEntity<KPIAttachment> httpEntity = new HttpEntity<>(kPIAttachment, httpHeaders);
        ParameterizedTypeReference<Result<KPIAttachment>> type = new ParameterizedTypeReference<Result<KPIAttachment>>() {};
        ResponseEntity<Result<KPIAttachment>> responseEntity = restTemplate.exchange("/kPIAttachment/add", HttpMethod.POST, httpEntity, type);
        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertNotNull(responseEntity.getBody().getContent());
    }

}