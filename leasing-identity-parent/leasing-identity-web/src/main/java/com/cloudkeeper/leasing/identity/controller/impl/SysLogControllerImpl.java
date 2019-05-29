package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.SysLogController;
import com.cloudkeeper.leasing.identity.domain.SysLog;
import com.cloudkeeper.leasing.identity.dto.syslog.SysLogDTO;
import com.cloudkeeper.leasing.identity.dto.syslog.SysLogSearchable;
import com.cloudkeeper.leasing.identity.service.SysLogService;
import com.cloudkeeper.leasing.identity.vo.SysLogVO;
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
 * 系统日志 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysLogControllerImpl implements SysLogController {

    /** 系统日志 service */
    private final SysLogService sysLogService;

    @Override
    public Result<SysLogVO> findOne(@ApiParam(value = "系统日志id", required = true) @PathVariable String id) {
        Optional<SysLog> sysLogOptional = sysLogService.findOptionalById(id);
        return sysLogOptional.map(sysLog -> Result.of(sysLog.convert(SysLogVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<SysLogVO> add(@ApiParam(value = "系统日志 DTO", required = true) @RequestBody @Validated SysLogDTO sysLogDTO) {
        SysLog sysLog = sysLogService.save(sysLogDTO.convert(SysLog.class));
        return Result.ofAddSuccess(sysLog.convert(SysLogVO.class));
    }

    @Override
    public Result<SysLogVO> update(@ApiParam(value = "系统日志id", required = true) @PathVariable String id,
        @ApiParam(value = "系统日志 DTO", required = true) @RequestBody @Validated SysLogDTO sysLogDTO) {
        Optional<SysLog> sysLogOptional = sysLogService.findOptionalById(id);
        if (!sysLogOptional.isPresent()) {
            return Result.ofLost();
        }
        SysLog sysLog = sysLogOptional.get();
        BeanUtils.copyProperties(sysLogDTO, sysLog);
        sysLog = sysLogService.save(sysLog);
        return Result.ofUpdateSuccess(sysLog.convert(SysLogVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "系统日志id", required = true) @PathVariable String id) {
        sysLogService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<SysLogVO>> list(@ApiParam(value = "系统日志查询条件", required = true) @RequestBody SysLogSearchable sysLogSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<SysLog> sysLogList = sysLogService.findAll(sysLogSearchable, sort);
        List<SysLogVO> sysLogVOList = SysLog.convert(sysLogList, SysLogVO.class);
        return Result.of(sysLogVOList);
    }

    @Override
    public Result<Page<SysLogVO>> page(@ApiParam(value = "系统日志查询条件", required = true) @RequestBody SysLogSearchable sysLogSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<SysLog> sysLogPage = sysLogService.findAll(sysLogSearchable, pageable);
        Page<SysLogVO> sysLogVOPage = SysLog.convert(sysLogPage, SysLogVO.class);
        return Result.of(sysLogVOPage);
    }

}