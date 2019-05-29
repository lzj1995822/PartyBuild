package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.VolunteerGroup;
import com.cloudkeeper.leasing.identity.repository.VolunteerGroupRepository;
import com.cloudkeeper.leasing.identity.service.VolunteerGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 志愿者服务队伍 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VolunteerGroupServiceImpl extends BaseServiceImpl<VolunteerGroup> implements VolunteerGroupService {

    /** 志愿者服务队伍 repository */
    private final VolunteerGroupRepository volunteerGroupRepository;

    @Override
    protected BaseRepository<VolunteerGroup> getBaseRepository() {
        return volunteerGroupRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("code", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("unit", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("tenet", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("unitAddress", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("officeNumber", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("captialName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("captialPosition", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("captialContact", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("contactOfficer", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("officerPosition", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("phoneNumber", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("qqNumber", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("basicSiution", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}