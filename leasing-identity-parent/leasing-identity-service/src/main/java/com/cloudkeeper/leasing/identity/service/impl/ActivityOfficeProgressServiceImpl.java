package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ActivityOfficeProgress;
import com.cloudkeeper.leasing.identity.repository.ActivityOfficeProgressRepository;
import com.cloudkeeper.leasing.identity.service.ActivityOfficeProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 机关活动进度记录 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ActivityOfficeProgressServiceImpl extends BaseServiceImpl<ActivityOfficeProgress> implements ActivityOfficeProgressService {

    /** 机关活动进度记录 repository */
    private final ActivityOfficeProgressRepository activityOfficeProgressRepository;

    @Override
    protected BaseRepository<ActivityOfficeProgress> getBaseRepository() {
        return activityOfficeProgressRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher();
    }

}