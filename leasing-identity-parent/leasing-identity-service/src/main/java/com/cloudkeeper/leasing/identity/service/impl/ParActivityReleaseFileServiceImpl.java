package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ParActivityReleaseFile;
import com.cloudkeeper.leasing.identity.repository.ParActivityReleaseFileRepository;
import com.cloudkeeper.leasing.identity.service.ParActivityReleaseFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 发布任务上传文件 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityReleaseFileServiceImpl extends BaseServiceImpl<ParActivityReleaseFile> implements ParActivityReleaseFileService {

    /** 发布任务上传文件 repository */
    private final ParActivityReleaseFileRepository parActivityReleaseFileRepository;

    @Override
    protected BaseRepository<ParActivityReleaseFile> getBaseRepository() {
        return parActivityReleaseFileRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("activityID", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("Url", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}