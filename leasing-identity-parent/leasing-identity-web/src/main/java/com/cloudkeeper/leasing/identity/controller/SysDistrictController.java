package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.identity.dto.sysdistrict.SysDistrictDTO;
import com.cloudkeeper.leasing.identity.dto.sysdistrict.SysDistrictSearchable;
import com.cloudkeeper.leasing.identity.vo.SysDistrictTreeVO;
import com.cloudkeeper.leasing.identity.vo.SysDistrictVO;
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
import java.util.Map;
import java.util.Set;

/**
 * 组织 controller
 * @author lxw
 */
@Api(value = "组织", tags = "组织")
@RequestMapping("/sysDistrict")
public interface SysDistrictController {

    /**
     * 查询
     * @param id 组织id
     * @return 组织 VO
     */
    @ApiOperation(value = "查询", notes = "查询", position = 1)
    @GetMapping("/{id}id")
    Result<SysDistrictVO> findOne(@ApiParam(value = "组织id", required = true) @PathVariable String id);

    /**
     * 新增
     * @param sysDistrictDTO 组织 DTO
     * @return 组织 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/")
    Result<SysDistrictVO> add(@ApiParam(value = "组织 DTO", required = true) @RequestBody @Validated SysDistrictDTO sysDistrictDTO);

    /**
     * 更新
     * @param id 组织id
     * @param sysDistrictDTO 组织 DTO
     * @return 组织 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @PutMapping("/{id}id")
    Result<SysDistrictVO> update(@ApiParam(value = "组织id", required = true) @PathVariable String id,
        @ApiParam(value = "组织 DTO", required = true) @RequestBody @Validated SysDistrictDTO sysDistrictDTO);

    /**
     * 删除
     * @param id 组织id
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "删除", position = 4)
    @DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "组织id", required = true) @PathVariable String id);

    /**
     * 列表查询
     * @param sysDistrictSearchable 组织查询条件
     * @param sort 排序条件
     * @return 组织 VO 集合
     */
    @ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/list")
    Result<List<SysDistrictVO>> list(@ApiParam(value = "组织查询条件", required = true) @RequestBody SysDistrictSearchable sysDistrictSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort);


    /**
     * 分页查询
     * @param sysDistrictSearchable 组织查询条件
     * @param pageable 分页条件
     * @return 组织 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
    @PostMapping("/page")
    Result<Page<SysDistrictVO>> page(@ApiParam(value = "组织查询条件", required = true) @RequestBody SysDistrictSearchable sysDistrictSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable);

    /**
     * 下拉项条件查询
     * @return 组织 VO 集合
     */
    @ApiOperation(value = "下拉项条件查询", notes = "下拉项条件查询<br/>sort：单个排序字段传多值，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/listSome")
    Result<Map<String ,String>> listSome();

    @ApiOperation(value = "组织树形结构", notes = "组织树形结构", position = 8)
    @GetMapping("/{sysDistrictId}tree")
    Result<Set<SysDistrictTreeVO>> tree(@PathVariable String sysDistrictId);

}
