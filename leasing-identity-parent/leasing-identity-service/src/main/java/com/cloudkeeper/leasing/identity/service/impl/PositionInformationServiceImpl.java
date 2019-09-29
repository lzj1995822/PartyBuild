package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.PositionInformation;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.identity.repository.PositionInformationRepository;
import com.cloudkeeper.leasing.identity.repository.SysDistrictRepository;
import com.cloudkeeper.leasing.identity.service.PositionInformationService;
import com.cloudkeeper.leasing.identity.vo.PassPercentVO;
import com.cloudkeeper.leasing.identity.vo.PositionNumberTypeVO;
import com.cloudkeeper.leasing.identity.vo.PositionNumberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 阵地信息 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PositionInformationServiceImpl extends BaseServiceImpl<PositionInformation> implements PositionInformationService {

    /** 阵地信息 repository */
    private final PositionInformationRepository positionInformationRepository;

    private final SysDistrictRepository sysDistrictRepository;

    @Override
    protected BaseRepository<PositionInformation> getBaseRepository() {
        return positionInformationRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtId", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("facilities", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("area", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("introduction", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("pictures", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("hotDegree", ExampleMatcher.GenericPropertyMatchers.contains());
    }


    @Override
    public Integer countAllByDistrictId(String districtId) {
        Integer integer = positionInformationRepository.countAllByDistrictIdStartingWith(districtId);
        return integer;
    }
    @Override
    public PositionNumberVO positionNumber(String districId){
        SysDistrict sysDistrict = sysDistrictRepository.findByDistrictId(districId);
        if(StringUtils.isEmpty(sysDistrict)){
            return null;
        }
        if(sysDistrict.getDistrictLevel() == 2){
            String sql="SELECT *,count(*) number from (" +
                    "SELECT type from (" +
                    "SELECT s.attachTo,s.districtId,p.type from Position_Information p  LEFT join SYS_District s on p.districtId = s.districtId" +
                    ") tb where tb.attachTo = '"+districId+"'"+
                    ") tb3 GROUP BY tb3.type";
            return getNumberSql(sql,districId);
        }else if(sysDistrict.getDistrictLevel() == 3){
            String sql="SELECT *,count(*) number from (" +
                    "SELECT type from (" +
                    "SELECT s.attachTo,s.districtId,p.type from Position_Information p  LEFT join SYS_District s on p.districtId = s.districtId" +
                    ") tb where tb.districtId = '"+districId+"'" +
                    "" +
                    ") tb3 GROUP BY tb3.type";
            return getNumberSql(sql,districId);
        }else {
            String sql = "SELECT *,count(*) number from (" +
                    "SELECT p.type from Position_Information p  LEFT join SYS_District s on p.districtId = s.districtId) tb3 GROUP BY tb3.type";
            return getNumberSql(sql,districId);
        }
    }
    //positionNumber SQl方法
    private PositionNumberVO getNumberSql(String sql,String districtId){
        PositionNumberVO positionNumberVO = new PositionNumberVO();
        List<PositionNumberTypeVO> list= new ArrayList<>();
        list = super.findAllBySql(PositionNumberTypeVO.class, sql);
        for(int i = 0;i < list.size();i++){
            if(list.get(i).getType().equals("PARTY_CARE")){
                positionNumberVO.setPartyCare(list.get(i).getNumber());
            }
            else if(list.get(i).getType().equals("MEMBER_EDUCATION")){
                positionNumberVO.setMemberEducation(list.get(i).getNumber());
            }
            else if(list.get(i).getType().equals("ORGANIZATIONAL_CONFERENCE")){
                positionNumberVO.setOrganizationalConference(list.get(i).getNumber());
            }
            else if(list.get(i).getType().equals("PARTY_STUDIO")){
                positionNumberVO.setPartyStudio(list.get(i).getNumber());
            }
            else if(list.get(i).getType().equals("HALL")){
                positionNumberVO.setHall(list.get(i).getNumber());
            }
            else if(list.get(i).getType().equals("COLUMN")){
                positionNumberVO.setColumn(list.get(i).getNumber());
            }
            else if(list.get(i).getType().equals("SQUARE")){
                positionNumberVO.setSquare(list.get(i).getNumber());
            }
        }
        return positionNumberVO;
    }
}
