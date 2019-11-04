package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.FeedbackTemplateController;
import com.cloudkeeper.leasing.identity.domain.FeedbackTemplate;
import com.cloudkeeper.leasing.identity.dto.feedbacktemplate.FeedbackTemplateDTO;
import com.cloudkeeper.leasing.identity.dto.feedbacktemplate.FeedbackTemplateSearchable;
import com.cloudkeeper.leasing.identity.service.FeedbackTemplateService;
import com.cloudkeeper.leasing.identity.vo.FeedbackTemplateVO;
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
 * 反馈配置模板 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FeedbackTemplateControllerImpl implements FeedbackTemplateController {

    /** 反馈配置模板 service */
    private final FeedbackTemplateService feedbackTemplateService;

    @Override
    public Result<FeedbackTemplateVO> findOne(@ApiParam(value = "反馈配置模板id", required = true) @PathVariable String id) {
        Optional<FeedbackTemplate> feedbackTemplateOptional = feedbackTemplateService.findOptionalById(id);
        return feedbackTemplateOptional.map(feedbackTemplate -> Result.of(feedbackTemplate.convert(FeedbackTemplateVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<FeedbackTemplateVO> add(@ApiParam(value = "反馈配置模板 DTO", required = true) @RequestBody @Validated FeedbackTemplateDTO feedbackTemplateDTO) {
        FeedbackTemplate feedbackTemplate = feedbackTemplateService.save(feedbackTemplateDTO.convert(FeedbackTemplate.class));
        return Result.ofAddSuccess(feedbackTemplate.convert(FeedbackTemplateVO.class));
    }

    @Override
    public Result<FeedbackTemplateVO> update(@ApiParam(value = "反馈配置模板id", required = true) @PathVariable String id,
        @ApiParam(value = "反馈配置模板 DTO", required = true) @RequestBody @Validated FeedbackTemplateDTO feedbackTemplateDTO) {
        Optional<FeedbackTemplate> feedbackTemplateOptional = feedbackTemplateService.findOptionalById(id);
        if (!feedbackTemplateOptional.isPresent()) {
            return Result.ofLost();
        }
        FeedbackTemplate feedbackTemplate = feedbackTemplateOptional.get();
        BeanUtils.copyProperties(feedbackTemplateDTO, feedbackTemplate);
        feedbackTemplate = feedbackTemplateService.save(feedbackTemplate);
        return Result.ofUpdateSuccess(feedbackTemplate.convert(FeedbackTemplateVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "反馈配置模板id", required = true) @PathVariable String id) {
        feedbackTemplateService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<FeedbackTemplateVO>> list(@ApiParam(value = "反馈配置模板查询条件", required = true) @RequestBody FeedbackTemplateSearchable feedbackTemplateSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<FeedbackTemplate> feedbackTemplateList = feedbackTemplateService.findAll(feedbackTemplateSearchable, sort);
        List<FeedbackTemplateVO> feedbackTemplateVOList = FeedbackTemplate.convert(feedbackTemplateList, FeedbackTemplateVO.class);
        return Result.of(feedbackTemplateVOList);
    }

    @Override
    public Result<Page<FeedbackTemplateVO>> page(@ApiParam(value = "反馈配置模板查询条件", required = true) @RequestBody FeedbackTemplateSearchable feedbackTemplateSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<FeedbackTemplate> feedbackTemplatePage = feedbackTemplateService.findAll(feedbackTemplateSearchable, pageable);
        Page<FeedbackTemplateVO> feedbackTemplateVOPage = FeedbackTemplate.convert(feedbackTemplatePage, FeedbackTemplateVO.class);
        return Result.of(feedbackTemplateVOPage);
    }

}