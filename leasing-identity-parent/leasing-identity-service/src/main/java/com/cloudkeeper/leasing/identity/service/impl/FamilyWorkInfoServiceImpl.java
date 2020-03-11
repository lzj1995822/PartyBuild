package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.FamilyWorkInfo;
import com.cloudkeeper.leasing.identity.repository.FamilyWorkInfoRepository;
import com.cloudkeeper.leasing.identity.service.FamilyWorkInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 专职村书记家庭工作情况 service
 * @author yujian
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FamilyWorkInfoServiceImpl extends BaseServiceImpl<FamilyWorkInfo> implements FamilyWorkInfoService {

    /** 专职村书记家庭工作情况 repository */
    private final FamilyWorkInfoRepository familyWorkInfoRepository;

    @Override
    protected BaseRepository<FamilyWorkInfo> getBaseRepository() {
        return familyWorkInfoRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("appellation", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("politicalStatus", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("job", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("duration", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public void deleteAllByCadresId(String cadresId) {
        familyWorkInfoRepository.deleteAllByCadresId(cadresId);
    }

    @Override
    public List<FamilyWorkInfo> findAllByCadresId(String cadresId) {
        return familyWorkInfoRepository.findAllByCadresId(cadresId);
    }
}