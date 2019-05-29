package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.identity.repository.SysDistrictRepository;
import com.cloudkeeper.leasing.identity.service.SysDistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysDistrictServiceImpl extends BaseServiceImpl<SysDistrict> implements SysDistrictService {

    /** 组织 repository */
    private final SysDistrictRepository sysDistrictRepository;

    @Override
    protected BaseRepository<SysDistrict> getBaseRepository() {
        return sysDistrictRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("districtName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("attachTo", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("subDistrictNum", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("description", ExampleMatcher.GenericPropertyMatchers.contains());
    }


    @Override
    public Map<String ,String> findAllByDistrictLevelNot() {
        List<SysDistrict> allByDistrictLevelNot = sysDistrictRepository.findAllByDistrictLevelNot(3);
        Map<String,String> map = new HashMap<>();
        for (SysDistrict item:allByDistrictLevelNot) {
            map.put(item.getDistrictId(),item.getDistrictName());
        }
        return map;
    }
}