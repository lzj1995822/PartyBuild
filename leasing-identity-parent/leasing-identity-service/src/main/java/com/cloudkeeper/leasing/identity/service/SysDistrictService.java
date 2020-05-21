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
    List<SysDistrictTreeVO> getTree(String districtId);


    List<SysDistrict> findAllByDistrictLevelAndDistrictType(Integer level, String districtType);

    void save(Integer isDelete,String id);

    void deleteByDisId(String id);

    String findMaxId(String districtId);

    Integer countAllByDistrictId(String districtId);

    SysDistrict findByDistrictId(String districtId);

    List<SysDistrict> findAllByDistrictIdGreaterThanEqual(String districtId);

    SysDistrict handleRelation(SysDistrict sysDistrict);

    List<SysDistrictTreeVO> findOfficeDistrictTree();

    Integer countAllByDistrictIdStartingWithAndDistrictNameContains(String districtId, String districtName);

    List<SysDistrict> findAllTowns();

    List<SysDistrict> findAllVillages();
}
