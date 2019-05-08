package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ParCamera;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 监控信息 repository 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParCameraRepositoryTest {

    /** 监控信息 repository */
    @Autowired
    private ParCameraRepository parCameraRepository;

    @Test
    public void saveTest() {
        ParCamera parCamera = new ParCamera();
        parCamera = parCameraRepository.save(parCamera);
        assertNotNull(parCamera.getId());
    }

}