package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.Reserve;
import com.cloudkeeper.leasing.identity.repository.ReserveRepository;
import com.cloudkeeper.leasing.identity.service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 后备人才 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReserveServiceImpl extends BaseServiceImpl<Reserve> implements ReserveService {

    /** 后备人才 repository */
    private final ReserveRepository reserveRepository;

    @Override
    protected BaseRepository<Reserve> getBaseRepository() {
        return reserveRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("nation", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("nativePlace", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("education", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("graduated", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("category", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("unit", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("basicSituation", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("resume", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtId", ExampleMatcher.GenericPropertyMatchers.startsWith());
    }

}