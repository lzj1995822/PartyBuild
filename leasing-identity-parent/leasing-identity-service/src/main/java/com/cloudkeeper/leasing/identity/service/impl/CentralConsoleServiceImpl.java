package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.SysUser;
import com.cloudkeeper.leasing.identity.service.*;
import com.cloudkeeper.leasing.identity.vo.CentralConsoleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



    @Override
    protected BaseRepository<BaseEntity> getBaseRepository() {
        return null;
    }

    @Override
    public CentralConsoleVo dataStatistics() {
        String currentPrincipalId = getCurrentPrincipalId();
        Optional<SysUser> optionalById = sysUserService.findOptionalById(currentPrincipalId);
        CentralConsoleVo centralConsoleVo = new CentralConsoleVo();
        if (optionalById.isPresent()){
            SysUser sysUser = optionalById.get();
            String districtId = sysUser.getDistrictId();
            centralConsoleVo.setVillageCadresNumber(villageCadresService.countAllByDistrictId(districtId));
            centralConsoleVo.setActivityPerformNumber(parActivityPerformService.countAll(districtId));
            centralConsoleVo.setPositionNumber(positionInformationService.countAllByDistrictId(districtId));
            centralConsoleVo.setActivityCompleteRate(parActivityObjectService.handleActivityCompleteRate(districtId));
        }
        return centralConsoleVo;
    }
}
