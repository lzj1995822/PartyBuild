package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.FamilyInfo;
import com.cloudkeeper.leasing.identity.repository.FamilyInfoRepository;
import com.cloudkeeper.leasing.identity.service.FamilyInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 专职村书记家庭情况 service
 * @author yujian
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FamilyInfoServiceImpl extends BaseServiceImpl<FamilyInfo> implements FamilyInfoService {

    /** 专职村书记家庭情况 repository */
    private final FamilyInfoRepository familyInfoRepository;

    @Override
    protected BaseRepository<FamilyInfo> getBaseRepository() {
        return familyInfoRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("appellation", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("birthDay", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("politicalStatus", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("orgAndJob", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public void deleteAllByCadresId(String cadresId) {
        familyInfoRepository.deleteAllByCadresId(cadresId);
    }

    @Override
    public List<FamilyInfo> findAllByCadresId(String cadresId) {
        return familyInfoRepository.findAllByCadresId(cadresId);
    }
}