package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParPictureInfro;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 电视截图 service 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ParPictureInfroServiceTest {

    /** 电视截图 service */
    @Autowired
    private ParPictureInfroService parPictureInfroService;

    @Test
    public void saveTest() {
        ParPictureInfro parPictureInfro = new ParPictureInfro();
        parPictureInfro = parPictureInfroService.save(parPictureInfro);
        assertNotNull(parPictureInfro.getId());
    }

}