package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.AuthorizationToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 访问权限 service 测试
 * @author cqh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AuthorizationTokenServiceTest {

    /** 访问权限 service */
    @Autowired
    private AuthorizationTokenService authorizationTokenService;

    @Test
    public void saveTest() {
        AuthorizationToken authorizationToken = new AuthorizationToken();
        authorizationToken = authorizationTokenService.save(authorizationToken);
        assertNotNull(authorizationToken.getId());
    }

}