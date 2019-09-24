package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.PeopleStream;
import com.cloudkeeper.leasing.identity.repository.PeopleStreamRepository;
import com.cloudkeeper.leasing.identity.service.PeopleStreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 阵地人流量 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PeopleStreamServiceImpl extends BaseServiceImpl<PeopleStream> implements PeopleStreamService {

    /** 阵地人流量 repository */
    private final PeopleStreamRepository peopleStreamRepository;

    @Override
    protected BaseRepository<PeopleStream> getBaseRepository() {
        return peopleStreamRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("locationCode", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}