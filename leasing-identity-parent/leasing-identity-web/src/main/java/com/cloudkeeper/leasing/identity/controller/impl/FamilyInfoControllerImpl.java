package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.FamilyInfoController;
import com.cloudkeeper.leasing.identity.domain.FamilyInfo;
import com.cloudkeeper.leasing.identity.dto.familyinfo.FamilyInfoDTO;
import com.cloudkeeper.leasing.identity.dto.familyinfo.FamilyInfoSearchable;
import com.cloudkeeper.leasing.identity.service.FamilyInfoService;
import com.cloudkeeper.leasing.identity.vo.FamilyInfoVO;
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
 * 专职村书记家庭情况 controller
 * @author yujian
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FamilyInfoControllerImpl implements FamilyInfoController {

    /** 专职村书记家庭情况 service */
    private final FamilyInfoService familyInfoService;

    @Override
    public Result<FamilyInfoVO> findOne(@ApiParam(value = "专职村书记家庭情况id", required = true) @PathVariable String id) {
        Optional<FamilyInfo> familyInfoOptional = familyInfoService.findOptionalById(id);
        return familyInfoOptional.map(familyInfo -> Result.of(familyInfo.convert(FamilyInfoVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<FamilyInfoVO> add(@ApiParam(value = "专职村书记家庭情况 DTO", required = true) @RequestBody @Validated FamilyInfoDTO familyInfoDTO) {
        FamilyInfo familyInfo = familyInfoService.save(familyInfoDTO.convert(FamilyInfo.class));
        return Result.ofAddSuccess(familyInfo.convert(FamilyInfoVO.class));
    }

    @Override
    public Result<FamilyInfoVO> update(@ApiParam(value = "专职村书记家庭情况id", required = true) @PathVariable String id,
        @ApiParam(value = "专职村书记家庭情况 DTO", required = true) @RequestBody @Validated FamilyInfoDTO familyInfoDTO) {
        Optional<FamilyInfo> familyInfoOptional = familyInfoService.findOptionalById(id);
        if (!familyInfoOptional.isPresent()) {
            return Result.ofLost();
        }
        FamilyInfo familyInfo = familyInfoOptional.get();
        BeanUtils.copyProperties(familyInfoDTO, familyInfo);
        familyInfo = familyInfoService.save(familyInfo);
        return Result.ofUpdateSuccess(familyInfo.convert(FamilyInfoVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "专职村书记家庭情况id", required = true) @PathVariable String id) {
        familyInfoService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<FamilyInfoVO>> list(@ApiParam(value = "专职村书记家庭情况查询条件", required = true) @RequestBody FamilyInfoSearchable familyInfoSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<FamilyInfo> familyInfoList = familyInfoService.findAll(familyInfoSearchable, sort);
        List<FamilyInfoVO> familyInfoVOList = FamilyInfo.convert(familyInfoList, FamilyInfoVO.class);
        return Result.of(familyInfoVOList);
    }

    @Override
    public Result<Page<FamilyInfoVO>> page(@ApiParam(value = "专职村书记家庭情况查询条件", required = true) @RequestBody FamilyInfoSearchable familyInfoSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<FamilyInfo> familyInfoPage = familyInfoService.findAll(familyInfoSearchable, pageable);
        Page<FamilyInfoVO> familyInfoVOPage = FamilyInfo.convert(familyInfoPage, FamilyInfoVO.class);
        return Result.of(familyInfoVOPage);
    }

}