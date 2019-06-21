package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.SysConfiguration;
import com.cloudkeeper.leasing.identity.repository.SysConfigurationRepository;
import com.cloudkeeper.leasing.identity.service.SysConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 系统属性配置 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysConfigurationServiceImpl extends BaseServiceImpl<SysConfiguration> implements SysConfigurationService {

    /** 系统属性配置 repository */
    private final SysConfigurationRepository sysConfigurationRepository;

    @Override
    protected BaseRepository<SysConfiguration> getBaseRepository() {
        return sysConfigurationRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("code", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("codeValue", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}