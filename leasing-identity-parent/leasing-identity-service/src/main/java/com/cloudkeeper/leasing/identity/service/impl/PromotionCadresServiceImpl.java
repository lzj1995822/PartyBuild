package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.PromotionCadres;
import com.cloudkeeper.leasing.identity.repository.PromotionCadresRepository;
import com.cloudkeeper.leasing.identity.service.PromotionCadresService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 村书记拟晋升名单 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PromotionCadresServiceImpl extends BaseServiceImpl<PromotionCadres> implements PromotionCadresService {

    /** 村书记拟晋升名单 repository */
    private final PromotionCadresRepository promotionCadresRepository;

    @Override
    protected BaseRepository<PromotionCadres> getBaseRepository() {
        return promotionCadresRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("cadresName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("postName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("purposeLevelName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("townId", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("taskId", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("villageId", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("isBreakRule", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.contains());
    }

}