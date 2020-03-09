package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.HonourInfoController;
import com.cloudkeeper.leasing.identity.domain.HonourInfo;
import com.cloudkeeper.leasing.identity.dto.honourinfo.HonourInfoDTO;
import com.cloudkeeper.leasing.identity.dto.honourinfo.HonourInfoSearchable;
import com.cloudkeeper.leasing.identity.service.HonourInfoService;
import com.cloudkeeper.leasing.identity.vo.HonourInfoVO;
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
 * 表彰情况 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HonourInfoControllerImpl implements HonourInfoController {

    /** 表彰情况 service */
    private final HonourInfoService honourInfoService;

    @Override
    public Result<HonourInfoVO> findOne(@ApiParam(value = "表彰情况id", required = true) @PathVariable String id) {
        Optional<HonourInfo> honourInfoOptional = honourInfoService.findOptionalById(id);
        return honourInfoOptional.map(honourInfo -> Result.of(honourInfo.convert(HonourInfoVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<HonourInfoVO> add(@ApiParam(value = "表彰情况 DTO", required = true) @RequestBody @Validated HonourInfoDTO honourInfoDTO) {
        HonourInfo honourInfo = honourInfoService.save(honourInfoDTO.convert(HonourInfo.class));
        return Result.ofAddSuccess(honourInfo.convert(HonourInfoVO.class));
    }

    @Override
    public Result<HonourInfoVO> update(@ApiParam(value = "表彰情况id", required = true) @PathVariable String id,
        @ApiParam(value = "表彰情况 DTO", required = true) @RequestBody @Validated HonourInfoDTO honourInfoDTO) {
        Optional<HonourInfo> honourInfoOptional = honourInfoService.findOptionalById(id);
        if (!honourInfoOptional.isPresent()) {
            return Result.ofLost();
        }
        HonourInfo honourInfo = honourInfoOptional.get();
        BeanUtils.copyProperties(honourInfoDTO, honourInfo);
        honourInfo = honourInfoService.save(honourInfo);
        return Result.ofUpdateSuccess(honourInfo.convert(HonourInfoVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "表彰情况id", required = true) @PathVariable String id) {
        honourInfoService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<HonourInfoVO>> list(@ApiParam(value = "表彰情况查询条件", required = true) @RequestBody HonourInfoSearchable honourInfoSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<HonourInfo> honourInfoList = honourInfoService.findAll(honourInfoSearchable, sort);
        List<HonourInfoVO> honourInfoVOList = HonourInfo.convert(honourInfoList, HonourInfoVO.class);
        return Result.of(honourInfoVOList);
    }

    @Override
    public Result<Page<HonourInfoVO>> page(@ApiParam(value = "表彰情况查询条件", required = true) @RequestBody HonourInfoSearchable honourInfoSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<HonourInfo> honourInfoPage = honourInfoService.findAll(honourInfoSearchable, pageable);
        Page<HonourInfoVO> honourInfoVOPage = HonourInfo.convert(honourInfoPage, HonourInfoVO.class);
        return Result.of(honourInfoVOPage);
    }

}