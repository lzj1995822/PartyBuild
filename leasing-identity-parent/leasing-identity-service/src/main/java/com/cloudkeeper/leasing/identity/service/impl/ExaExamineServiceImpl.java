package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ExaExamine;
import com.cloudkeeper.leasing.identity.repository.ExaExamineRepository;
import com.cloudkeeper.leasing.identity.service.ExaExamineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 考核审核 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExaExamineServiceImpl extends BaseServiceImpl<ExaExamine> implements ExaExamineService {

    /** 考核审核 repository */
    private final ExaExamineRepository exaExamineRepository;

    @Override
    protected BaseRepository<ExaExamine> getBaseRepository() {
        return exaExamineRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("remark", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}