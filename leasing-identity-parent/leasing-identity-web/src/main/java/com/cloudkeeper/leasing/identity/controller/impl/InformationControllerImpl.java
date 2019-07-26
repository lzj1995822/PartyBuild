package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.InformationController;
import com.cloudkeeper.leasing.identity.domain.Information;
import com.cloudkeeper.leasing.identity.dto.information.InformationDTO;
import com.cloudkeeper.leasing.identity.dto.information.InformationSearchable;
import com.cloudkeeper.leasing.identity.service.InformationService;
import com.cloudkeeper.leasing.identity.service.SysLogService;
import com.cloudkeeper.leasing.identity.vo.InformationVO;
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
 * 消息通知 controller
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InformationControllerImpl implements InformationController {

    /** 消息通知 service */
    private final InformationService informationService;

    private final SysLogService sysLogService;

    @Override
    public Result<InformationVO> findOne(@ApiParam(value = "消息通知id", required = true) @PathVariable String id) {
        Optional<Information> informationOptional = informationService.findOptionalById(id);
        return informationOptional.map(information -> Result.of(information.convert(InformationVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<InformationVO> add(@ApiParam(value = "消息通知 DTO", required = true) @RequestBody @Validated InformationDTO informationDTO) {
        Information information = informationService.save(informationDTO.convert(Information.class));
        String  msg= informationService.actionLog("发布","[通知公告]", information.getTitle());
        sysLogService.pushLog(this.getClass().getName(),msg,informationService.getTableName(),information.getId());
        return Result.ofAddSuccess(information.convert(InformationVO.class));
    }

    @Override
    public Result<InformationVO> update(@ApiParam(value = "消息通知id", required = true) @PathVariable String id,
        @ApiParam(value = "消息通知 DTO", required = true) @RequestBody @Validated InformationDTO informationDTO) {
        Optional<Information> informationOptional = informationService.findOptionalById(id);
        if (!informationOptional.isPresent()) {
            return Result.ofLost();
        }
        Information information = informationOptional.get();
        BeanUtils.copyProperties(informationDTO, information);
        information = informationService.save(information);
        String  msg= informationService.actionLog("修改","[通知公告]", information.getTitle());
        sysLogService.pushLog(this.getClass().getName(),msg,informationService.getTableName(),information.getId());
        return Result.ofUpdateSuccess(information.convert(InformationVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "消息通知id", required = true) @PathVariable String id) {
        Information information = informationService.findById(id);
        informationService.deleteById(id);
        String  msg= informationService.actionLog("删除","[通知公告]", information.getTitle());
        sysLogService.pushLog(this.getClass().getName(),msg,informationService.getTableName(),information.getId());
        return Result.ofDeleteSuccess();
    }

    @Authorization(required = false)
    @Override
    public Result<List<InformationVO>> list(@ApiParam(value = "消息通知查询条件", required = true) @RequestBody InformationSearchable informationSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<Information> informationList = informationService.findAll(informationSearchable, sort);
        List<InformationVO> informationVOList = Information.convert(informationList, InformationVO.class);
        return Result.of(informationVOList);
    }

    @Override
    public Result<Page<InformationVO>> page(@ApiParam(value = "消息通知查询条件", required = true) @RequestBody InformationSearchable informationSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<Information> informationPage = informationService.findAll(informationSearchable, pageable);
        Page<InformationVO> informationVOPage = Information.convert(informationPage, InformationVO.class);
        return Result.of(informationVOPage);
    }

}