package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ParRepresentative;
import com.cloudkeeper.leasing.identity.repository.ParRepresentativeRepository;
import com.cloudkeeper.leasing.identity.service.ParRepresentativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 党代表 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParRepresentativeServiceImpl extends BaseServiceImpl<ParRepresentative> implements ParRepresentativeService {

    /** 党代表 repository */
    private final ParRepresentativeRepository parRepresentativeRepository;

    @Override
    protected BaseRepository<ParRepresentative> getBaseRepository() {
        return parRepresentativeRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("sex", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("education", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("talent", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("resume", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("performance", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("politicalTrail", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("rewardPunish", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("opinion", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("remark", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtId", ExampleMatcher.GenericPropertyMatchers.startsWith());
    }

}