package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.identity.dto.sumperhour.SumPerHourDTO;
import com.cloudkeeper.leasing.identity.dto.sumperhour.SumPerHourSearchable;
import com.cloudkeeper.leasing.identity.vo.HeatMapVO;
import com.cloudkeeper.leasing.identity.vo.SumPerHourVO;
import com.cloudkeeper.leasing.base.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 每小时人流量 controller
 * @author asher
 */
@Api(value = "每小时人流量", tags = "每小时人流量")
@RequestMapping("/sumPerHour")
public interface SumPerHourController {

    /**
     * 查询
     * @param id 每小时人流量id
     * @return 每小时人流量 VO
     */
    @ApiOperation(value = "查询", notes = "查询", position = 1)
    @GetMapping("/{id}id")
    Result<SumPerHourVO> findOne(@ApiParam(value = "每小时人流量id", required = true) @PathVariable String id);

    /**
     * 新增
     * @param sumPerHourDTO 每小时人流量 DTO
     * @return 每小时人流量 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/")
    Result<SumPerHourVO> add(@ApiParam(value = "每小时人流量 DTO", required = true) @RequestBody @Validated SumPerHourDTO sumPerHourDTO);

    /**
     * 更新
     * @param id 每小时人流量id
     * @param sumPerHourDTO 每小时人流量 DTO
     * @return 每小时人流量 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @PutMapping("/{id}id")
    Result<SumPerHourVO> update(@ApiParam(value = "每小时人流量id", required = true) @PathVariable String id,
        @ApiParam(value = "每小时人流量 DTO", required = true) @RequestBody @Validated SumPerHourDTO sumPerHourDTO);

    /**
     * 删除
     * @param id 每小时人流量id
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "删除", position = 4)
    @DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "每小时人流量id", required = true) @PathVariable String id);

    /**
     * 列表查询
     * @param sumPerHourSearchable 每小时人流量查询条件
     * @param sort 排序条件
     * @return 每小时人流量 VO 集合
     */
    @ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/list")
    Result<List<SumPerHourVO>> list(@ApiParam(value = "每小时人流量查询条件", required = true) @RequestBody SumPerHourSearchable sumPerHourSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort);

    /**
     * 分页查询
     * @param sumPerHourSearchable 每小时人流量查询条件
     * @param pageable 分页条件
     * @return 每小时人流量 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
    @PostMapping("/page")
    Result<Page<SumPerHourVO>> page(@ApiParam(value = "每小时人流量查询条件", required = true) @RequestBody SumPerHourSearchable sumPerHourSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable);


    @ApiOperation(value = "热力图数据")
    @PostMapping("/getHeatMapData")
    Result<List<HeatMapVO>> getHeatMapData(@RequestBody SumPerHourDTO sumPerHourDTO);

}