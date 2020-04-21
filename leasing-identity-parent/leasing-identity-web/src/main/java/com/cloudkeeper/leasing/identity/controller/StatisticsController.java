package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.dto.villagecadres.ExportDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresStatisticsSearchable;
import com.cloudkeeper.leasing.identity.vo.StatisticsClassifyVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsListVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsNotIntegerVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/getSexStatistics/{districtId}/{cadresType}")
    Result<List<StatisticsVO>> getSxStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable("districtId") String districtId,@PathVariable("cadresType") String cadresType);

    /**
     * 年龄结构分析
     * @param districtId 组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "年龄结构分析", notes = "年龄结构分析", position = 1)
    @GetMapping("/getAgeStatistics/{districtId}/{cadresType}")
    Result<List<StatisticsVO>> getAgeStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable("districtId") String districtId,@PathVariable("cadresType") String cadresType);

    /**
     * 学历结构分析
     * @param districtId 组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "学历结构分析", notes = "学历结构分析", position = 1)
    @GetMapping("/getEduStatistics/{districtId}/{cadresType}")
    Result<List<StatisticsVO>> getEduStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable("districtId") String districtId,@PathVariable("cadresType") String cadresType);

    /**
     * 人员类型分析
     * @param districtId 组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "人员类型分析", notes = "人员类型分析", position = 1)
    @GetMapping("/getcadresTypeStatistics/{districtId}/{cadresType}")
    Result<List<StatisticsVO>> getcadresTypeStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable("districtId") String districtId,@PathVariable("cadresType") String cadresType);

    /**
     * 任职年限分析
     * @param districtId 组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "任职年限分析", notes = "任职年限分析", position = 1)
    @GetMapping("/getServingYearStatistics/{districtId}/{cadresType}")
    Result<List<StatisticsVO>> getServingYearStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable("districtId") String districtId,@PathVariable("cadresType") String cadresType);

    /**
     * 职级情况分析
     * @param districtId 组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "职级情况分析", notes = "职级情况分析", position = 1)
    @GetMapping("/getRankStatistics/{districtId}/{cadresType}")
    Result<List<StatisticsVO>> getRankStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable("districtId") String districtId,@PathVariable("cadresType") String cadresType);

    /**
     * 报酬结构分析
     * @param districtId 组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "报酬结构分析", notes = "报酬结构分析", position = 1)
    @GetMapping("/getSalaryStatistics/{districtId}/{cadresType}")
    Result<List<StatisticsClassifyVO>> getSalaryStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable("districtId") String districtId,@PathVariable("cadresType") String cadresType);

    /**
     * 报酬结构分析
     * @param  组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "报酬结构列表", notes = "报酬结构列表", position = 1)
    @GetMapping("/getSalaryStatisticsList/{type}/{cadresType}")
    Result<List<StatisticsNotIntegerVO>> getSalaryStatisticsList(@ApiParam(value = "组织ID", required = true) @PathVariable("type") String type,@PathVariable("cadresType") String cadresType);

    /**
     * 党龄情况分析
     * @param districtId 组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "党龄情况分析", notes = "党龄情况分析", position = 1)
    @GetMapping("/getPartyStandingStatistics/{districtId}/{cadresType}")
    Result<List<StatisticsVO>> getPartyStandingStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable("districtId") String districtId,@PathVariable("cadresType") String cadresType);

    /**
     * 奖惩情况分析
     * @param districtId 组织ID
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "奖惩情况分析", notes = "奖惩情况分析", position = 1)
    @GetMapping("/getRewardsStatistics/{districtId}/{cadresType}")
    Result<List<StatisticsListVO>> getRewardsStatistics(@ApiParam(value = "组织ID", required = true) @PathVariable("districtId") String districtId,@PathVariable("cadresType") String cadresType);

    /**
     * 获取所有统计信息
     * @param
     * @return 专职村主任统计 VO
     */
    @ApiOperation(value = "获取所有统计信息", notes = "获取所有统计信息", position = 1)
    @GetMapping("/getAllStatistics")
    Result<List<StatisticsVO>> getAllStatistics();

    @ApiOperation(value = "自定义查询", notes = "自定义查询", position = 1)
    @PostMapping("/getCustomStatistics")
    Result<Object> getCustomStatistics(@ApiParam(value = "查询参数", required = true)@RequestBody  List<VillageCadresStatisticsSearchable> villageCadresStatisticsSearchables);

    @ApiOperation(value = "自定义查询", notes = "自定义查询", position = 1)
    @PostMapping("/page")
    Result<Object> page(@ApiParam(value = "查询参数", required = true)@RequestBody  List<VillageCadresStatisticsSearchable> villageCadresStatisticsSearchables
    , @ApiParam(value = "分页参数", required = true) @RequestParam Integer page, @RequestParam Integer size, Pageable pageable);

    @ApiOperation(value = "导出", notes = "导出", position = 1)
    @PostMapping("/export")
    Result<Object> export(@RequestBody ExportDTO exportDTO);
}
