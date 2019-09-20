package com.cloudkeeper.leasing.identity.controller.impl;


import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.base.model.CloudResult;
import com.cloudkeeper.leasing.identity.controller.CloudStatisticsController;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.identity.service.*;
import com.cloudkeeper.leasing.identity.vo.CentralConsoleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * 云平台
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CloudStatisticsControllerImpl implements CloudStatisticsController {


    private final SysDistrictService sysDistrictService;

    private final VillageCadresService villageCadresService;

    private final ParActivityPerformService parActivityPerformService;

    private final CadrePositionService cadrePositionService;

    private final ParMemberService parMemberService;

    @Authorization(required = false)
    @Override
    public CloudResult<Integer> countOrganizationNumber() {
        Integer integer = sysDistrictService.countAllByDistrictId("01");
        return CloudResult.of(integer);
    }

    @Authorization(required = false)
    @Override
    public CloudResult<Integer> countPartyMemberNumber() {
        Integer integer = parMemberService.countAll("01");
        return CloudResult.of(integer);
    }

    @Authorization(required = false)
    @Override
    public CloudResult<Long> countVillageCadresNumber() {
        Long aLong = villageCadresService.countAllByDistrictId("01");
        return CloudResult.of(aLong);
    }

    @Authorization(required = false)
    @Override
    public CloudResult<Integer> countVillageSecretaryNumber() {
        Integer integer = cadrePositionService.countVillageSecretaryNumber("01", "SECRETARY");
        return CloudResult.of(integer);
    }

    @Authorization(required = false)
    @Override
    public CloudResult<Integer> countActivityPerformNumber() {
        Integer integer = parActivityPerformService.countAllByStatus("");
        return  CloudResult.of(integer);
    }

    @Authorization(required = false)
    @Override
    public CloudResult<Integer> countActivityFinishedNumber() {
        Integer integer = parActivityPerformService.countAllByStatus("2");
        return  CloudResult.of(integer);
    }

    @Authorization(required = false)
    @Override
    public CloudResult<BigDecimal> countActivityCompleteRate() {
        Integer integer = parActivityPerformService.countAllByStatus("");
        Integer integer1 = parActivityPerformService.countAllByStatus("2");
        float rate = (float)integer1/integer;
        DecimalFormat df = new DecimalFormat("0.0000");//保留4位小数
        String activityCompleteRate = df.format(rate)+"%";
        return CloudResult.of(activityCompleteRate);
    }
}
