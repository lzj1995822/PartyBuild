package com.cloudkeeper.leasing.identity.service.impl;


import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.PositionInformation;
import com.cloudkeeper.leasing.identity.dto.positioninformation.PositionInformationSearchable;
import com.cloudkeeper.leasing.identity.service.GisService;
import com.cloudkeeper.leasing.identity.service.PositionInformationService;
import com.cloudkeeper.leasing.identity.service.VillageCadresService;
import com.cloudkeeper.leasing.identity.vo.GisVO;
import com.cloudkeeper.leasing.identity.vo.PositionInformationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GisServiceImpl extends BaseServiceImpl<BaseEntity> implements GisService {

    private final PositionInformationService positionInformationService;

    private final VillageCadresService villageCadresService;

    @Override
    protected BaseRepository<BaseEntity> getBaseRepository() { return null; }

    @Override
    public List<GisVO> queryPosition() {
        List<PositionInformation> all = positionInformationService.findAll();
        List<PositionInformationVO> voList = PositionInformation.convert(all, PositionInformationVO.class);
        List<GisVO> list = new ArrayList<>();
        PositionInformationSearchable searchable = new PositionInformationSearchable();
        for(int i=0;i<voList.size();i++){
            searchable.setDistrictId(voList.get(i).getDistrictId());
            searchable.setType("SECRETARY");
            GisVO gisVO = new GisVO();
            gisVO.setId(voList.get(i).getId());
            gisVO.setPositionName(voList.get(i).getName());
            gisVO.setPositionType(voList.get(i).getType());
            gisVO.setPositionDistrictId(voList.get(i).getDistrictId());
            gisVO.setPositionDistrictName(voList.get(i).getDistrictName());
            gisVO.setPositionIntroduction(StringUtils.isEmpty(voList.get(i).getIntroduction())? "":voList.get(i).getIntroduction());
            gisVO.setPositionLonLat(voList.get(i).getLonLat());
            gisVO.setPositionCadresNumber(villageCadresService.countAllByDistrictId(voList.get(i).getDistrictId()));
            gisVO.setPositionCadreName(villageCadresService.findAll(searchable).size()==0 ? "": villageCadresService.findAll(searchable).get(0).getName());
            list.add(gisVO);
        }
        return list;
    }
}
