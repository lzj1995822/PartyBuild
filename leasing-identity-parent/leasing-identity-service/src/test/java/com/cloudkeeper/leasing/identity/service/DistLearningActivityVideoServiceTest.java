package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.DistLearningActivityVideo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 远教视频 service 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DistLearningActivityVideoServiceTest {

    /** 远教视频 service */
    @Autowired
    private DistLearningActivityVideoService distLearningActivityVideoService;

    @Test
    public void saveTest() {
        DistLearningActivityVideo distLearningActivityVideo = new DistLearningActivityVideo();
        distLearningActivityVideo = distLearningActivityVideoService.save(distLearningActivityVideo);
        assertNotNull(distLearningActivityVideo.getId());
    }

}