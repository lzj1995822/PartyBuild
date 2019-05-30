package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.DistLearningActivityVideo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 远教视频 repository 测试
 * @author lxw
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DistLearningActivityVideoRepositoryTest {

    /** 远教视频 repository */
    @Autowired
    private DistLearningActivityVideoRepository distLearningActivityVideoRepository;

    @Test
    public void saveTest() {
        DistLearningActivityVideo distLearningActivityVideo = new DistLearningActivityVideo();
        distLearningActivityVideo = distLearningActivityVideoRepository.save(distLearningActivityVideo);
        assertNotNull(distLearningActivityVideo.getId());
    }

}