package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.SysUserController;
import com.cloudkeeper.leasing.identity.domain.SysUser;
import com.cloudkeeper.leasing.identity.dto.sysuser.SysUserDTO;
import com.cloudkeeper.leasing.identity.dto.sysuser.SysUserSearchable;
import com.cloudkeeper.leasing.identity.service.SysUserService;
import com.cloudkeeper.leasing.identity.vo.SysUserVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * 系统用户 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserControllerImpl implements SysUserController {

    /** 系统用户 service */
    private final SysUserService sysUserService;

    @Override
    public Result<SysUserVO> findOne(@ApiParam(value = "系统用户id", required = true) @PathVariable String id) {
        Optional<SysUser> sysUserOptional = sysUserService.findOptionalById(id);
        return sysUserOptional.map(sysUser -> Result.of(sysUser.convert(SysUserVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<SysUserVO> add(@ApiParam(value = "系统用户 DTO", required = true) @RequestBody @Validated SysUserDTO sysUserDTO) {
        SysUser sysUser = sysUserService.save(sysUserDTO.convert(SysUser.class));
        return Result.ofAddSuccess(sysUser.convert(SysUserVO.class));
    }

    @Override
    public Result<SysUserVO> update(@ApiParam(value = "系统用户id", required = true) @PathVariable String id,
        @ApiParam(value = "系统用户 DTO", required = true) @RequestBody @Validated SysUserDTO sysUserDTO) {
        Optional<SysUser> sysUserOptional = sysUserService.findOptionalById(id);
        if (!sysUserOptional.isPresent()) {
            return Result.ofLost();
        }
        SysUser sysUser = sysUserOptional.get();
        BeanUtils.copyProperties(sysUserDTO, sysUser);
        sysUser = sysUserService.save(sysUser);
        return Result.ofUpdateSuccess(sysUser.convert(SysUserVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "系统用户id", required = true) @PathVariable String id) {
        sysUserService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<SysUserVO>> list(@ApiParam(value = "系统用户查询条件", required = true) @RequestBody SysUserSearchable sysUserSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<SysUser> sysUserList = sysUserService.findAll(sysUserSearchable, sort);
        List<SysUserVO> sysUserVOList = SysUser.convert(sysUserList, SysUserVO.class);
        return Result.of(sysUserVOList);
    }

    @Override
    public Result<Page<SysUserVO>> page(@ApiParam(value = "系统用户查询条件", required = true) @RequestBody SysUserSearchable sysUserSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<SysUser> sysUserPage = sysUserService.findAll(sysUserSearchable, pageable);
        Page<SysUserVO> sysUserVOPage = SysUser.convert(sysUserPage, SysUserVO.class);
        return Result.of(sysUserVOPage);
    }

}