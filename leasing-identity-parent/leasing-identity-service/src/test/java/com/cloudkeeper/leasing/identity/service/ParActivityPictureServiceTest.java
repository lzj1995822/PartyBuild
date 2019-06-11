package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParActivityPicture;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 手机截图 service 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ParActivityPictureServiceTest {

    /** 手机截图 service */
    @Autowired
    private ParActivityPictureService parActivityPictureService;

    @Test
    public void saveTest() {
        ParActivityPicture parActivityPicture = new ParActivityPicture();
        parActivityPicture = parActivityPictureService.save(parActivityPicture);
        assertNotNull(parActivityPicture.getId());
    }

}