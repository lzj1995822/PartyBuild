package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.AuthorizationToken;
import com.cloudkeeper.leasing.identity.repository.AuthorizationTokenRepository;
import com.cloudkeeper.leasing.identity.service.AuthorizationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 访问权限 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizationTokenServiceImpl extends BaseServiceImpl<AuthorizationToken> implements AuthorizationTokenService {

    /** 访问权限 repository */
    private final AuthorizationTokenRepository authorizationTokenRepository;

    @Override
    protected BaseRepository<AuthorizationToken> getBaseRepository() {
        return authorizationTokenRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("code", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("des", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("tokenVal", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}