package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.SysUser;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresSearchable;
import com.cloudkeeper.leasing.identity.service.*;
import com.cloudkeeper.leasing.identity.vo.CentralConsoleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CentralConsoleServiceImpl extends BaseServiceImpl<BaseEntity> implements CentralConsoleService {

    //村干部
    private final VillageCadresService villageCadresService;

    //活动执行次数
    private final ParActivityPerformService parActivityPerformService;

    //基本阵地
    private final PositionInformationService positionInformationService;

    //当前用户
    private final SysUserService sysUserService;

    //活动完成率
    private final ParActivityObjectService parActivityObjectService;

   //岗位（通过岗位拿村书记数量）
    private final  CadrePositionService cadrePositionService;

    private final SysDistrictService sysDistrictService;

    private final ParMemberService parMemberService;

    private final SumPerHourService sumPerHourService;


    @Override
    protected BaseRepository<BaseEntity> getBaseRepository() {
        return null;
    }

    @Override
    public CentralConsoleVo dataStatistics(@NonNull String year) {
        String currentPrincipalId = getCurrentPrincipalId();
        if(StringUtils.isEmpty(currentPrincipalId)){
            currentPrincipalId = "1";
        }
        Optional<SysUser> optionalById = sysUserService.findOptionalById(currentPrincipalId);
        CentralConsoleVo centralConsoleVo = new CentralConsoleVo();
        if (optionalById.isPresent()){
            SysUser sysUser = optionalById.get();
            String districtId = sysUser.getDistrictId();
            centralConsoleVo.setParMemberNumber(parMemberService.countAll(districtId));
            centralConsoleVo.setOrganizationNumber(sysDistrictService.countAllByDistrictId(districtId));
            centralConsoleVo.setVillageCadresNumber(villageCadresService.countAllByDistrictId(districtId));
            centralConsoleVo.setActivityPerformNumber(parActivityPerformService.countAll(districtId, year));
            centralConsoleVo.setPositionNumber(positionInformationService.countAllByDistrictId(districtId));
            centralConsoleVo.setActivityCompleteRate(parActivityObjectService.handleActivityCompleteRate(districtId, year));
            centralConsoleVo.setVillageSecretaryNumber(cadrePositionService.countVillageSecretaryNumber(districtId,"SECRETARY"));
            centralConsoleVo.setStreamTotal(sumPerHourService.countAll(districtId));
            centralConsoleVo.setStreamRate((double) (centralConsoleVo.getStreamTotal()/centralConsoleVo.getPositionNumber()));
        }
        return centralConsoleVo;
    }
}
