package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.vo.SysDistrictTreeVO;

import java.util.Map;
import java.util.Set;

/**
 * 组织 service
 * @author lxw
 */
public interface SysDistrictService extends BaseService<SysDistrict> {

    Map<String,String> findAllByDistrictLevelNot();

    /**
     * 树形结构
     * @return
     */
    Set<SysDistrictTreeVO> tree(String sysDistrictId);
    /**
     * 根据attchTo chaxun
     * @return
     */
    Set<SysDistrict> sysDistrictsByAttachTo(String attachTo);
}
