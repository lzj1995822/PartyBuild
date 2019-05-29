package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.CadrePosition;
import com.cloudkeeper.leasing.identity.repository.CadrePositionRepository;
import com.cloudkeeper.leasing.identity.service.CadrePositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 岗位管理 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadrePositionServiceImpl extends BaseServiceImpl<CadrePosition> implements CadrePositionService {

    /** 岗位管理 repository */
    private final CadrePositionRepository cadrePositionRepository;

    @Override
    protected BaseRepository<CadrePosition> getBaseRepository() {
        return cadrePositionRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("workPlace", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("duty", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}