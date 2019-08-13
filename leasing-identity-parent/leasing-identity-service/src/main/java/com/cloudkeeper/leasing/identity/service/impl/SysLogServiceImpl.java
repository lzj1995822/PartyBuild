package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.constant.AuthorizationConstants;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ParActivity;
import com.cloudkeeper.leasing.identity.domain.ParActivityObject;
import com.cloudkeeper.leasing.identity.domain.SysLog;
import com.cloudkeeper.leasing.identity.domain.SysUser;
import com.cloudkeeper.leasing.identity.repository.ParActivityObjectRepository;
import com.cloudkeeper.leasing.identity.repository.SysDistrictRepository;
import com.cloudkeeper.leasing.identity.repository.SysLogRepository;
import com.cloudkeeper.leasing.identity.repository.SysUserRepository;
import com.cloudkeeper.leasing.identity.service.SysLogService;
import com.cloudkeeper.leasing.identity.service.SysUserService;
import com.cloudkeeper.leasing.identity.vo.SysLogVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * 系统日志 service
 * @author asher
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysLogServiceImpl extends BaseServiceImpl<SysLog> implements SysLogService {

    /** 系统日志 repository */
    private final SysLogRepository sysLogRepository;

   // private final SysUserService sysUserService;

    private final ParActivityObjectRepository parActivityObjectRepository;

    private final SysDistrictRepository sysDistrictRepository;

    private final SysUserRepository sysUserRepository;

    @Override
    protected BaseRepository<SysLog> getBaseRepository() {
        return sysLogRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("controllerName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("msg", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("tableName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("actor", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    public SysLogVO pushLog(String controllerName, String msg, String tableName, String businessId) {
        String userId = (String) super.getHttpSession().getAttribute(AuthorizationConstants.CURRENT_USER_ID);
        SysLog sysLog = new SysLog();
        if(StringUtils.isEmpty(userId)){
            Optional<ParActivityObject> parActivityObject = parActivityObjectRepository.findById(businessId);
            String orgId =  parActivityObject.get().getOrganizationId();
            String name = sysDistrictRepository.findAllByDistrictId(orgId).get(0).getDistrictName();
            sysLog = super.save(new SysLog(controllerName, msg, tableName, businessId, name));
        }else {
            Optional<SysUser> byId = sysUserRepository.findById(userId);
            SysUser sysUser = byId.get();
             sysLog = super.save(new SysLog(controllerName, msg, tableName, businessId, sysUser.getName()));
        }

        return sysLog.convert(SysLogVO.class);
    }
}
