package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.ReserveController;
import com.cloudkeeper.leasing.identity.domain.Reserve;
import com.cloudkeeper.leasing.identity.dto.reserve.ReserveDTO;
import com.cloudkeeper.leasing.identity.dto.reserve.ReserveSearchable;
import com.cloudkeeper.leasing.identity.service.ReserveService;
import com.cloudkeeper.leasing.identity.service.SysLogService;
import com.cloudkeeper.leasing.identity.vo.ReserveVO;
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
 * 后备人才 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReserveControllerImpl implements ReserveController {

    /** 后备人才 service */
    private final ReserveService reserveService;

    private final SysLogService sysLogService;

    @Override
    public Result<ReserveVO> findOne(@ApiParam(value = "后备人才id", required = true) @PathVariable String id) {
        Optional<Reserve> reserveOptional = reserveService.findOptionalById(id);
        return reserveOptional.map(reserve -> Result.of(reserve.convert(ReserveVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<ReserveVO> add(@ApiParam(value = "后备人才 DTO", required = true) @RequestBody @Validated ReserveDTO reserveDTO) {
        Reserve reserve = reserveService.save(reserveDTO.convert(Reserve.class));
        String  msg= reserveService.actionLog("新增","[后备人才信息]", reserve.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,reserveService.getTableName(),reserve.getId());
        return Result.ofAddSuccess(reserve.convert(ReserveVO.class));
    }

    @Override
    public Result<ReserveVO> update(@ApiParam(value = "后备人才id", required = true) @PathVariable String id,
        @ApiParam(value = "后备人才 DTO", required = true) @RequestBody @Validated ReserveDTO reserveDTO) {
        Optional<Reserve> reserveOptional = reserveService.findOptionalById(id);
        if (!reserveOptional.isPresent()) {
            return Result.ofLost();
        }
        Reserve reserve = reserveOptional.get();
        BeanUtils.copyProperties(reserveDTO, reserve);
        reserve = reserveService.save(reserve);
        String  msg= reserveService.actionLog("修改","[后备人才信息]", reserve.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,reserveService.getTableName(),reserve.getId());
        return Result.ofUpdateSuccess(reserve.convert(ReserveVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "后备人才id", required = true) @PathVariable String id) {
        Reserve reserve = reserveService.findById(id);
        reserveService.deleteById(id);
        String  msg= reserveService.actionLog("删除","[后备人才信息]", reserve.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,reserveService.getTableName(),reserve.getId());
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<ReserveVO>> list(@ApiParam(value = "后备人才查询条件", required = true) @RequestBody ReserveSearchable reserveSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<Reserve> reserveList = reserveService.findAll(reserveSearchable, sort);
        List<ReserveVO> reserveVOList = Reserve.convert(reserveList, ReserveVO.class);
        return Result.of(reserveVOList);
    }

    @Override
    public Result<Page<ReserveVO>> page(@ApiParam(value = "后备人才查询条件", required = true) @RequestBody ReserveSearchable reserveSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<Reserve> reservePage = reserveService.findAll(reserveSearchable, pageable);
        Page<ReserveVO> reserveVOPage = Reserve.convert(reservePage, ReserveVO.class);
        return Result.of(reserveVOPage);
    }

}