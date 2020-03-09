package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.RewardInfo;
import com.cloudkeeper.leasing.identity.repository.RewardInfoRepository;
import com.cloudkeeper.leasing.identity.service.RewardInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报酬情况 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RewardInfoServiceImpl extends BaseServiceImpl<RewardInfo> implements RewardInfoService {

    /** 报酬情况 repository */
    private final RewardInfoRepository rewardInfoRepository;

    @Override
    protected BaseRepository<RewardInfo> getBaseRepository() {
        return rewardInfoRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("basicReward", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("reviewReward", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("otherReward", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("totalReward", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("supportDoc", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("isEdit", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("des", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public void deleteAllByCadresId(String cadresId) {
        rewardInfoRepository.deleteAllByCadresId(cadresId);
    }

    @Override
    public List<RewardInfo> findAllByCadresId(String cadresId) {
        return rewardInfoRepository.findAllByCadresId(cadresId);
    }
}