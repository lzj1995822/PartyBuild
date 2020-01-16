package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.identity.domain.SysConfiguration;
import com.cloudkeeper.leasing.identity.dto.sysconfiguration.SysConfigurationDTO;
import com.cloudkeeper.leasing.identity.dto.sysconfiguration.SysConfigurationSearchable;
import com.cloudkeeper.leasing.identity.vo.SysConfigurationVO;
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
 * 系统属性配置 controller
 * @author cqh
 */
@Api(value = "系统属性配置", tags = "系统属性配置")
@RequestMapping("/sysConfiguration")
public interface SysConfigurationController {

    /**
     * 查询
     * @param id 系统属性配置id
     * @return 系统属性配置 VO
     */
    @ApiOperation(value = "查询", notes = "查询", position = 1)
    @GetMapping("/{id}id")
    Result<SysConfigurationVO> findOne(@ApiParam(value = "系统属性配置id", required = true) @PathVariable String id);

    /**
     * 新增
     * @param sysConfigurationDTO 系统属性配置 DTO
     * @return 系统属性配置 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/")
    Result<SysConfigurationVO> add(@ApiParam(value = "系统属性配置 DTO", required = true) @RequestBody @Validated SysConfigurationDTO sysConfigurationDTO);

    /**
     * 更新
     * @param id 系统属性配置id
     * @param sysConfigurationDTO 系统属性配置 DTO
     * @return 系统属性配置 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @PutMapping("/{id}id")
    Result<SysConfigurationVO> update(@ApiParam(value = "系统属性配置id", required = true) @PathVariable String id,
        @ApiParam(value = "系统属性配置 DTO", required = true) @RequestBody @Validated SysConfigurationDTO sysConfigurationDTO);

    /**
     * 删除
     * @param id 系统属性配置id
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "删除", position = 4)
    @DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "系统属性配置id", required = true) @PathVariable String id);

    /**
     * 列表查询
     * @param sysConfigurationSearchable 系统属性配置查询条件
     * @param sort 排序条件
     * @return 系统属性配置 VO 集合
     */
    @ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/list")
    Result<List<SysConfigurationVO>> list(@ApiParam(value = "系统属性配置查询条件", required = true) @RequestBody SysConfigurationSearchable sysConfigurationSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort);

    /**
     * 分页查询
     * @param sysConfigurationSearchable 系统属性配置查询条件
     * @param pageable 分页条件
     * @return 系统属性配置 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
    @PostMapping("/page")
    Result<Page<SysConfigurationVO>> page(@ApiParam(value = "系统属性配置查询条件", required = true) @RequestBody SysConfigurationSearchable sysConfigurationSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable);

    /**
     * 处理测试用户
     */
    @ApiOperation(value = "编辑逻辑删除", notes = "编辑逻辑删除", position = 3)
    @PutMapping("/{id}isDelete")
    Result<Boolean> updateUse(@ApiParam(value = "系统属性配置id", required = true) @PathVariable String id,
                                       @ApiParam(value = "系统属性配置 DTO", required = true) @RequestBody @Validated SysConfigurationDTO sysConfigurationDTO);


}