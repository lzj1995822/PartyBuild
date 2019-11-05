package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.FeedbackItemValueController;
import com.cloudkeeper.leasing.identity.domain.FeedbackItemValue;
import com.cloudkeeper.leasing.identity.dto.feedbackitemvalue.FeedbackItemValueDTO;
import com.cloudkeeper.leasing.identity.dto.feedbackitemvalue.FeedbackItemValueSearchable;
import com.cloudkeeper.leasing.identity.service.FeedbackItemValueService;
import com.cloudkeeper.leasing.identity.vo.FeedbackItemValueVO;
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
 * 反馈配置项值 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FeedbackItemValueControllerImpl implements FeedbackItemValueController {

    /** 反馈配置项值 service */
    private final FeedbackItemValueService feedbackItemValueService;

    @Override
    public Result<FeedbackItemValueVO> findOne(@ApiParam(value = "反馈配置项值id", required = true) @PathVariable String id) {
        Optional<FeedbackItemValue> feedbackItemValueOptional = feedbackItemValueService.findOptionalById(id);
        return feedbackItemValueOptional.map(feedbackItemValue -> Result.of(feedbackItemValue.convert(FeedbackItemValueVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<FeedbackItemValueVO> add(@ApiParam(value = "反馈配置项值 DTO", required = true) @RequestBody @Validated FeedbackItemValueDTO feedbackItemValueDTO) {
        FeedbackItemValue feedbackItemValue = feedbackItemValueService.save(feedbackItemValueDTO.convert(FeedbackItemValue.class));
        return Result.ofAddSuccess(feedbackItemValue.convert(FeedbackItemValueVO.class));
    }

    @Override
    public Result<FeedbackItemValueVO> update(@ApiParam(value = "反馈配置项值id", required = true) @PathVariable String id,
        @ApiParam(value = "反馈配置项值 DTO", required = true) @RequestBody @Validated FeedbackItemValueDTO feedbackItemValueDTO) {
        Optional<FeedbackItemValue> feedbackItemValueOptional = feedbackItemValueService.findOptionalById(id);
        if (!feedbackItemValueOptional.isPresent()) {
            return Result.ofLost();
        }
        FeedbackItemValue feedbackItemValue = feedbackItemValueOptional.get();
        BeanUtils.copyProperties(feedbackItemValueDTO, feedbackItemValue);
        feedbackItemValue = feedbackItemValueService.save(feedbackItemValue);
        return Result.ofUpdateSuccess(feedbackItemValue.convert(FeedbackItemValueVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "反馈配置项值id", required = true) @PathVariable String id) {
        feedbackItemValueService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<FeedbackItemValueVO>> list(@ApiParam(value = "反馈配置项值查询条件", required = true) @RequestBody FeedbackItemValueSearchable feedbackItemValueSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<FeedbackItemValue> feedbackItemValueList = feedbackItemValueService.findAll(feedbackItemValueSearchable, sort);
        List<FeedbackItemValueVO> feedbackItemValueVOList = FeedbackItemValue.convert(feedbackItemValueList, FeedbackItemValueVO.class);
        return Result.of(feedbackItemValueVOList);
    }

    @Override
    public Result<Page<FeedbackItemValueVO>> page(@ApiParam(value = "反馈配置项值查询条件", required = true) @RequestBody FeedbackItemValueSearchable feedbackItemValueSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<FeedbackItemValue> feedbackItemValuePage = feedbackItemValueService.findAll(feedbackItemValueSearchable, pageable);
        Page<FeedbackItemValueVO> feedbackItemValueVOPage = FeedbackItemValue.convert(feedbackItemValuePage, FeedbackItemValueVO.class);
        return Result.of(feedbackItemValueVOPage);
    }

}