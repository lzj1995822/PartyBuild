package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.Volunteer;
import com.cloudkeeper.leasing.identity.repository.VolunteerRepository;
import com.cloudkeeper.leasing.identity.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 志愿者 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VolunteerServiceImpl extends BaseServiceImpl<Volunteer> implements VolunteerService {

    /** 志愿者 repository */
    private final VolunteerRepository volunteerRepository;

    @Override
    protected BaseRepository<Volunteer> getBaseRepository() {
        return volunteerRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("category", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("promise", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("otherCategory", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}