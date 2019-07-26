package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.AcceptInformationController;
import com.cloudkeeper.leasing.identity.domain.AcceptInformation;
import com.cloudkeeper.leasing.identity.dto.acceptinformation.AcceptInformationDTO;
import com.cloudkeeper.leasing.identity.dto.acceptinformation.AcceptInformationSearchable;
import com.cloudkeeper.leasing.identity.service.AcceptInformationService;
import com.cloudkeeper.leasing.identity.service.InformationService;
import com.cloudkeeper.leasing.identity.service.SysLogService;
import com.cloudkeeper.leasing.identity.vo.AcceptInformationVO;
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
 * 接收公告 controller
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AcceptInformationControllerImpl implements AcceptInformationController {

    /** 接收公告 service */
    private final AcceptInformationService acceptInformationService;

    private final SysLogService sysLogService;

    @Override
    public Result<AcceptInformationVO> findOne(@ApiParam(value = "接收公告id", required = true) @PathVariable String id) {
        Optional<AcceptInformation> acceptInformationOptional = acceptInformationService.findOptionalById(id);
        return acceptInformationOptional.map(acceptInformation -> Result.of(acceptInformation.convert(AcceptInformationVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<AcceptInformationVO> add(@ApiParam(value = "接收公告 DTO", required = true) @RequestBody @Validated AcceptInformationDTO acceptInformationDTO) {
        AcceptInformation acceptInformation = acceptInformationService.save(acceptInformationDTO.convert(AcceptInformation.class));
        return Result.ofAddSuccess(acceptInformation.convert(AcceptInformationVO.class));
    }

    @Override
    public Result<AcceptInformationVO> update(@ApiParam(value = "接收公告id", required = true) @PathVariable String id,
        @ApiParam(value = "接收公告 DTO", required = true) @RequestBody @Validated AcceptInformationDTO acceptInformationDTO) {
        Optional<AcceptInformation> acceptInformationOptional = acceptInformationService.findOptionalById(id);
        if (!acceptInformationOptional.isPresent()) {
            return Result.ofLost();
        }
        AcceptInformation acceptInformation = acceptInformationOptional.get();
        BeanUtils.copyProperties(acceptInformationDTO, acceptInformation);
        acceptInformation = acceptInformationService.save(acceptInformation);
        String  msg= acceptInformationService.actionLog("查收","[通知公告]", acceptInformation.getInformation().getTitle());
        sysLogService.pushLog(this.getClass().getName(),msg,acceptInformationService.getTableName(),acceptInformation.getId());
        return Result.ofUpdateSuccess(acceptInformation.convert(AcceptInformationVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "接收公告id", required = true) @PathVariable String id) {
        acceptInformationService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<AcceptInformationVO>> list(@ApiParam(value = "接收公告查询条件", required = true) @RequestBody AcceptInformationSearchable acceptInformationSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<AcceptInformation> acceptInformationList = acceptInformationService.findAll(acceptInformationSearchable, sort);
        List<AcceptInformationVO> acceptInformationVOList = AcceptInformation.convert(acceptInformationList, AcceptInformationVO.class);
        return Result.of(acceptInformationVOList);
    }

    @Override
    public Result<Page<AcceptInformationVO>> page(@ApiParam(value = "接收公告查询条件", required = true) @RequestBody AcceptInformationSearchable acceptInformationSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<AcceptInformation> acceptInformationPage = acceptInformationService.findAll(acceptInformationSearchable, pageable);
        Page<AcceptInformationVO> acceptInformationVOPage = AcceptInformation.convert(acceptInformationPage, AcceptInformationVO.class);
        return Result.of(acceptInformationVOPage);
    }

}