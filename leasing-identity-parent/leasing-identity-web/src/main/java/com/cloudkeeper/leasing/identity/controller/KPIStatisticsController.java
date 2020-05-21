package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.identity.dto.kpistatistics.KPIStatisticsDTO;
import com.cloudkeeper.leasing.identity.dto.kpistatistics.KPIStatisticsSearchable;
import com.cloudkeeper.leasing.identity.vo.KPIStatisticsVO;
import com.cloudkeeper.leasing.base.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 双向印证 controller
 * @author yujian
 */
@Api(value = "双向印证", tags = "双向印证")
@RequestMapping("/kPIStatistics")
public interface KPIStatisticsController {

    /**
     * 查询
     * @param id 双向印证id
     * @return 双向印证 VO
     */
    @ApiOperation(value = "查询", notes = "查询", position = 1)
    @GetMapping("/{id}id")
    Result<KPIStatisticsVO> findOne(@ApiParam(value = "双向印证id", required = true) @PathVariable String id);

    /**
     * 新增
     * @param kPIStatisticsDTO 双向印证 DTO
     * @return 双向印证 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/")
    Result<KPIStatisticsVO> add(@ApiParam(value = "双向印证 DTO", required = true) @RequestBody @Validated KPIStatisticsDTO kPIStatisticsDTO);

    /**
     * 更新
     * @param id 双向印证id
     * @param kPIStatisticsDTO 双向印证 DTO
     * @return 双向印证 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @PutMapping("/{id}id")
    Result<KPIStatisticsVO> update(@ApiParam(value = "双向印证id", required = true) @PathVariable String id,
        @ApiParam(value = "双向印证 DTO", required = true) @RequestBody @Validated KPIStatisticsDTO kPIStatisticsDTO);

    /**
     * 删除
     * @param id 双向印证id
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "删除", position = 4)
    @DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "双向印证id", required = true) @PathVariable String id);

    /**
     * 列表查询
     * @param kPIStatisticsSearchable 双向印证查询条件
     * @param sort 排序条件
     * @return 双向印证 VO 集合
     */
    @ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/list")
    Result<List<KPIStatisticsVO>> list(@ApiParam(value = "双向印证查询条件", required = true) @RequestBody KPIStatisticsSearchable kPIStatisticsSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort);

    /**
     * 分页查询
     * @param kPIStatisticsSearchable 双向印证查询条件
     * @param pageable 分页条件
     * @return 双向印证 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
    @PostMapping("/page")
    Result<Page<KPIStatisticsVO>> page(@ApiParam(value = "双向印证查询条件", required = true) @RequestBody KPIStatisticsSearchable kPIStatisticsSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable);

    @ApiOperation(value = "初始化", notes = "初始化", position = 5)
    @GetMapping("/generateKpiResult")
    Result<Boolean> generateKpiResult(@Nonnull String taskId);

}
