package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.WorkLedgerController;
import com.cloudkeeper.leasing.identity.domain.WorkLedger;
import com.cloudkeeper.leasing.identity.dto.workledger.WorkLedgerDTO;
import com.cloudkeeper.leasing.identity.dto.workledger.WorkLedgerSearchable;
import com.cloudkeeper.leasing.identity.service.WorkLedgerService;
import com.cloudkeeper.leasing.identity.vo.WorkLedgerVO;
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
 * 工作台账 controller
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WorkLedgerControllerImpl implements WorkLedgerController {

    /** 工作台账 service */
    private final WorkLedgerService workLedgerService;

    @Override
    public Result<WorkLedgerVO> findOne(@ApiParam(value = "工作台账id", required = true) @PathVariable String id) {
        Optional<WorkLedger> workLedgerOptional = workLedgerService.findOptionalById(id);
        return workLedgerOptional.map(workLedger -> Result.of(workLedger.convert(WorkLedgerVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<WorkLedgerVO> add(@ApiParam(value = "工作台账 DTO", required = true) @RequestBody @Validated WorkLedgerDTO workLedgerDTO) {
        WorkLedger workLedger = workLedgerService.save(workLedgerDTO.convert(WorkLedger.class));
        return Result.ofAddSuccess(workLedger.convert(WorkLedgerVO.class));
    }

    @Override
    public Result<WorkLedgerVO> update(@ApiParam(value = "工作台账id", required = true) @PathVariable String id,
        @ApiParam(value = "工作台账 DTO", required = true) @RequestBody @Validated WorkLedgerDTO workLedgerDTO) {
        Optional<WorkLedger> workLedgerOptional = workLedgerService.findOptionalById(id);
        if (!workLedgerOptional.isPresent()) {
            return Result.ofLost();
        }
        WorkLedger workLedger = workLedgerOptional.get();
        BeanUtils.copyProperties(workLedgerDTO, workLedger);
        workLedger = workLedgerService.save(workLedger);
        return Result.ofUpdateSuccess(workLedger.convert(WorkLedgerVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "工作台账id", required = true) @PathVariable String id) {
        workLedgerService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<WorkLedgerVO>> list(@ApiParam(value = "工作台账查询条件", required = true) @RequestBody WorkLedgerSearchable workLedgerSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<WorkLedger> workLedgerList = workLedgerService.findAll(workLedgerSearchable, sort);
        List<WorkLedgerVO> workLedgerVOList = WorkLedger.convert(workLedgerList, WorkLedgerVO.class);
        return Result.of(workLedgerVOList);
    }

    @Override
    public Result<Page<WorkLedgerVO>> page(@ApiParam(value = "工作台账查询条件", required = true) @RequestBody WorkLedgerSearchable workLedgerSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<WorkLedger> workLedgerPage = workLedgerService.findAll(workLedgerSearchable, pageable);
        Page<WorkLedgerVO> workLedgerVOPage = WorkLedger.convert(workLedgerPage, WorkLedgerVO.class);
        return Result.of(workLedgerVOPage);
    }

}