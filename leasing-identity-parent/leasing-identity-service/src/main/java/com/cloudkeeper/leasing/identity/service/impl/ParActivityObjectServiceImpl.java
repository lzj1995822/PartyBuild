package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ParActivityObject;
import com.cloudkeeper.leasing.identity.repository.ParActivityObjectRepository;
import com.cloudkeeper.leasing.identity.service.ParActivityObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 任务对象 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityObjectServiceImpl extends BaseServiceImpl<ParActivityObject> implements ParActivityObjectService {

    /** 任务对象 repository */
    private final ParActivityObjectRepository parActivityObjectRepository;

    @Override
    protected BaseRepository<ParActivityObject> getBaseRepository() {
        return parActivityObjectRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("isWorking", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public void deleteAllByActivityId(String activityId) {
        parActivityObjectRepository.deleteAllByActivityId(activityId);
    }
}
