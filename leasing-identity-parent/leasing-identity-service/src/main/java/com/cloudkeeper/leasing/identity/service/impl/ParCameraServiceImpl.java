package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ParCamera;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.identity.repository.ParCameraRepository;
import com.cloudkeeper.leasing.identity.repository.SysDistrictRepository;
import com.cloudkeeper.leasing.identity.service.ParCameraService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 监控信息 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParCameraServiceImpl extends BaseServiceImpl<ParCamera> implements ParCameraService {

    /** 监控信息 repository */
    private final ParCameraRepository parCameraRepository;

    private final RedisTemplate<String, String> redisTemplate;

    private final SysDistrictRepository sysDistrictRepository;


    @Override
    protected BaseRepository<ParCamera> getBaseRepository() {
        return parCameraRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("IP", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("number", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("time", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("remark", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public ParCamera findByNumber(String boxNumber) {
        return parCameraRepository.findByNumber(boxNumber);
    }

    @Override
    public ParCamera redisIp(String key) {
        ParCamera parCamera = new ParCamera();
        parCamera.setIP(redisTemplate.boundValueOps(key).get());
        SysDistrict byDistrictId = sysDistrictRepository.findByDistrictId(key);
        parCamera.setOrganizationId(byDistrictId.getId());
        return  parCamera;
    }
}
