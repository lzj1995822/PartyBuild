package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.PeopleStreamController;
import com.cloudkeeper.leasing.identity.domain.PeopleStream;
import com.cloudkeeper.leasing.identity.dto.peoplestream.PeopleStreamDTO;
import com.cloudkeeper.leasing.identity.dto.peoplestream.PeopleStreamSearchable;
import com.cloudkeeper.leasing.identity.service.PeopleStreamService;
import com.cloudkeeper.leasing.identity.vo.PeopleStreamVO;
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
 * 阵地人流量 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PeopleStreamControllerImpl implements PeopleStreamController {

    /** 阵地人流量 service */
    private final PeopleStreamService peopleStreamService;

    @Override
    public Result<PeopleStreamVO> findOne(@ApiParam(value = "阵地人流量id", required = true) @PathVariable String id) {
        Optional<PeopleStream> peopleStreamOptional = peopleStreamService.findOptionalById(id);
        return peopleStreamOptional.map(peopleStream -> Result.of(peopleStream.convert(PeopleStreamVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<PeopleStreamVO> add(@ApiParam(value = "阵地人流量 DTO", required = true) @RequestBody @Validated PeopleStreamDTO peopleStreamDTO) {
        PeopleStream peopleStream = peopleStreamService.save(peopleStreamDTO.convert(PeopleStream.class));
        return Result.ofAddSuccess(peopleStream.convert(PeopleStreamVO.class));
    }

    @Override
    public Result<PeopleStreamVO> update(@ApiParam(value = "阵地人流量id", required = true) @PathVariable String id,
        @ApiParam(value = "阵地人流量 DTO", required = true) @RequestBody @Validated PeopleStreamDTO peopleStreamDTO) {
        Optional<PeopleStream> peopleStreamOptional = peopleStreamService.findOptionalById(id);
        if (!peopleStreamOptional.isPresent()) {
            return Result.ofLost();
        }
        PeopleStream peopleStream = peopleStreamOptional.get();
        BeanUtils.copyProperties(peopleStreamDTO, peopleStream);
        peopleStream = peopleStreamService.save(peopleStream);
        return Result.ofUpdateSuccess(peopleStream.convert(PeopleStreamVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "阵地人流量id", required = true) @PathVariable String id) {
        peopleStreamService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<PeopleStreamVO>> list(@ApiParam(value = "阵地人流量查询条件", required = true) @RequestBody PeopleStreamSearchable peopleStreamSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<PeopleStream> peopleStreamList = peopleStreamService.findAll(peopleStreamSearchable, sort);
        List<PeopleStreamVO> peopleStreamVOList = PeopleStream.convert(peopleStreamList, PeopleStreamVO.class);
        return Result.of(peopleStreamVOList);
    }

    @Override
    public Result<Page<PeopleStreamVO>> page(@ApiParam(value = "阵地人流量查询条件", required = true) @RequestBody PeopleStreamSearchable peopleStreamSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<PeopleStream> peopleStreamPage = peopleStreamService.findAll(peopleStreamSearchable, pageable);
        Page<PeopleStreamVO> peopleStreamVOPage = PeopleStream.convert(peopleStreamPage, PeopleStreamVO.class);
        return Result.of(peopleStreamVOPage);
    }

}