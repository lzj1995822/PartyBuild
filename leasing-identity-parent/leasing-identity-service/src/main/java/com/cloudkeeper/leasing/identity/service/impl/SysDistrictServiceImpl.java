package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.identity.dto.sysdistrict.SysDistrictSearchable;
import com.cloudkeeper.leasing.identity.repository.SysDistrictRepository;
import com.cloudkeeper.leasing.identity.service.SysDistrictService;
import com.cloudkeeper.leasing.identity.vo.SysDistrictTreeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 组织 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysDistrictServiceImpl extends BaseServiceImpl<SysDistrict> implements SysDistrictService {

    private static final String ROOT_DISTRICT_ID = "01";

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
                .withMatcher("districtType", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("description", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtId", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("location", ExampleMatcher.GenericPropertyMatchers.contains());
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

    @Override
    public Set<SysDistrictTreeVO> tree(String sysDistrictId) {
        Set<SysDistrict> sysDistrictTreeVOS = sysDistrictRepository.findAllByAttachTo(sysDistrictId);
        if (StringUtils.isEmpty(sysDistrictTreeVOS)) {
            return null;
        }
        return generateTree(sysDistrictTreeVOS);
    }

    /**
     * 生成SysDistrictTreeVO
     * @return
     */
    private Set<SysDistrictTreeVO> generateTree(Set<SysDistrict> sysDistricts) {
        Set<SysDistrictTreeVO> sysDistrictTreeVOSet =  new HashSet<>();
        for (SysDistrict item : sysDistricts) {
            SysDistrictTreeVO itemVo = new SysDistrictTreeVO();
            itemVo.setLabel(item.getDistrictName());
            itemVo.setId(item.getDistrictId());
            if (item.getDistrictLevel().equals(3)) {
                itemVo.setLeaf(true);
            }
            sysDistrictTreeVOSet.add(itemVo);
        }
        return sysDistrictTreeVOSet;
    }

    /**
     * 根据attchTo chaxun
     * @return
     */
    @Override
    public Set<SysDistrict> sysDistrictsByAttachTo(String attachTo){
        Set<SysDistrict> s =  sysDistrictRepository.findAllByAttachTo(attachTo);
        return s;
    }

    @Override
    public Set<SysDistrictTreeVO> getTree(String districtId) {
        SysDistrict sysDistrictByDistrictId = sysDistrictRepository.findSysDistrictByDistrictId(districtId);
        HashSet<SysDistrict> sysDistricts = new HashSet<>();
        sysDistricts.add(sysDistrictByDistrictId);
        return this.translateToVO(sysDistricts);
    }

    @Override
    public List<SysDistrict> findAllByDistrictLevel(Integer level) {
        return sysDistrictRepository.findAllByDistrictLevel(level);
    }

    @Override
    public void save(Integer isDelete, String id) {
        sysDistrictRepository.save(isDelete,id);
    }
    @Override
    public void deleteByDisId(String id){
        sysDistrictRepository.deleteByDisId(id);
    }

    @Override
    public String findMaxId(String districtId) {
        String maxDistrictId = new String();
        if(districtId.length()==2){
            maxDistrictId = sysDistrictRepository.findMaxByDistrictIdAndDistrictLevel(districtId,2);
        }
        if(districtId.length()==4){
            maxDistrictId = sysDistrictRepository.findMaxByDistrictIdAndDistrictLevel(districtId,3);
        }
        maxDistrictId = StringUtils.isEmpty(maxDistrictId) ? districtId+"01":"0"+ (Integer.valueOf(maxDistrictId) + 1);
        return maxDistrictId;
    }

    @Override
    public Integer countAllByDistrictId(String districtId) {
        Integer integer = sysDistrictRepository.countAllByDistrictIdStartingWith(districtId);
        return integer;
    }

    private Set<SysDistrictTreeVO> translateToVO(Set<SysDistrict> sysDistricts) {
        HashSet<SysDistrictTreeVO> sysDistrictTreeVOS = new HashSet<>();
        for (SysDistrict sysDistrict: sysDistricts) {
            SysDistrictTreeVO sysDistrictTreeVO = new SysDistrictTreeVO();
            sysDistrictTreeVO.setId(sysDistrict.getDistrictId());
            sysDistrictTreeVO.setLabel(sysDistrict.getDistrictName());
            //查出下属组织
            Set<SysDistrict> children = sysDistrict.getChildren();
            if (sysDistrict.getDistrictLevel().equals(3)) {
                sysDistrictTreeVO.setLeaf(true);
            }
            if (children.size() > 0) {
                sysDistrictTreeVO.setChildren(translateToVO(children));
            }
            sysDistrictTreeVOS.add(sysDistrictTreeVO);
        }
        return sysDistrictTreeVOS;
    }
    /**
     * 根据id 查询attachId
     * @return
     */
    @Override
    public List<SysDistrict> sysDistrictsByDistrictId(String districtId){
        List<SysDistrict> attach =  sysDistrictRepository.findAllByDistrictId(districtId);
        return attach;
    }

}
