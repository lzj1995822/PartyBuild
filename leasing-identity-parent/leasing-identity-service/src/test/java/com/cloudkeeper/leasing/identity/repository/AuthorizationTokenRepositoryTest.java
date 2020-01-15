package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.AuthorizationToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 访问权限 repository 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorizationTokenRepositoryTest {

    /** 访问权限 repository */
    @Autowired
    private AuthorizationTokenRepository authorizationTokenRepository;

    @Test
    public void saveTest() {
        AuthorizationToken authorizationToken = new AuthorizationToken();
        authorizationToken = authorizationTokenRepository.save(authorizationToken);
        assertNotNull(authorizationToken.getId());
    }

}