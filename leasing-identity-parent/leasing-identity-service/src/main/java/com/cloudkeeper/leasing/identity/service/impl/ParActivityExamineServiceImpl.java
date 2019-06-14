package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ParActivityExamine;
import com.cloudkeeper.leasing.identity.repository.ParActivityExamineRepository;
import com.cloudkeeper.leasing.identity.service.ParActivityExamineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 活动考核记录 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityExamineServiceImpl extends BaseServiceImpl<ParActivityExamine> implements ParActivityExamineService {

    /** 活动考核记录 repository */
    private final ParActivityExamineRepository parActivityExamineRepository;

    @Override
    protected BaseRepository<ParActivityExamine> getBaseRepository() {
        return parActivityExamineRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("remark", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}