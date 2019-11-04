package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.FeedbackTemplateItemController;
import com.cloudkeeper.leasing.identity.domain.FeedbackTemplateItem;
import com.cloudkeeper.leasing.identity.dto.feedbacktemplateitem.FeedbackTemplateItemDTO;
import com.cloudkeeper.leasing.identity.dto.feedbacktemplateitem.FeedbackTemplateItemSearchable;
import com.cloudkeeper.leasing.identity.service.FeedbackTemplateItemService;
import com.cloudkeeper.leasing.identity.vo.FeedbackTemplateItemVO;
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
 * 反馈配置项 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FeedbackTemplateItemControllerImpl implements FeedbackTemplateItemController {

    /** 反馈配置项 service */
    private final FeedbackTemplateItemService feedbackTemplateItemService;

    @Override
    public Result<FeedbackTemplateItemVO> findOne(@ApiParam(value = "反馈配置项id", required = true) @PathVariable String id) {
        Optional<FeedbackTemplateItem> feedbackTemplateItemOptional = feedbackTemplateItemService.findOptionalById(id);
        return feedbackTemplateItemOptional.map(feedbackTemplateItem -> Result.of(feedbackTemplateItem.convert(FeedbackTemplateItemVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<FeedbackTemplateItemVO> add(@ApiParam(value = "反馈配置项 DTO", required = true) @RequestBody @Validated FeedbackTemplateItemDTO feedbackTemplateItemDTO) {
        FeedbackTemplateItem feedbackTemplateItem = feedbackTemplateItemService.save(feedbackTemplateItemDTO.convert(FeedbackTemplateItem.class));
        return Result.ofAddSuccess(feedbackTemplateItem.convert(FeedbackTemplateItemVO.class));
    }

    @Override
    public Result<FeedbackTemplateItemVO> update(@ApiParam(value = "反馈配置项id", required = true) @PathVariable String id,
        @ApiParam(value = "反馈配置项 DTO", required = true) @RequestBody @Validated FeedbackTemplateItemDTO feedbackTemplateItemDTO) {
        Optional<FeedbackTemplateItem> feedbackTemplateItemOptional = feedbackTemplateItemService.findOptionalById(id);
        if (!feedbackTemplateItemOptional.isPresent()) {
            return Result.ofLost();
        }
        FeedbackTemplateItem feedbackTemplateItem = feedbackTemplateItemOptional.get();
        BeanUtils.copyProperties(feedbackTemplateItemDTO, feedbackTemplateItem);
        feedbackTemplateItem = feedbackTemplateItemService.save(feedbackTemplateItem);
        return Result.ofUpdateSuccess(feedbackTemplateItem.convert(FeedbackTemplateItemVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "反馈配置项id", required = true) @PathVariable String id) {
        feedbackTemplateItemService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<FeedbackTemplateItemVO>> list(@ApiParam(value = "反馈配置项查询条件", required = true) @RequestBody FeedbackTemplateItemSearchable feedbackTemplateItemSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<FeedbackTemplateItem> feedbackTemplateItemList = feedbackTemplateItemService.findAll(feedbackTemplateItemSearchable, sort);
        List<FeedbackTemplateItemVO> feedbackTemplateItemVOList = FeedbackTemplateItem.convert(feedbackTemplateItemList, FeedbackTemplateItemVO.class);
        return Result.of(feedbackTemplateItemVOList);
    }

    @Override
    public Result<Page<FeedbackTemplateItemVO>> page(@ApiParam(value = "反馈配置项查询条件", required = true) @RequestBody FeedbackTemplateItemSearchable feedbackTemplateItemSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<FeedbackTemplateItem> feedbackTemplateItemPage = feedbackTemplateItemService.findAll(feedbackTemplateItemSearchable, pageable);
        Page<FeedbackTemplateItemVO> feedbackTemplateItemVOPage = FeedbackTemplateItem.convert(feedbackTemplateItemPage, FeedbackTemplateItemVO.class);
        return Result.of(feedbackTemplateItemVOPage);
    }

}