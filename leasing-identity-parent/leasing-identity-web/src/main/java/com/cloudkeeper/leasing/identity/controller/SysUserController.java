package com.cloudkeeper.leasing.identity.controller;

import com.cloudkeeper.leasing.identity.dto.sysuser.SysUserDTO;
import com.cloudkeeper.leasing.identity.dto.sysuser.SysUserSearchable;
import com.cloudkeeper.leasing.identity.vo.SysUserVO;
import com.cloudkeeper.leasing.base.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 系统用户 controller
 * @author asher
 */
@Api(value = "系统用户", tags = "系统用户")
@RequestMapping("/sysUser")
public interface SysUserController {

    /**
     * 查询
     * @param id 系统用户id
     * @return 系统用户 VO
     */
    @ApiOperation(value = "查询", notes = "查询", position = 1)
    @GetMapping("/{id}id")
    Result<SysUserVO> findOne(@ApiParam(value = "系统用户id", required = true) @PathVariable String id);

    /**
     * 新增
     * @param sysUserDTO 系统用户 DTO
     * @return 系统用户 VO
     */
    @ApiOperation(value = "新增", notes = "新增", position = 2)
    @PostMapping("/")
    Result<SysUserVO> add(@ApiParam(value = "系统用户 DTO", required = true) @RequestBody @Validated SysUserDTO sysUserDTO);

    /**
     * 更新
     * @param id 系统用户id
     * @param sysUserDTO 系统用户 DTO
     * @return 系统用户 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @PutMapping("/{id}id")
    Result<SysUserVO> update(@ApiParam(value = "系统用户id", required = true) @PathVariable String id,
        @ApiParam(value = "系统用户 DTO", required = true) @RequestBody @Validated SysUserDTO sysUserDTO);

    /**
     * 删除
     * @param id 系统用户id
     * @return 删除结果
     */
    @ApiOperation(value = "删除", notes = "删除", position = 4)
    @DeleteMapping("/{id}id")
    Result delete(@ApiParam(value = "系统用户id", required = true) @PathVariable String id);

    /**
     * 列表查询
     * @param sysUserSearchable 系统用户查询条件
     * @param sort 排序条件
     * @return 系统用户 VO 集合
     */
    @ApiOperation(value = "列表查询", notes = "列表查询<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 5)
    @PostMapping("/list")
    Result<List<SysUserVO>> list(@ApiParam(value = "系统用户查询条件", required = true) @RequestBody SysUserSearchable sysUserSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort);

    /**
     * 分页查询
     * @param sysUserSearchable 系统用户查询条件
     * @param pageable 分页条件
     * @return 系统用户 VO 分页
     */
    @ApiOperation(value = "分页查询", notes = "分页查询<br/>page：第几页，默认为0，是第一页<br/>size：分页大小, 默认为10<br/>sort：排序字段，默认是asc排序方式，可以不写，格式：sort=code,asc&sort=name&sort=note,desc", position = 6)
    @PostMapping("/page")
    Result<Page<SysUserVO>> page(@ApiParam(value = "系统用户查询条件", required = true) @RequestBody SysUserSearchable sysUserSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable);

    /**
     * 登录接口
     * @param sysUserDTO 系统用户DTO
     * @return
     */
    @ApiOperation(value = "登录", notes = "登录", position = 4)
    @PostMapping("/login")
    Result<Map<String, Object>> login(@RequestBody SysUserDTO sysUserDTO);

    /**
     * 权限验证接口
     * @param  auth_token 请求对象
     * @return
     */
    @ApiOperation(value = "权限验证", notes = "权限验证", position = 6)
    @GetMapping("/auth")
    String auth(String auth_token);

    @ApiOperation(value = "初始化机关账号", notes = "初始化机关账号", position = 6)
    @GetMapping("/initOfficeAccounts")
    void initOfficeAccounts();

}