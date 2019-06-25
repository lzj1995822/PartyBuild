package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.SysConfigurationController;
import com.cloudkeeper.leasing.identity.domain.SysConfiguration;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.identity.domain.SysUser;
import com.cloudkeeper.leasing.identity.dto.sysconfiguration.SysConfigurationDTO;
import com.cloudkeeper.leasing.identity.dto.sysconfiguration.SysConfigurationSearchable;
import com.cloudkeeper.leasing.identity.service.SysConfigurationService;
import com.cloudkeeper.leasing.identity.service.SysDistrictService;
import com.cloudkeeper.leasing.identity.service.SysUserService;
import com.cloudkeeper.leasing.identity.vo.SysConfigurationVO;
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
 * 系统属性配置 controller
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysConfigurationControllerImpl implements SysConfigurationController {

    /** 系统属性配置 service */
    private final SysConfigurationService sysConfigurationService;

    private final SysUserService sysUserService;

    private final SysDistrictService sysDistrictService;


    @Override
    public Result<SysConfigurationVO> findOne(@ApiParam(value = "系统属性配置id", required = true) @PathVariable String id) {
        Optional<SysConfiguration> sysConfigurationOptional = sysConfigurationService.findOptionalById(id);
        return sysConfigurationOptional.map(sysConfiguration -> Result.of(sysConfiguration.convert(SysConfigurationVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<SysConfigurationVO> add(@ApiParam(value = "系统属性配置 DTO", required = true) @RequestBody @Validated SysConfigurationDTO sysConfigurationDTO) {
        SysConfiguration sysConfiguration = sysConfigurationService.save(sysConfigurationDTO.convert(SysConfiguration.class));
        return Result.ofAddSuccess(sysConfiguration.convert(SysConfigurationVO.class));
    }

    @Override
    public Result<SysConfigurationVO> update(@ApiParam(value = "系统属性配置id", required = true) @PathVariable String id,
        @ApiParam(value = "系统属性配置 DTO", required = true) @RequestBody @Validated SysConfigurationDTO sysConfigurationDTO) {
        Optional<SysConfiguration> sysConfigurationOptional = sysConfigurationService.findOptionalById(id);
        if (!sysConfigurationOptional.isPresent()) {
            return Result.ofLost();
        }
        SysConfiguration sysConfiguration = sysConfigurationOptional.get();
        BeanUtils.copyProperties(sysConfigurationDTO, sysConfiguration);
        sysConfiguration = sysConfigurationService.save(sysConfiguration);
        return Result.ofUpdateSuccess(sysConfiguration.convert(SysConfigurationVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "系统属性配置id", required = true) @PathVariable String id) {
        sysConfigurationService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<SysConfigurationVO>> list(@ApiParam(value = "系统属性配置查询条件", required = true) @RequestBody SysConfigurationSearchable sysConfigurationSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<SysConfiguration> sysConfigurationList = sysConfigurationService.findAll(sysConfigurationSearchable, sort);
        List<SysConfigurationVO> sysConfigurationVOList = SysConfiguration.convert(sysConfigurationList, SysConfigurationVO.class);
        return Result.of(sysConfigurationVOList);
    }

    @Override
    public Result<Page<SysConfigurationVO>> page(@ApiParam(value = "系统属性配置查询条件", required = true) @RequestBody SysConfigurationSearchable sysConfigurationSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<SysConfiguration> sysConfigurationPage = sysConfigurationService.findAll(sysConfigurationSearchable, pageable);
        Page<SysConfigurationVO> sysConfigurationVOPage = SysConfiguration.convert(sysConfigurationPage, SysConfigurationVO.class);
        return Result.of(sysConfigurationVOPage);
    }

    @Override
    public void updateUse(@ApiParam(value = "系统属性配置id", required = true) @PathVariable String id,
                          @ApiParam(value = "系统属性配置 DTO", required = true) @RequestBody @Validated SysConfigurationDTO sysConfigurationDTO) {
        SysConfiguration convert = sysConfigurationDTO.convert(SysConfiguration.class);
        String codeValue = convert.getCodeValue();
        Integer integer = Integer.valueOf(codeValue);
        sysUserService.save(integer,"6a97c2d9-ae44-402e-b117-2a1b55ded4d8");
        sysUserService.save(integer,"6bc3aad9-78cb-4a79-9154-fdf02dec1fe4");
        sysDistrictService.save(integer,"2c80b704-65e8-46a3-8222-3c0cb8fddfdc");
        sysDistrictService.save(integer,"b526c2e7-ec30-439b-b298-7bed0d853db7");
        sysConfigurationService.save(convert);
    }

}