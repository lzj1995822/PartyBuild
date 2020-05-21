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
                .withMatcher("location", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("orgParent", ExampleMatcher.GenericPropertyMatchers.contains());
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
    public List<SysDistrictTreeVO> getTree(String districtId) {
        SysDistrict sysDistrictByDistrictId = sysDistrictRepository.findSysDistrictByDistrictId(districtId);
        List<SysDistrict> sysDistricts = new ArrayList<>();
        sysDistricts.add(sysDistrictByDistrictId);
        return this.translateToVO(sysDistricts, null);
    }

    @Override
    public List<SysDistrict> findAllByDistrictLevelAndDistrictType(Integer level, String districtType) {
        return sysDistrictRepository.findAllByDistrictLevelAndDistrictTypeOrderByDistrictIdAsc(level, districtType);
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
        int length = districtId.length();
        maxDistrictId = sysDistrictRepository.findMaxByDistrictIdAndDistrictLevel(districtId,length/2 + 1);
        maxDistrictId = StringUtils.isEmpty(maxDistrictId) ? districtId+"01":"0"+ (Long.valueOf(maxDistrictId) + 1);
        return maxDistrictId;
    }

    @Override
    public Integer countAllByDistrictId(String districtId) {
        Integer integer = sysDistrictRepository.countAllByDistrictIdStartingWithAndIsPartyGroup(districtId, "0");
        return integer;
    }

    @Override
    public SysDistrict findByDistrictId(String districtId) {
        return sysDistrictRepository.findByDistrictId(districtId);
    }

    @Override
    public List<SysDistrict> findAllByDistrictIdGreaterThanEqual(String districtId) {
        return sysDistrictRepository.findAllByDistrictIdGreaterThanEqual(districtId);
    }

    @Override
    public SysDistrict handleRelation(SysDistrict sysDistrict) {
        SysDistrict byDistrictId = findByDistrictId(sysDistrict.getAttachTo());
        if (byDistrictId != null) {
            sysDistrict.setParentName(byDistrictId.getDistrictName());
        }
        SysDistrict byDistrictId1 = findByDistrictId(sysDistrict.getOrgParent());
        if (byDistrictId1 != null) {
            sysDistrict.setOrgParentName(byDistrictId1.getDistrictName());
        }
        return sysDistrict;
    }

    private List<SysDistrictTreeVO> translateToVO(List<SysDistrict> sysDistricts, String parentId) {
        List<SysDistrictTreeVO> sysDistrictTreeVOS = new ArrayList<>();
        for (SysDistrict sysDistrict: sysDistricts) {
            if (!StringUtils.isEmpty(parentId) && parentId.equals(sysDistrict.getId())) {
                continue;
            }
            SysDistrictTreeVO sysDistrictTreeVO = new SysDistrictTreeVO();
            sysDistrictTreeVO.setId(sysDistrict.getDistrictId());
            sysDistrictTreeVO.setLabel(sysDistrict.getDistrictName());
            //查出下属组织
            List<SysDistrict> children = sysDistrict.getOrgChildren();
            if (children.size() == 0) {
                sysDistrictTreeVO.setLeaf(true);
            }
            if (children.size() > 0) {
                sysDistrictTreeVO.setChildren(translateToVO(children, sysDistrict.getId()));
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

    @Override
    public List<SysDistrictTreeVO> findOfficeDistrictTree() {
        List<SysDistrict> office = sysDistrictRepository.findAllByDistrictLevelAndDistrictTypeOrderByDistrictIdAsc(2, "Office");
        List<SysDistrictTreeVO> sysDistrictTreeVOS = translateToVO(office, null);

        ArrayList<SysDistrictTreeVO> resList = new ArrayList<>();
        ArrayList<SysDistrictTreeVO> partyList  = new ArrayList<>();
        for (SysDistrictTreeVO item : sysDistrictTreeVOS) {
            if (!item.getId().equals("0118")) {
                partyList.add(item);
            } else {
                resList.add(item);
            }
        }
        SysDistrictTreeVO sysDistrictTreeVO = new SysDistrictTreeVO();
        sysDistrictTreeVO.setId("10");
        sysDistrictTreeVO.setLabel("党委");
        sysDistrictTreeVO.setChildren(partyList);
        resList.add(sysDistrictTreeVO);
        return resList;
    }

    @Override
    public Integer countAllByDistrictIdStartingWithAndDistrictNameContains(String districtId, String districtName) {
        return sysDistrictRepository.countAllByDistrictIdStartingWithAndDistrictNameContains(districtId, districtName);
    }

    @Override
    public List<SysDistrict> findAllTowns() {
        return sysDistrictRepository.findAllByDistrictTypeAndDistrictLevel("Party", 2);
    }

    @Override
    public List<SysDistrict> findAllVillages() {
        return sysDistrictRepository.findAllByDistrictTypeAndDistrictLevel("Party", 3);
    }
}
