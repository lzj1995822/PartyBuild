package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.TrainingInfoController;
import com.cloudkeeper.leasing.identity.domain.TrainingInfo;
import com.cloudkeeper.leasing.identity.dto.traininginfo.TrainingInfoDTO;
import com.cloudkeeper.leasing.identity.dto.traininginfo.TrainingInfoSearchable;
import com.cloudkeeper.leasing.identity.service.TrainingInfoService;
import com.cloudkeeper.leasing.identity.vo.TrainingInfoVO;
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
 * 专职村书记培训情况 controller
 * @author yujian
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TrainingInfoControllerImpl implements TrainingInfoController {

    /** 专职村书记培训情况 service */
    private final TrainingInfoService trainingInfoService;

    @Override
    public Result<TrainingInfoVO> findOne(@ApiParam(value = "专职村书记培训情况id", required = true) @PathVariable String id) {
        Optional<TrainingInfo> trainingInfoOptional = trainingInfoService.findOptionalById(id);
        return trainingInfoOptional.map(trainingInfo -> Result.of(trainingInfo.convert(TrainingInfoVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<TrainingInfoVO> add(@ApiParam(value = "专职村书记培训情况 DTO", required = true) @RequestBody @Validated TrainingInfoDTO trainingInfoDTO) {
        TrainingInfo trainingInfo = trainingInfoService.save(trainingInfoDTO.convert(TrainingInfo.class));
        return Result.ofAddSuccess(trainingInfo.convert(TrainingInfoVO.class));
    }

    @Override
    public Result<TrainingInfoVO> update(@ApiParam(value = "专职村书记培训情况id", required = true) @PathVariable String id,
        @ApiParam(value = "专职村书记培训情况 DTO", required = true) @RequestBody @Validated TrainingInfoDTO trainingInfoDTO) {
        Optional<TrainingInfo> trainingInfoOptional = trainingInfoService.findOptionalById(id);
        if (!trainingInfoOptional.isPresent()) {
            return Result.ofLost();
        }
        TrainingInfo trainingInfo = trainingInfoOptional.get();
        BeanUtils.copyProperties(trainingInfoDTO, trainingInfo);
        trainingInfo = trainingInfoService.save(trainingInfo);
        return Result.ofUpdateSuccess(trainingInfo.convert(TrainingInfoVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "专职村书记培训情况id", required = true) @PathVariable String id) {
        trainingInfoService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<TrainingInfoVO>> list(@ApiParam(value = "专职村书记培训情况查询条件", required = true) @RequestBody TrainingInfoSearchable trainingInfoSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<TrainingInfo> trainingInfoList = trainingInfoService.findAll(trainingInfoSearchable, sort);
        List<TrainingInfoVO> trainingInfoVOList = TrainingInfo.convert(trainingInfoList, TrainingInfoVO.class);
        return Result.of(trainingInfoVOList);
    }

    @Override
    public Result<Page<TrainingInfoVO>> page(@ApiParam(value = "专职村书记培训情况查询条件", required = true) @RequestBody TrainingInfoSearchable trainingInfoSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<TrainingInfo> trainingInfoPage = trainingInfoService.findAll(trainingInfoSearchable, pageable);
        Page<TrainingInfoVO> trainingInfoVOPage = TrainingInfo.convert(trainingInfoPage, TrainingInfoVO.class);
        return Result.of(trainingInfoVOPage);
    }

}