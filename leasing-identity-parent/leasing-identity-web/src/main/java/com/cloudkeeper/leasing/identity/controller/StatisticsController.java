package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.vo.StatisticsClassifyVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsListVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsVO;
import com.cloudkeeper.leasing.identity.vo.SumPerHourVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Api(value = "专职村主任统计", tags = "专职村主任统计")
@RequestMapping("/statistics")
public interface StatisticsController {


    /**
     * 男女比例统计
     * @param districtId 组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "男女比例统计", notes = "男女比例统计", position = 1)
    @GetMapping("/getSexStatistics/{districtId}")
    Result<List<StatisticsVO>> getSxStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable String districtId);

    /**
     * 年龄结构分析
     * @param districtId 组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "年龄结构分析", notes = "年龄结构分析", position = 1)
    @GetMapping("/getAgeStatistics/{districtId}")
    Result<List<StatisticsVO>> getAgeStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable String districtId);

    /**
     * 学历结构分析
     * @param districtId 组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "学历结构分析", notes = "学历结构分析", position = 1)
    @GetMapping("/getEduStatistics/{districtId}")
    Result<List<StatisticsVO>> getEduStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable String districtId);

    /**
     * 人员类型分析
     * @param districtId 组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "人员类型分析", notes = "人员类型分析", position = 1)
    @GetMapping("/getcadresTypeStatistics/{districtId}")
    Result<List<StatisticsVO>> getcadresTypeStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable String districtId);

    /**
     * 任职年限分析
     * @param districtId 组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "任职年限分析", notes = "任职年限分析", position = 1)
    @GetMapping("/getServingYearStatistics/{districtId}")
    Result<List<StatisticsVO>> getServingYearStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable String districtId);

    /**
     * 职级情况分析
     * @param districtId 组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "职级情况分析", notes = "职级情况分析", position = 1)
    @GetMapping("/getRankStatistics/{districtId}")
    Result<List<StatisticsVO>> getRankStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable String districtId);

    /**
     * 报酬结构分析
     * @param districtId 组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "报酬结构分析", notes = "报酬结构分析", position = 1)
    @GetMapping("/getSalaryStatistics/{districtId}")
    Result<List<StatisticsClassifyVO>> getSalaryStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable String districtId);



    /**
     * 党龄情况分析
     * @param districtId 组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "党龄情况分析", notes = "党龄情况分析", position = 1)
    @GetMapping("/getPartyStandingStatistics/{districtId}")
    Result<List<StatisticsVO>> getPartyStandingStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable String districtId);

    /**
     * 奖惩情况分析
     * @param districtId 组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "奖惩情况分析", notes = "奖惩情况分析", position = 1)
    @GetMapping("/getRewardsStatistics/{districtId}")
    Result<List<StatisticsListVO>> getRewardsStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable String districtId);

}