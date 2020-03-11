package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.FamilyWorkInfoController;
import com.cloudkeeper.leasing.identity.domain.FamilyWorkInfo;
import com.cloudkeeper.leasing.identity.dto.familyworkinfo.FamilyWorkInfoDTO;
import com.cloudkeeper.leasing.identity.dto.familyworkinfo.FamilyWorkInfoSearchable;
import com.cloudkeeper.leasing.identity.service.FamilyWorkInfoService;
import com.cloudkeeper.leasing.identity.vo.FamilyWorkInfoVO;
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
 * 专职村书记家庭工作情况 controller
 * @author yujian
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FamilyWorkInfoControllerImpl implements FamilyWorkInfoController {

    /** 专职村书记家庭工作情况 service */
    private final FamilyWorkInfoService familyWorkInfoService;

    @Override
    public Result<FamilyWorkInfoVO> findOne(@ApiParam(value = "专职村书记家庭工作情况id", required = true) @PathVariable String id) {
        Optional<FamilyWorkInfo> familyWorkInfoOptional = familyWorkInfoService.findOptionalById(id);
        return familyWorkInfoOptional.map(familyWorkInfo -> Result.of(familyWorkInfo.convert(FamilyWorkInfoVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<FamilyWorkInfoVO> add(@ApiParam(value = "专职村书记家庭工作情况 DTO", required = true) @RequestBody @Validated FamilyWorkInfoDTO familyWorkInfoDTO) {
        FamilyWorkInfo familyWorkInfo = familyWorkInfoService.save(familyWorkInfoDTO.convert(FamilyWorkInfo.class));
        return Result.ofAddSuccess(familyWorkInfo.convert(FamilyWorkInfoVO.class));
    }

    @Override
    public Result<FamilyWorkInfoVO> update(@ApiParam(value = "专职村书记家庭工作情况id", required = true) @PathVariable String id,
        @ApiParam(value = "专职村书记家庭工作情况 DTO", required = true) @RequestBody @Validated FamilyWorkInfoDTO familyWorkInfoDTO) {
        Optional<FamilyWorkInfo> familyWorkInfoOptional = familyWorkInfoService.findOptionalById(id);
        if (!familyWorkInfoOptional.isPresent()) {
            return Result.ofLost();
        }
        FamilyWorkInfo familyWorkInfo = familyWorkInfoOptional.get();
        BeanUtils.copyProperties(familyWorkInfoDTO, familyWorkInfo);
        familyWorkInfo = familyWorkInfoService.save(familyWorkInfo);
        return Result.ofUpdateSuccess(familyWorkInfo.convert(FamilyWorkInfoVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "专职村书记家庭工作情况id", required = true) @PathVariable String id) {
        familyWorkInfoService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<FamilyWorkInfoVO>> list(@ApiParam(value = "专职村书记家庭工作情况查询条件", required = true) @RequestBody FamilyWorkInfoSearchable familyWorkInfoSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<FamilyWorkInfo> familyWorkInfoList = familyWorkInfoService.findAll(familyWorkInfoSearchable, sort);
        List<FamilyWorkInfoVO> familyWorkInfoVOList = FamilyWorkInfo.convert(familyWorkInfoList, FamilyWorkInfoVO.class);
        return Result.of(familyWorkInfoVOList);
    }

    @Override
    public Result<Page<FamilyWorkInfoVO>> page(@ApiParam(value = "专职村书记家庭工作情况查询条件", required = true) @RequestBody FamilyWorkInfoSearchable familyWorkInfoSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<FamilyWorkInfo> familyWorkInfoPage = familyWorkInfoService.findAll(familyWorkInfoSearchable, pageable);
        Page<FamilyWorkInfoVO> familyWorkInfoVOPage = FamilyWorkInfo.convert(familyWorkInfoPage, FamilyWorkInfoVO.class);
        return Result.of(familyWorkInfoVOPage);
    }

}