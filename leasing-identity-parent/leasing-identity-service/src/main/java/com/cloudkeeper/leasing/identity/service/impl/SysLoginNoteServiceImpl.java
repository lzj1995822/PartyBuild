package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.SysLoginNote;
import com.cloudkeeper.leasing.identity.repository.SysLoginNoteRepository;
import com.cloudkeeper.leasing.identity.service.SysLoginNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 系统登录日志 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysLoginNoteServiceImpl extends BaseServiceImpl<SysLoginNote> implements SysLoginNoteService {

    /** 系统登录日志 repository */
    private final SysLoginNoteRepository sysLoginNoteRepository;

    @Override
    protected BaseRepository<SysLoginNote> getBaseRepository() {
        return sysLoginNoteRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("createTime", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("userName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("action", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("region", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("createTime", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}