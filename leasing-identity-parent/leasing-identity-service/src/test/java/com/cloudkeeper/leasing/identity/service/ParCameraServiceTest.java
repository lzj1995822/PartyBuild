package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParCamera;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 监控信息 service 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ParCameraServiceTest {

    /** 监控信息 service */
    @Autowired
    private ParCameraService parCameraService;

    @Test
    public void saveTest() {
        ParCamera parCamera = new ParCamera();
        parCamera = parCameraService.save(parCamera);
        assertNotNull(parCamera.getId());
    }

}