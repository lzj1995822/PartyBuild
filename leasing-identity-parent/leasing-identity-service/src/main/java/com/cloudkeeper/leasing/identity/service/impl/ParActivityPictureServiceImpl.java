package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ParActivityPicture;
import com.cloudkeeper.leasing.identity.domain.ParPictureInfro;
import com.cloudkeeper.leasing.identity.repository.ParActivityPictureRepository;
import com.cloudkeeper.leasing.identity.service.ParActivityPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 手机截图 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityPictureServiceImpl extends BaseServiceImpl<ParActivityPicture> implements ParActivityPictureService {

    /** 手机截图 repository */
    private final ParActivityPictureRepository parActivityPictureRepository;

    @Override
    protected BaseRepository<ParActivityPicture> getBaseRepository() {
        return parActivityPictureRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("activityID", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("ImageUrl", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public ParActivityPicture findByRedisUuid(String redisUuid) {
        return parActivityPictureRepository.findByRedisUuid(redisUuid);
    }
}