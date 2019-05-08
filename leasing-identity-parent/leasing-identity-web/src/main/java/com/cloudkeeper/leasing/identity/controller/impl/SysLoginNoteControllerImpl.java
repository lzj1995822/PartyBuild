package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.SysLoginNoteController;
import com.cloudkeeper.leasing.identity.domain.SysLoginNote;
import com.cloudkeeper.leasing.identity.dto.sysloginnote.SysLoginNoteDTO;
import com.cloudkeeper.leasing.identity.dto.sysloginnote.SysLoginNoteSearchable;
import com.cloudkeeper.leasing.identity.service.SysLoginNoteService;
import com.cloudkeeper.leasing.identity.vo.SysLoginNoteVO;
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
 * 系统登录日志 controller
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysLoginNoteControllerImpl implements SysLoginNoteController {

    /** 系统登录日志 service */
    private final SysLoginNoteService sysLoginNoteService;

    @Override
    public Result<SysLoginNoteVO> findOne(@ApiParam(value = "系统登录日志id", required = true) @PathVariable String id) {
        Optional<SysLoginNote> sysLoginNoteOptional = sysLoginNoteService.findOptionalById(id);
        return sysLoginNoteOptional.map(sysLoginNote -> Result.of(sysLoginNote.convert(SysLoginNoteVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<SysLoginNoteVO> add(@ApiParam(value = "系统登录日志 DTO", required = true) @RequestBody @Validated SysLoginNoteDTO sysLoginNoteDTO) {
        SysLoginNote sysLoginNote = sysLoginNoteService.save(sysLoginNoteDTO.convert(SysLoginNote.class));
        return Result.ofAddSuccess(sysLoginNote.convert(SysLoginNoteVO.class));
    }

    @Override
    public Result<SysLoginNoteVO> update(@ApiParam(value = "系统登录日志id", required = true) @PathVariable String id,
        @ApiParam(value = "系统登录日志 DTO", required = true) @RequestBody @Validated SysLoginNoteDTO sysLoginNoteDTO) {
        Optional<SysLoginNote> sysLoginNoteOptional = sysLoginNoteService.findOptionalById(id);
        if (!sysLoginNoteOptional.isPresent()) {
            return Result.ofLost();
        }
        SysLoginNote sysLoginNote = sysLoginNoteOptional.get();
        BeanUtils.copyProperties(sysLoginNoteDTO, sysLoginNote);
        sysLoginNote = sysLoginNoteService.save(sysLoginNote);
        return Result.ofUpdateSuccess(sysLoginNote.convert(SysLoginNoteVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "系统登录日志id", required = true) @PathVariable String id) {
        sysLoginNoteService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<SysLoginNoteVO>> list(@ApiParam(value = "系统登录日志查询条件", required = true) @RequestBody SysLoginNoteSearchable sysLoginNoteSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<SysLoginNote> sysLoginNoteList = sysLoginNoteService.findAll(sysLoginNoteSearchable, sort);
        List<SysLoginNoteVO> sysLoginNoteVOList = SysLoginNote.convert(sysLoginNoteList, SysLoginNoteVO.class);
        return Result.of(sysLoginNoteVOList);
    }

    @Override
    public Result<Page<SysLoginNoteVO>> page(@ApiParam(value = "系统登录日志查询条件", required = true) @RequestBody SysLoginNoteSearchable sysLoginNoteSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<SysLoginNote> sysLoginNotePage = sysLoginNoteService.findAll(sysLoginNoteSearchable, pageable);
        Page<SysLoginNoteVO> sysLoginNoteVOPage = SysLoginNote.convert(sysLoginNotePage, SysLoginNoteVO.class);
        return Result.of(sysLoginNoteVOPage);
    }

}