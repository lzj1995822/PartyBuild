package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.HonourInfo;
import com.cloudkeeper.leasing.identity.repository.HonourInfoRepository;
import com.cloudkeeper.leasing.identity.service.HonourInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 表彰情况 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HonourInfoServiceImpl extends BaseServiceImpl<HonourInfo> implements HonourInfoService {

    /** 表彰情况 repository */
    private final HonourInfoRepository honourInfoRepository;

    @Override
    protected BaseRepository<HonourInfo> getBaseRepository() {
        return honourInfoRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("content", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("des", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("supportDoc", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("isEdit", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public void deleteAllByCadresId(String cadresId) {
        honourInfoRepository.deleteAllByCadresId(cadresId);
    }

    @Override
    public List<HonourInfo> findAllByCadresId(String cadresId) {
        return honourInfoRepository.findAllByCadresId(cadresId);
    }

    @Override
    public List<HonourInfo> findAllByCadresIdAndRewardsType(String cadresId, String rewardsType) {
        return honourInfoRepository.findAllByCadresIdAndRewardsType(cadresId, rewardsType);
    }
}