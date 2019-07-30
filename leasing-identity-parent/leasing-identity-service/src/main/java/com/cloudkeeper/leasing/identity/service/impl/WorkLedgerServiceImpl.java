package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.WorkLedger;
import com.cloudkeeper.leasing.identity.repository.WorkLedgerRepository;
import com.cloudkeeper.leasing.identity.service.WorkLedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 工作台账 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WorkLedgerServiceImpl extends BaseServiceImpl<WorkLedger> implements WorkLedgerService {

    /** 工作台账 repository */
    private final WorkLedgerRepository workLedgerRepository;

    @Override
    protected BaseRepository<WorkLedger> getBaseRepository() {
        return workLedgerRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("ledgerType", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("workAddress", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("content", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("enclosure", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtId", ExampleMatcher.GenericPropertyMatchers.startsWith());
    }

}