package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.TVSignIn;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 远教视频签到记录 repository 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TVSignInRepositoryTest {

    /** 远教视频签到记录 repository */
    @Autowired
    private TVSignInRepository tVSignInRepository;

    @Test
    public void saveTest() {
        TVSignIn tVSignIn = new TVSignIn();
        tVSignIn = tVSignInRepository.save(tVSignIn);
        assertNotNull(tVSignIn.getId());
    }

}