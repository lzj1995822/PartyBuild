package com.cloudkeeper.leasing.identity.controller;


import com.cloudkeeper.leasing.base.model.CloudResult;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Api(value = "云平台", tags = "云平台")
@RequestMapping("/cloudStatistics")
public interface CloudStatisticsController {

    @ApiOperation(value = "党组织数量", notes = "党组织数量", position = 1)
    @PostMapping("/organizationNumber")
    CloudResult<Integer> countOrganizationNumber();

    @ApiOperation(value = "党员", notes = "党员数量", position = 1)
    @PostMapping("/partyMemberNumber")
    CloudResult<Integer> countPartyMemberNumber();

    @ApiOperation(value = "村干部", notes = "村干部", position = 1)
    @PostMapping("/villageCadresNumber")
    CloudResult<Long> countVillageCadresNumber();

    @ApiOperation(value = "村书记", notes = "村书记", position = 1)
    @PostMapping("/villageSecretaryNumber")
    CloudResult<Integer> countVillageSecretaryNumber();

    @ApiOperation(value = "活动执行次数", notes = "活动执行次数", position = 1)
    @PostMapping("/activityPerformNumber")
    CloudResult<Integer> countActivityPerformNumber();

    @ApiOperation(value = "活动完成次数", notes = "活动完成次数", position = 1)
    @PostMapping("/activityFinishedNumber")
    CloudResult<Integer> countActivityFinishedNumber();

    @ApiOperation(value = "活动完成率", notes = "活动完成率", position = 1)
    @PostMapping("/activityCompleteRate")
    CloudResult<ActivityDashboardFormatVo> countActivityCompleteRate();

    @ApiOperation(value = "当月通知公告和活动", notes = "当月通知公告和活动", position = 1)
    @PostMapping("/currentActivity")
    CloudResult<Map<String, Object>> listCurrent();

    @ApiOperation(value = "年度村排名", notes = "年度村排名", position = 1)
    @PostMapping("/cunRanking")
    CloudResult<Map<String, Object>> cunRanking();

    @ApiOperation(value = "年度镇排名", notes = "年度镇排名", position = 1)
    @PostMapping("/townRanking")
    CloudResult<Map<String, Object>> townRanking();

    @ApiOperation(value = "运行多少天", notes = "运行多少天", position = 1)
    @PostMapping("/runDays")
    CloudResult<Integer> runDays() throws ParseException;

    @ApiOperation(value = "月度镇活动完成率", notes = "月度镇活动完成率", position = 1)
    @PostMapping("/townMonthRate")
    Result<List<CloudActivityRateVO>> townMonthRate() ;

    @ApiOperation(value = "月度村活动完成情况", notes = "月度村活动完成情况", position = 1)
    @PostMapping("/cunMonthObject")
    Result<List<CloudActivityCunFinishedVO>> cunMonthObject(String attachTo) ;

    @ApiOperation(value = "今日活动执行次数", notes = "今日活动执行次数", position = 1)
    @PostMapping("/activityExecuteNumber")
    CloudResult<Integer> countActivityExecuteNumber();

    @ApiOperation(value = "今日活动审核通过个数", notes = "今日活动审核通过个数", position = 1)
    @PostMapping("/activityPassNumber")
    CloudResult<Integer> countActivityPassNumber();

    @ApiOperation(value = "今日活动正在执行个数", notes = "今日活动正在执行个数", position = 1)
    @PostMapping("/activityIsWorkingNumber")
    CloudResult<Integer> countActivityIsWorkingNumber();

    @ApiOperation(value = "活动log", notes = "活动log", position = 1)
    @PostMapping("/getCloudLog")
    CloudResult<Map<String, Object>> getCloudLog();

}
