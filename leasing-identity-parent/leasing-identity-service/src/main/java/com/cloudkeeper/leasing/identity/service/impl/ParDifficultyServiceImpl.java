package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ParDifficulty;
import com.cloudkeeper.leasing.identity.repository.ParDifficultyRepository;
import com.cloudkeeper.leasing.identity.service.ParDifficultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 困难党员 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParDifficultyServiceImpl extends BaseServiceImpl<ParDifficulty> implements ParDifficultyService {

    /** 困难党员 repository */
    private final ParDifficultyRepository parDifficultyRepository;

    @Override
    protected BaseRepository<ParDifficulty> getBaseRepository() {
        return parDifficultyRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("isDifficulty", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("incomeSource", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("averageIncome", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("memberName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtId", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("povertyCauses", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}