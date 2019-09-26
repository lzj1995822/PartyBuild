package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.SumPerHourController;
import com.cloudkeeper.leasing.identity.domain.SumPerHour;
import com.cloudkeeper.leasing.identity.dto.sumperhour.SumPerHourDTO;
import com.cloudkeeper.leasing.identity.dto.sumperhour.SumPerHourSearchable;
import com.cloudkeeper.leasing.identity.service.SumPerHourService;
import com.cloudkeeper.leasing.identity.vo.SumPerHourVO;
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
 * 每小时人流量 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SumPerHourControllerImpl implements SumPerHourController {

    /** 每小时人流量 service */
    private final SumPerHourService sumPerHourService;

    @Override
    public Result<SumPerHourVO> findOne(@ApiParam(value = "每小时人流量id", required = true) @PathVariable String id) {
        Optional<SumPerHour> sumPerHourOptional = sumPerHourService.findOptionalById(id);
        return sumPerHourOptional.map(sumPerHour -> Result.of(sumPerHour.convert(SumPerHourVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<SumPerHourVO> add(@ApiParam(value = "每小时人流量 DTO", required = true) @RequestBody @Validated SumPerHourDTO sumPerHourDTO) {
        SumPerHour sumPerHour = sumPerHourService.save(sumPerHourDTO.convert(SumPerHour.class));
        return Result.ofAddSuccess(sumPerHour.convert(SumPerHourVO.class));
    }

    @Override
    public Result<SumPerHourVO> update(@ApiParam(value = "每小时人流量id", required = true) @PathVariable String id,
        @ApiParam(value = "每小时人流量 DTO", required = true) @RequestBody @Validated SumPerHourDTO sumPerHourDTO) {
        Optional<SumPerHour> sumPerHourOptional = sumPerHourService.findOptionalById(id);
        if (!sumPerHourOptional.isPresent()) {
            return Result.ofLost();
        }
        SumPerHour sumPerHour = sumPerHourOptional.get();
        BeanUtils.copyProperties(sumPerHourDTO, sumPerHour);
        sumPerHour = sumPerHourService.save(sumPerHour);
        return Result.ofUpdateSuccess(sumPerHour.convert(SumPerHourVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "每小时人流量id", required = true) @PathVariable String id) {
        sumPerHourService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<SumPerHourVO>> list(@ApiParam(value = "每小时人流量查询条件", required = true) @RequestBody SumPerHourSearchable sumPerHourSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<SumPerHour> sumPerHourList = sumPerHourService.findAll(sumPerHourSearchable, sort);
        List<SumPerHourVO> sumPerHourVOList = SumPerHour.convert(sumPerHourList, SumPerHourVO.class);
        return Result.of(sumPerHourVOList);
    }

    @Override
    public Result<Page<SumPerHourVO>> page(@ApiParam(value = "每小时人流量查询条件", required = true) @RequestBody SumPerHourSearchable sumPerHourSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<SumPerHour> sumPerHourPage = sumPerHourService.findAll(sumPerHourSearchable, pageable);
        Page<SumPerHourVO> sumPerHourVOPage = SumPerHour.convert(sumPerHourPage, SumPerHourVO.class);
        return Result.of(sumPerHourVOPage);
    }

}