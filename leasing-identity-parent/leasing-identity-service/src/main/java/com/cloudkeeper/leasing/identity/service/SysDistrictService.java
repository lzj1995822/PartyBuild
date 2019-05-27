package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.base.service.BaseService;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

/**
 * 组织 service
 * @author lxw
 */
public interface SysDistrictService extends BaseService<SysDistrict> {

    Map<String,String> findAllByDistrictLevelNot();
}