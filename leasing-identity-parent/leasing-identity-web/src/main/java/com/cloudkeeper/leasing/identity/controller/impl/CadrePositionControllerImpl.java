package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.CadrePositionController;
import com.cloudkeeper.leasing.identity.domain.CadrePosition;
import com.cloudkeeper.leasing.identity.dto.cadreposition.CadrePositionDTO;
import com.cloudkeeper.leasing.identity.dto.cadreposition.CadrePositionSearchable;
import com.cloudkeeper.leasing.identity.service.CadrePositionService;
import com.cloudkeeper.leasing.identity.vo.CadrePositionVO;
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
 * 岗位管理 controller
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadrePositionControllerImpl implements CadrePositionController {

    /** 岗位管理 service */
    private final CadrePositionService cadrePositionService;

    @Override
    public Result<CadrePositionVO> findOne(@ApiParam(value = "岗位管理id", required = true) @PathVariable String id) {
        Optional<CadrePosition> cadrePositionOptional = cadrePositionService.findOptionalById(id);
        return cadrePositionOptional.map(cadrePosition -> Result.of(cadrePosition.convert(CadrePositionVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<CadrePositionVO> add(@ApiParam(value = "岗位管理 DTO", required = true) @RequestBody @Validated CadrePositionDTO cadrePositionDTO) {
        CadrePosition cadrePosition = cadrePositionService.save(cadrePositionDTO.convert(CadrePosition.class));
        return Result.ofAddSuccess(cadrePosition.convert(CadrePositionVO.class));
    }

    @Override
    public Result<CadrePositionVO> update(@ApiParam(value = "岗位管理id", required = true) @PathVariable String id,
        @ApiParam(value = "岗位管理 DTO", required = true) @RequestBody @Validated CadrePositionDTO cadrePositionDTO) {
        Optional<CadrePosition> cadrePositionOptional = cadrePositionService.findOptionalById(id);
        if (!cadrePositionOptional.isPresent()) {
            return Result.ofLost();
        }
        CadrePosition cadrePosition = cadrePositionOptional.get();
        BeanUtils.copyProperties(cadrePositionDTO, cadrePosition);
        cadrePosition = cadrePositionService.save(cadrePosition);
        return Result.ofUpdateSuccess(cadrePosition.convert(CadrePositionVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "岗位管理id", required = true) @PathVariable String id) {
        cadrePositionService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<CadrePositionVO>> list(@ApiParam(value = "岗位管理查询条件", required = true) @RequestBody CadrePositionSearchable cadrePositionSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<CadrePosition> cadrePositionList = cadrePositionService.findAll(cadrePositionSearchable, sort);
        List<CadrePositionVO> cadrePositionVOList = CadrePosition.convert(cadrePositionList, CadrePositionVO.class);
        return Result.of(cadrePositionVOList);
    }

    @Override
    public Result<Page<CadrePositionVO>> page(@ApiParam(value = "岗位管理查询条件", required = true) @RequestBody CadrePositionSearchable cadrePositionSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<CadrePosition> cadrePositionPage = cadrePositionService.findAll(cadrePositionSearchable, pageable);
        Page<CadrePositionVO> cadrePositionVOPage = CadrePosition.convert(cadrePositionPage, CadrePositionVO.class);
        return Result.of(cadrePositionVOPage);
    }

}