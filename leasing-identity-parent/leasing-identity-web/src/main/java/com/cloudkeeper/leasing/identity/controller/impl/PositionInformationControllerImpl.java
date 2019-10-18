package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.PositionInformationController;
import com.cloudkeeper.leasing.identity.domain.PositionInformation;
import com.cloudkeeper.leasing.identity.dto.positioninformation.PositionInformationDTO;
import com.cloudkeeper.leasing.identity.dto.positioninformation.PositionInformationSearchable;
import com.cloudkeeper.leasing.identity.service.PositionInformationService;
import com.cloudkeeper.leasing.identity.service.SysLogService;
import com.cloudkeeper.leasing.identity.vo.PositionInformationVO;
import com.cloudkeeper.leasing.identity.vo.PositionNumberVO;
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
 * 阵地信息 controller
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PositionInformationControllerImpl implements PositionInformationController {

    /** 阵地信息 service */
    private final PositionInformationService positionInformationService;

    private final SysLogService sysLogService;

    @Override
    public Result<PositionInformationVO> findOne(@ApiParam(value = "阵地信息id", required = true) @PathVariable String id) {
        Optional<PositionInformation> positionInformationOptional = positionInformationService.findOptionalById(id);
        return positionInformationOptional.map(positionInformation -> Result.of(positionInformation.convert(PositionInformationVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<PositionInformationVO> add(@ApiParam(value = "阵地信息 DTO", required = true) @RequestBody @Validated PositionInformationDTO positionInformationDTO) {
        PositionInformation positionInformation = positionInformationService.save(positionInformationDTO.convert(PositionInformation.class));
        String  msg= positionInformationService.actionLog("新增","[基本阵地信息]", positionInformation.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,positionInformationService.getTableName(),positionInformation.getId());
        return Result.ofAddSuccess(positionInformation.convert(PositionInformationVO.class));
    }

    @Override
    public Result<PositionInformationVO> update(@ApiParam(value = "阵地信息id", required = true) @PathVariable String id,
        @ApiParam(value = "阵地信息 DTO", required = true) @RequestBody @Validated PositionInformationDTO positionInformationDTO) {
        Optional<PositionInformation> positionInformationOptional = positionInformationService.findOptionalById(id);
        if (!positionInformationOptional.isPresent()) {
            return Result.ofLost();
        }
        PositionInformation positionInformation = positionInformationOptional.get();
        BeanUtils.copyProperties(positionInformationDTO, positionInformation);
        positionInformation = positionInformationService.save(positionInformation);
        String  msg= positionInformationService.actionLog("修改","[基本阵地信息]", positionInformation.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,positionInformationService.getTableName(),positionInformation.getId());
        return Result.ofUpdateSuccess(positionInformation.convert(PositionInformationVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "阵地信息id", required = true) @PathVariable String id) {
        PositionInformation positionInformation = positionInformationService.findById(id);
        positionInformationService.deleteById(id);
        String  msg= positionInformationService.actionLog("删除","[基本阵地信息]", positionInformation.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,positionInformationService.getTableName(),positionInformation.getId());
        return Result.ofDeleteSuccess();
    }

    @Authorization(required = false)
    @Override
    public Result<List<PositionInformationVO>> list(@ApiParam(value = "阵地信息查询条件", required = true) @RequestBody PositionInformationSearchable positionInformationSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<PositionInformation> positionInformationList = positionInformationService.findAll(positionInformationSearchable, sort);
        List<PositionInformationVO> positionInformationVOList = PositionInformation.convert(positionInformationList, PositionInformationVO.class);
        return Result.of(positionInformationVOList);
    }

    @Override
    public Result<Page<PositionInformationVO>> page(@ApiParam(value = "阵地信息查询条件", required = true) @RequestBody PositionInformationSearchable positionInformationSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<PositionInformation> positionInformationPage = positionInformationService.findAll(positionInformationSearchable, pageable);
        Page<PositionInformationVO> positionInformationVOPage = PositionInformation.convert(positionInformationPage, PositionInformationVO.class);
        return Result.of(positionInformationVOPage);
    }
    @Override
    public Result<PositionNumberVO> positionNumber(@ApiParam(value = "组织ID", required = true)String  districtId){
        PositionNumberVO positionNumberVO = positionInformationService.positionNumber(districtId);
        return Result.of(200,"success",positionNumberVO);
    }
}
