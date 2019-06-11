package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.ParActivityFeedbackController;
import com.cloudkeeper.leasing.identity.domain.ParActivityFeedback;
import com.cloudkeeper.leasing.identity.dto.paractivityfeedback.ParActivityFeedbackDTO;
import com.cloudkeeper.leasing.identity.dto.paractivityfeedback.ParActivityFeedbackSearchable;
import com.cloudkeeper.leasing.identity.service.ParActivityFeedbackService;
import com.cloudkeeper.leasing.identity.vo.ParActivityFeedbackVO;
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
 * 移动端执行记录 controller
 * @author lxw
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityFeedbackControllerImpl implements ParActivityFeedbackController {

    /** 移动端执行记录 service */
    private final ParActivityFeedbackService parActivityFeedbackService;

    @Override
    public Result<ParActivityFeedbackVO> findOne(@ApiParam(value = "移动端执行记录id", required = true) @PathVariable String id) {
        Optional<ParActivityFeedback> parActivityFeedbackOptional = parActivityFeedbackService.findOptionalById(id);
        return parActivityFeedbackOptional.map(parActivityFeedback -> Result.of(parActivityFeedback.convert(ParActivityFeedbackVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<ParActivityFeedbackVO> add(@ApiParam(value = "移动端执行记录 DTO", required = true) @RequestBody @Validated ParActivityFeedbackDTO parActivityFeedbackDTO) {
        ParActivityFeedback parActivityFeedback = parActivityFeedbackService.save(parActivityFeedbackDTO.convert(ParActivityFeedback.class));
        return Result.ofAddSuccess(parActivityFeedback.convert(ParActivityFeedbackVO.class));
    }

    @Override
    public Result<ParActivityFeedbackVO> update(@ApiParam(value = "移动端执行记录id", required = true) @PathVariable String id,
        @ApiParam(value = "移动端执行记录 DTO", required = true) @RequestBody @Validated ParActivityFeedbackDTO parActivityFeedbackDTO) {
        Optional<ParActivityFeedback> parActivityFeedbackOptional = parActivityFeedbackService.findOptionalById(id);
        if (!parActivityFeedbackOptional.isPresent()) {
            return Result.ofLost();
        }
        ParActivityFeedback parActivityFeedback = parActivityFeedbackOptional.get();
        BeanUtils.copyProperties(parActivityFeedbackDTO, parActivityFeedback);
        parActivityFeedback = parActivityFeedbackService.save(parActivityFeedback);
        return Result.ofUpdateSuccess(parActivityFeedback.convert(ParActivityFeedbackVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "移动端执行记录id", required = true) @PathVariable String id) {
        parActivityFeedbackService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<ParActivityFeedbackVO>> list(@ApiParam(value = "移动端执行记录查询条件", required = true) @RequestBody ParActivityFeedbackSearchable parActivityFeedbackSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<ParActivityFeedback> parActivityFeedbackList = parActivityFeedbackService.findAll(parActivityFeedbackSearchable, sort);
        List<ParActivityFeedbackVO> parActivityFeedbackVOList = ParActivityFeedback.convert(parActivityFeedbackList, ParActivityFeedbackVO.class);
        return Result.of(parActivityFeedbackVOList);
    }

    @Override
    public Result<Page<ParActivityFeedbackVO>> page(@ApiParam(value = "移动端执行记录查询条件", required = true) @RequestBody ParActivityFeedbackSearchable parActivityFeedbackSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<ParActivityFeedback> parActivityFeedbackPage = parActivityFeedbackService.findAll(parActivityFeedbackSearchable, pageable);
        Page<ParActivityFeedbackVO> parActivityFeedbackVOPage = ParActivityFeedback.convert(parActivityFeedbackPage, ParActivityFeedbackVO.class);
        return Result.of(parActivityFeedbackVOPage);
    }

    @Override
    public Result<Page<ParActivityFeedbackVO>> phonePage(@ApiParam(value = "移动端执行记录查询条件", required = true) @RequestBody ParActivityFeedbackSearchable parActivityFeedbackSearchable,
                                                  @ApiParam(value = "分页参数", required = true) Pageable pageable){
        Page<ParActivityFeedbackVO> parActivityFeedbackVOPage = parActivityFeedbackService.phonePage(parActivityFeedbackSearchable, pageable);
        return Result.of(parActivityFeedbackVOPage);

    }

}
