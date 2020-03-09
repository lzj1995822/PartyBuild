package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.RewardInfoController;
import com.cloudkeeper.leasing.identity.domain.RewardInfo;
import com.cloudkeeper.leasing.identity.dto.rewardinfo.RewardInfoDTO;
import com.cloudkeeper.leasing.identity.dto.rewardinfo.RewardInfoSearchable;
import com.cloudkeeper.leasing.identity.service.RewardInfoService;
import com.cloudkeeper.leasing.identity.vo.RewardInfoVO;
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
 * 报酬情况 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RewardInfoControllerImpl implements RewardInfoController {

    /** 报酬情况 service */
    private final RewardInfoService rewardInfoService;

    @Override
    public Result<RewardInfoVO> findOne(@ApiParam(value = "报酬情况id", required = true) @PathVariable String id) {
        Optional<RewardInfo> rewardInfoOptional = rewardInfoService.findOptionalById(id);
        return rewardInfoOptional.map(rewardInfo -> Result.of(rewardInfo.convert(RewardInfoVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<RewardInfoVO> add(@ApiParam(value = "报酬情况 DTO", required = true) @RequestBody @Validated RewardInfoDTO rewardInfoDTO) {
        RewardInfo rewardInfo = rewardInfoService.save(rewardInfoDTO.convert(RewardInfo.class));
        return Result.ofAddSuccess(rewardInfo.convert(RewardInfoVO.class));
    }

    @Override
    public Result<RewardInfoVO> update(@ApiParam(value = "报酬情况id", required = true) @PathVariable String id,
        @ApiParam(value = "报酬情况 DTO", required = true) @RequestBody @Validated RewardInfoDTO rewardInfoDTO) {
        Optional<RewardInfo> rewardInfoOptional = rewardInfoService.findOptionalById(id);
        if (!rewardInfoOptional.isPresent()) {
            return Result.ofLost();
        }
        RewardInfo rewardInfo = rewardInfoOptional.get();
        BeanUtils.copyProperties(rewardInfoDTO, rewardInfo);
        rewardInfo = rewardInfoService.save(rewardInfo);
        return Result.ofUpdateSuccess(rewardInfo.convert(RewardInfoVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "报酬情况id", required = true) @PathVariable String id) {
        rewardInfoService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<RewardInfoVO>> list(@ApiParam(value = "报酬情况查询条件", required = true) @RequestBody RewardInfoSearchable rewardInfoSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<RewardInfo> rewardInfoList = rewardInfoService.findAll(rewardInfoSearchable, sort);
        List<RewardInfoVO> rewardInfoVOList = RewardInfo.convert(rewardInfoList, RewardInfoVO.class);
        return Result.of(rewardInfoVOList);
    }

    @Override
    public Result<Page<RewardInfoVO>> page(@ApiParam(value = "报酬情况查询条件", required = true) @RequestBody RewardInfoSearchable rewardInfoSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<RewardInfo> rewardInfoPage = rewardInfoService.findAll(rewardInfoSearchable, pageable);
        Page<RewardInfoVO> rewardInfoVOPage = RewardInfo.convert(rewardInfoPage, RewardInfoVO.class);
        return Result.of(rewardInfoVOPage);
    }

}