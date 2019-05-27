package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.AcceptInformation;
import com.cloudkeeper.leasing.identity.repository.AcceptInformationRepository;
import com.cloudkeeper.leasing.identity.service.AcceptInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 接收公告 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AcceptInformationServiceImpl extends BaseServiceImpl<AcceptInformation> implements AcceptInformationService {

    /** 接收公告 repository */
    private final AcceptInformationRepository acceptInformationRepository;

    @Override
    protected BaseRepository<AcceptInformation> getBaseRepository() {
        return acceptInformationRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("objs", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public Integer deleteAllByInformationId(String informationId) {
       return acceptInformationRepository.deleteAllByInformationId(informationId);
    }
}