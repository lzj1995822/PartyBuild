package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.ParActivityController;
import com.cloudkeeper.leasing.identity.domain.ParActivity;
import com.cloudkeeper.leasing.identity.dto.paractivity.ParActivityDTO;
import com.cloudkeeper.leasing.identity.dto.paractivity.ParActivitySearchable;
import com.cloudkeeper.leasing.identity.service.ParActivityService;
import com.cloudkeeper.leasing.identity.service.SysLogService;
import com.cloudkeeper.leasing.identity.vo.ParActivityVO;
import com.cloudkeeper.leasing.identity.vo.TVIndexVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 活动 controller
 * @author lxw
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityControllerImpl implements ParActivityController {

    /** 活动 service */
    private final ParActivityService parActivityService;

    private final SysLogService sysLogService;

    @Override
    @Authorization(required = false)
    public Result<ParActivityVO> findOne(@ApiParam(value = "活动id", required = true) @PathVariable String id) {
        Optional<ParActivity> parActivityOptional = parActivityService.findOptionalById(id);
        return parActivityOptional.map(parActivity -> Result.of(parActivity.convert(ParActivityVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    @Transactional
    public Result<ParActivityVO> add(@ApiParam(value = "活动 DTO", required = true) @RequestBody @Validated ParActivityDTO parActivityDTO) {
        ParActivityVO parActivityVO = parActivityService.save(parActivityDTO);
        String  msg= parActivityService.actionLog("发布", parActivityVO.getTaskType(), parActivityVO.getTitle());
        sysLogService.pushLog(this.getClass().getName(),msg,parActivityService.getTableName(),parActivityVO.getId());
        return Result.ofAddSuccess(parActivityVO);
    }

    @Override
    public Result<ParActivityVO> update(@ApiParam(value = "活动id", required = true) @PathVariable String id,
        @ApiParam(value = "活动 DTO", required = true) @RequestBody @Validated ParActivityDTO parActivityDTO) {
        Optional<ParActivity> parActivityOptional = parActivityService.findOptionalById(id);
        if (!parActivityOptional.isPresent()) {
            return Result.ofLost();
        }
        ParActivity parActivity = parActivityOptional.get();
        BeanUtils.copyProperties(parActivityDTO, parActivity);
        parActivity = parActivityService.save(parActivity);

        String  msg= parActivityService.actionLog("修改", parActivity.getTaskType(), parActivity.getTitle());
        sysLogService.pushLog(this.getClass().getName(),msg,parActivityService.getTableName(),parActivity.getId());
        return Result.ofUpdateSuccess(parActivity.convert(ParActivityVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "活动id", required = true) @PathVariable String id) {
        ParActivity byId = parActivityService.findById(id);
        parActivityService.deleteById(id);
        String  msg= parActivityService.actionLog("删除", byId.getTaskType(), byId.getTitle());
        sysLogService.pushLog(this.getClass().getName(),msg,parActivityService.getTableName(),byId.getId());
        return Result.ofDeleteSuccess();
    }

    @Authorization(required = false)
    @Override
    public Result<List<ParActivityVO>> list(@ApiParam(value = "活动查询条件", required = true) @RequestBody ParActivitySearchable parActivitySearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<ParActivity> parActivityList = parActivityService.findAll(parActivitySearchable, sort);
        List<ParActivityVO> parActivityVOList = ParActivity.convert(parActivityList, ParActivityVO.class);
        return Result.of(parActivityVOList);
    }

    @Override
    public Result<Page<ParActivityVO>> page(@ApiParam(value = "活动查询条件", required = true) @RequestBody ParActivitySearchable parActivitySearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<ParActivity> parActivityPage = parActivityService.handleDifferentRole(parActivitySearchable, pageable);
        Page<ParActivityVO> parActivityVOPage = ParActivity.convert(parActivityPage, ParActivityVO.class);
        return Result.of(parActivityVOPage);
    }

    @Override
    public Result<ParActivityVO> updateAlarmTime(@ApiParam(value = "活动id", required = true) @PathVariable String id,
                                                 @ApiParam(value = "提醒时间", required = true) @RequestBody ParActivityDTO parActivityDTO){
        ParActivityVO parActivityVO = parActivityService.updateAlarmTime(id,parActivityDTO.getAlarmTime());
        if(StringUtils.isEmpty(parActivityVO)){
            return Result.ofLost();
        }
        return Result.ofUpdateSuccess(parActivityVO);
    }

    @Override
    public Result deleteAll(@ApiParam(value = "活动id", required = true) @PathVariable String id) {
        parActivityService.deleteAll(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result updateProject() {
        List<ParActivity> all = parActivityService.findAll();
        all.stream().forEach(item -> {
            parActivityService.updateProgress(item.getId());
        });
        return Result.of("更新成功");
    }

    @Override
    @Authorization(required = false)
    public Result<TVIndexVO> tv() {
        return parActivityService.tvIndex();
    }


}
