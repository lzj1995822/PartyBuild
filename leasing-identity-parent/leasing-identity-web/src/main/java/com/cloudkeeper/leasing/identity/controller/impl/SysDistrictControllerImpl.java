package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.SysDistrictController;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.identity.dto.sysdistrict.SysDistrictDTO;
import com.cloudkeeper.leasing.identity.dto.sysdistrict.SysDistrictSearchable;
import com.cloudkeeper.leasing.identity.service.SysDistrictService;
import com.cloudkeeper.leasing.identity.vo.SysDistrictVO;
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
 * 组织 controller
 * @author lxw
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysDistrictControllerImpl implements SysDistrictController {

    /** 组织 service */
    private final SysDistrictService sysDistrictService;

    @Override
    public Result<SysDistrictVO> findOne(@ApiParam(value = "组织id", required = true) @PathVariable String id) {
        Optional<SysDistrict> sysDistrictOptional = sysDistrictService.findOptionalById(id);
        return sysDistrictOptional.map(sysDistrict -> Result.of(sysDistrict.convert(SysDistrictVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<SysDistrictVO> add(@ApiParam(value = "组织 DTO", required = true) @RequestBody @Validated SysDistrictDTO sysDistrictDTO) {
        SysDistrict sysDistrict = sysDistrictService.save(sysDistrictDTO.convert(SysDistrict.class));
        return Result.ofAddSuccess(sysDistrict.convert(SysDistrictVO.class));
    }

    @Override
    public Result<SysDistrictVO> update(@ApiParam(value = "组织id", required = true) @PathVariable String id,
        @ApiParam(value = "组织 DTO", required = true) @RequestBody @Validated SysDistrictDTO sysDistrictDTO) {
        Optional<SysDistrict> sysDistrictOptional = sysDistrictService.findOptionalById(id);
        if (!sysDistrictOptional.isPresent()) {
            return Result.ofLost();
        }
        SysDistrict sysDistrict = sysDistrictOptional.get();
        BeanUtils.copyProperties(sysDistrictDTO, sysDistrict);
        sysDistrict = sysDistrictService.save(sysDistrict);
        return Result.ofUpdateSuccess(sysDistrict.convert(SysDistrictVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "组织id", required = true) @PathVariable String id) {
        sysDistrictService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<SysDistrictVO>> list(@ApiParam(value = "组织查询条件", required = true) @RequestBody SysDistrictSearchable sysDistrictSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<SysDistrict> sysDistrictList = sysDistrictService.findAll(sysDistrictSearchable, sort);
        List<SysDistrictVO> sysDistrictVOList = SysDistrict.convert(sysDistrictList, SysDistrictVO.class);
        return Result.of(sysDistrictVOList);
    }

    @Override
    public Result<Page<SysDistrictVO>> page(@ApiParam(value = "组织查询条件", required = true) @RequestBody SysDistrictSearchable sysDistrictSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<SysDistrict> sysDistrictPage = sysDistrictService.findAll(sysDistrictSearchable, pageable);
        Page<SysDistrictVO> sysDistrictVOPage = SysDistrict.convert(sysDistrictPage, SysDistrictVO.class);
        return Result.of(sysDistrictVOPage);
    }

}