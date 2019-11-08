package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.ActivityOfficeProgressController;
import com.cloudkeeper.leasing.identity.domain.ActivityOfficeProgress;
import com.cloudkeeper.leasing.identity.dto.activityofficeprogress.ActivityOfficeProgressDTO;
import com.cloudkeeper.leasing.identity.dto.activityofficeprogress.ActivityOfficeProgressSearchable;
import com.cloudkeeper.leasing.identity.service.ActivityOfficeProgressService;
import com.cloudkeeper.leasing.identity.vo.ActivityOfficeProgressVO;
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
 * 机关活动进度记录 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ActivityOfficeProgressControllerImpl implements ActivityOfficeProgressController {

    /** 机关活动进度记录 service */
    private final ActivityOfficeProgressService activityOfficeProgressService;

    @Override
    public Result<ActivityOfficeProgressVO> findOne(@ApiParam(value = "机关活动进度记录id", required = true) @PathVariable String id) {
        Optional<ActivityOfficeProgress> activityOfficeProgressOptional = activityOfficeProgressService.findOptionalById(id);
        return activityOfficeProgressOptional.map(activityOfficeProgress -> Result.of(activityOfficeProgress.convert(ActivityOfficeProgressVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<ActivityOfficeProgressVO> add(@ApiParam(value = "机关活动进度记录 DTO", required = true) @RequestBody @Validated ActivityOfficeProgressDTO activityOfficeProgressDTO) {
        ActivityOfficeProgress activityOfficeProgress = activityOfficeProgressService.save(activityOfficeProgressDTO.convert(ActivityOfficeProgress.class));
        return Result.ofAddSuccess(activityOfficeProgress.convert(ActivityOfficeProgressVO.class));
    }

    @Override
    public Result<ActivityOfficeProgressVO> update(@ApiParam(value = "机关活动进度记录id", required = true) @PathVariable String id,
        @ApiParam(value = "机关活动进度记录 DTO", required = true) @RequestBody @Validated ActivityOfficeProgressDTO activityOfficeProgressDTO) {
        Optional<ActivityOfficeProgress> activityOfficeProgressOptional = activityOfficeProgressService.findOptionalById(id);
        if (!activityOfficeProgressOptional.isPresent()) {
            return Result.ofLost();
        }
        ActivityOfficeProgress activityOfficeProgress = activityOfficeProgressOptional.get();
        BeanUtils.copyProperties(activityOfficeProgressDTO, activityOfficeProgress);
        activityOfficeProgress = activityOfficeProgressService.save(activityOfficeProgress);
        return Result.ofUpdateSuccess(activityOfficeProgress.convert(ActivityOfficeProgressVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "机关活动进度记录id", required = true) @PathVariable String id) {
        activityOfficeProgressService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<ActivityOfficeProgressVO>> list(@ApiParam(value = "机关活动进度记录查询条件", required = true) @RequestBody ActivityOfficeProgressSearchable activityOfficeProgressSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<ActivityOfficeProgress> activityOfficeProgressList = activityOfficeProgressService.findAll(activityOfficeProgressSearchable, sort);
        List<ActivityOfficeProgressVO> activityOfficeProgressVOList = ActivityOfficeProgress.convert(activityOfficeProgressList, ActivityOfficeProgressVO.class);
        return Result.of(activityOfficeProgressVOList);
    }

    @Override
    public Result<Page<ActivityOfficeProgressVO>> page(@ApiParam(value = "机关活动进度记录查询条件", required = true) @RequestBody ActivityOfficeProgressSearchable activityOfficeProgressSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<ActivityOfficeProgress> activityOfficeProgressPage = activityOfficeProgressService.findAll(activityOfficeProgressSearchable, pageable);
        Page<ActivityOfficeProgressVO> activityOfficeProgressVOPage = ActivityOfficeProgress.convert(activityOfficeProgressPage, ActivityOfficeProgressVO.class);
        return Result.of(activityOfficeProgressVOPage);
    }

}