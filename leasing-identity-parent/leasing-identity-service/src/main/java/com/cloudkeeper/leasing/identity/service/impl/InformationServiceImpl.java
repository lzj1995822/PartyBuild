package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.Information;
import com.cloudkeeper.leasing.identity.repository.InformationRepository;
import com.cloudkeeper.leasing.identity.service.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 消息通知 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InformationServiceImpl extends BaseServiceImpl<Information> implements InformationService {

    /** 消息通知 repository */
    private final InformationRepository informationRepository;

    @Override
    protected BaseRepository<Information> getBaseRepository() {
        return informationRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("description", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtID", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}