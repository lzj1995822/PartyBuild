package com.cloudkeeper.leasing.identity.controller;


import com.cloudkeeper.leasing.base.model.CloudResult;
import com.cloudkeeper.leasing.identity.vo.CentralConsoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Api(value = "云平台", tags = "云平台")
@RequestMapping("/cloudStatistics")
public interface CloudStatisticsController {

    @ApiOperation(value = "党组织数量", notes = "党组织数量", position = 1)
    @PostMapping("/organizationNumber")
    CloudResult<Integer> organizationNumber();

    @ApiOperation(value = "党员", notes = "党员数量", position = 1)
    @PostMapping("/vartyMemberNumber")
    CloudResult<Integer> partyMemberNumber();

    @ApiOperation(value = "村干部", notes = "村干部", position = 1)
    @PostMapping("/villageCadresNumber")
    CloudResult<Long> villageCadresNumber();

    @ApiOperation(value = "村书记", notes = "村书记", position = 1)
    @PostMapping("/villageSecretaryNumber")
    CloudResult<Integer> villageSecretaryNumber();

    @ApiOperation(value = "活动执行次数", notes = "活动执行次数", position = 1)
    @PostMapping("/activityPerformNumber")
    CloudResult<Integer> activityPerformNumber();

    @ApiOperation(value = "活动完成次数", notes = "活动完成次数", position = 1)
    @PostMapping("/activityFinishedNumber")
    CloudResult<Integer> activityFinishedNumber();

    @ApiOperation(value = "活动完成率", notes = "活动完成率", position = 1)
    @PostMapping("/partyMemberNumber")
    CloudResult<BigDecimal> activityCompleteRate();

}
