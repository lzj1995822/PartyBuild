package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.PositionInformation;
import com.cloudkeeper.leasing.identity.repository.PositionInformationRepository;
import com.cloudkeeper.leasing.identity.service.PositionInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * 阵地信息 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PositionInformationServiceImpl extends BaseServiceImpl<PositionInformation> implements PositionInformationService {

    /** 阵地信息 repository */
    private final PositionInformationRepository positionInformationRepository;

    @Override
    protected BaseRepository<PositionInformation> getBaseRepository() {
        return positionInformationRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtId", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("facilities", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("area", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("introduction", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("pictures", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("hotDegree", ExampleMatcher.GenericPropertyMatchers.contains());
    }


    @Override
    public Integer countAllByDistrictId(String districtId) {
        Integer integer = positionInformationRepository.countAllByDistrictIdStartingWith(districtId);
        return integer;
    }
}