package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.dto.kpiquota.KpiQuotaDTO;
import com.cloudkeeper.leasing.identity.dto.kpitownquota.KPITownQuotaDTO;
import com.cloudkeeper.leasing.identity.dto.kpitownquota.KPITownQuotaSearchable;
import com.cloudkeeper.leasing.identity.vo.KPITownQuotaVO;
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
 * 镇考核指标 controller
 * @author yujian
 */
@Api(value = "镇考核指标", tags = "镇考核指标")
@RequestMapping("/kPITownQuota")
public interface KPITownQuotaController {

    /**
     * 查询
     * @param id 镇考核指标id
     * @return 镇考核指标 VO
     */
    @ApiOperation(value = "查询", notes = "查询", position = 1)
    @GetMapping("/{id}id")
    Result<KPITownQuotaVO> findOne(@ApiParam(value = "镇考核指标id", required = true) @PathVariable String id);

    /**
     * 新增
     * @param kPITownQuotaDTO 镇考核指标 DTO
     * @return 镇考核指标 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/")
    Result<KPITownQuotaVO> add(@ApiParam(value = "镇考核指标 DTO", required = true) @RequestBody @Validated KPITownQuotaDTO kPITownQuotaDTO);

    /**
     * 更新
     * @param id 镇考核指标id
     * @param kPITownQuotaDTO 镇考核指标 DTO
     * @return 镇考核指标 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @PutMapping("/{id}id")
    Result<KPITownQuotaVO> update(@ApiParam(value = "镇考核指标id", required = true) @PathVariable String id,
        @ApiParam(value = "镇考核指标 DTO", required = true) @RequestBody @Validated KPITownQuotaDTO kPITownQuotaDTO);

    /**
     * 删除
     * @param id 镇考核指标id
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "删除", position = 4)
    @DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "镇考核指标id", required = true) @PathVariable String id);

    /**
     * 列表查询
     * @param kPITownQuotaSearchable 镇考核指标查询条件
     * @param sort 排序条件
     * @return 镇考核指标 VO 集合
     */
    @ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/list")
    Result<List<KPITownQuotaVO>> list(@ApiParam(value = "镇考核指标查询条件", required = true) @RequestBody KPITownQuotaSearchable kPITownQuotaSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort);

    /**
     * 分页查询
     * @param kPITownQuotaSearchable 镇考核指标查询条件
     * @param pageable 分页条件
     * @return 镇考核指标 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
    @PostMapping("/page")
    Result<Page<KPITownQuotaVO>> page(@ApiParam(value = "镇考核指标查询条件", required = true) @RequestBody KPITownQuotaSearchable kPITownQuotaSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable);

    /**
     * 获取所有镇考核指标
     * @param districtId 组织ID
     * @return 镇考核指标
     */
    @ApiOperation(value = "获取所有镇考核指标", notes = "获取所有镇考核指标", position = 6)
    @GetMapping("/getAll/{districtId}/{parentQuotaId}")
    Result<Object> getAll(@PathVariable String districtId,@PathVariable String parentQuotaId);


    /**
     * 增加所有村的考核指标
     * @param
     * @return 镇考核指标
     */
    @ApiOperation(value = "获取所有镇考核指标", notes = "获取所有镇考核指标", position = 6)
    @PostMapping("/addAll")
    Result<Object> addAll(@RequestBody List<KpiQuotaDTO> kpiQuotas);

    /**
     * 初始化03,04,05的数据
     * @param
     * @return 镇考核指标
     */
    @ApiOperation(value = "初始化固定指标", notes = "初始化固定指标", position = 6)
    @GetMapping("/initAll")
    Result<Boolean> initAll();
}
