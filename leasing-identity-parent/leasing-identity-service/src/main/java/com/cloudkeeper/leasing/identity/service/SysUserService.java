package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.SysUser;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.dto.sysuser.SysUserDTO;

import java.util.List;
import java.util.Map;

/**
 * 系统用户 service
 * @author asher
 */
public interface SysUserService extends BaseService<SysUser> {

    /**
     * 登录接口
     * @param sysUserDTO
     * @return map
     */
    Result<Map<String,Object>> login(SysUserDTO sysUserDTO);

    void save(Integer isDelete,String id);

    void initOfficeAccounts();

}