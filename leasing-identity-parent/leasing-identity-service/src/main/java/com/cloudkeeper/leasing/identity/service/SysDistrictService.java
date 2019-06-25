package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.vo.SysDistrictTreeVO;

import java.util.List;
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
    /**
     * 根据districtId chaxun
     * @return
     */
    List<SysDistrict> sysDistrictsByDistrictId(String districtId);

    /**
     * 生成层级数组
    */
    Set<SysDistrictTreeVO> getTree(String districtId);


    List<SysDistrict> findAllByDistrictLevel(Integer level);

    void save(Integer isDelete,String id);

}
