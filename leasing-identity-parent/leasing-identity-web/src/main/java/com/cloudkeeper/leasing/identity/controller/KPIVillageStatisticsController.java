package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.domain.KPIVillageStatistics;
import com.cloudkeeper.leasing.identity.dto.kpivillagestatistics.KPIVillageStatisticsDTO;
import com.cloudkeeper.leasing.identity.dto.kpivillagestatistics.KPIVillageStatisticsSearchable;
import com.cloudkeeper.leasing.identity.vo.KPIVillageStatisticsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 村一级指标统计 controller
 * @author yujian
 */
@Api(value = "村一级指标统计", tags = "村一级指标统计")
@RequestMapping("/kPIVillageStatistics")
public interface KPIVillageStatisticsController {

    /**
     * 查询
     * @param id 村一级指标统计id
     * @return 村一级指标统计 VO
     */
    @ApiOperation(value = "查询", notes = "查询", position = 1)
    @GetMapping("/{id}id")
    Result<KPIVillageStatisticsVO> findOne(@ApiParam(value = "村一级指标统计id", required = true) @PathVariable String id);

    /**
     * 新增
     * @param kPIVillageStatisticsDTO 村一级指标统计 DTO
     * @return 村一级指标统计 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/")
    Result<KPIVillageStatisticsVO> add(@ApiParam(value = "村一级指标统计 DTO", required = true) @RequestBody @Validated KPIVillageStatisticsDTO kPIVillageStatisticsDTO);

    /**
     * 更新
     * @param id 村一级指标统计id
     * @param kPIVillageStatisticsDTO 村一级指标统计 DTO
     * @return 村一级指标统计 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @PutMapping("/{id}id")
    Result<KPIVillageStatisticsVO> update(@ApiParam(value = "村一级指标统计id", required = true) @PathVariable String id,
        @ApiParam(value = "村一级指标统计 DTO", required = true) @RequestBody @Validated KPIVillageStatisticsDTO kPIVillageStatisticsDTO);

    /**
     * 删除
     * @param id 村一级指标统计id
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "删除", position = 4)
    @DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "村一级指标统计id", required = true) @PathVariable String id);

    /**
     * 列表查询
     * @param kPIVillageStatisticsSearchable 村一级指标统计查询条件
     * @param sort 排序条件
     * @return 村一级指标统计 VO 集合
     */
    @ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/list")
    Result<List<KPIVillageStatisticsVO>> list(@ApiParam(value = "村一级指标统计查询条件", required = true) @RequestBody KPIVillageStatisticsSearchable kPIVillageStatisticsSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort);

    /**
     * 分页查询
     * @param kPIVillageStatisticsSearchable 村一级指标统计查询条件
     * @param pageable 分页条件
     * @return 村一级指标统计 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
    @PostMapping("/page")
    Result<Page<KPIVillageStatisticsVO>> page(@ApiParam(value = "村一级指标统计查询条件", required = true) @RequestBody KPIVillageStatisticsSearchable kPIVillageStatisticsSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable);

    @ApiOperation(value = "设置统计", notes = "设置统计", position = 6)
    @PostMapping("/generateStatistics")
    Result<Boolean> generateStatistics(String taskId);


    @ApiOperation(value = "导入文件", notes = "导入文件", position = 6)
    @RequestMapping("/importExcel")
    @Authorization(required = false)
    Object importExcel(@RequestParam("file") MultipartFile file);


    @ApiOperation(value = "获取统计数据", notes = "获取统计数据", position = 6)
    @GetMapping("/getStatistics")
    Result<Object> getStatistics(String taskYear);

    @ApiOperation(value = "获取平均值一下数量", notes = "获取平均值一下数量", position = 6)
    @GetMapping("/getStatisticsOnAverage/{quotaId}")
    Result<Object> getStatisticsOnAverage(@PathVariable String quotaId);


    @ApiOperation(value = "获取平均值一下数量", notes = "获取平均值一下数量", position = 6)
    @GetMapping("/getExcellent")
    Result<List<KPIVillageStatistics>> getExcellent(String taskYear);
}
