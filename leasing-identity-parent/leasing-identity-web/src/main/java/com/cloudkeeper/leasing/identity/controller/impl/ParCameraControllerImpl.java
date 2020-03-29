package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.ParCameraController;
import com.cloudkeeper.leasing.identity.domain.ParCamera;
import com.cloudkeeper.leasing.identity.dto.parcamera.ParCameraDTO;
import com.cloudkeeper.leasing.identity.dto.parcamera.ParCameraSearchable;
import com.cloudkeeper.leasing.identity.service.ParCameraService;
import com.cloudkeeper.leasing.identity.service.SysLogService;
import com.cloudkeeper.leasing.identity.vo.ParCameraVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * 监控信息 controller
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParCameraControllerImpl implements ParCameraController {

    /** 监控信息 service */
    private final ParCameraService parCameraService;

    private final SysLogService sysLogService;

    @Override
    public Result<ParCameraVO> findOne(@ApiParam(value = "监控信息id", required = true) @PathVariable String id) {
        Optional<ParCamera> parCameraOptional = parCameraService.findOptionalById(id);
        return parCameraOptional.map(parCamera -> Result.of(parCamera.convert(ParCameraVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<ParCameraVO> add(@ApiParam(value = "监控信息 DTO", required = true) @RequestBody @Validated ParCameraDTO parCameraDTO) {
        ParCamera parCamera = parCameraService.save(parCameraDTO.convert(ParCamera.class));
        String  msg= parCameraService.actionLog("新增","[监控信息]", parCamera.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,parCameraService.getTableName(),parCamera.getId());
        return Result.ofAddSuccess(parCamera.convert(ParCameraVO.class));
    }

    @Override
    public Result<ParCameraVO> update(@ApiParam(value = "监控信息id", required = true) @PathVariable String id,
        @ApiParam(value = "监控信息 DTO", required = true) @RequestBody @Validated ParCameraDTO parCameraDTO) {
        Optional<ParCamera> parCameraOptional = parCameraService.findOptionalById(id);
        if (!parCameraOptional.isPresent()) {
            return Result.ofLost();
        }
        ParCamera parCamera = parCameraOptional.get();
        BeanUtils.copyProperties(parCameraDTO, parCamera);
        parCamera = parCameraService.save(parCamera);
        String  msg= parCameraService.actionLog("修改","[监控信息]", parCamera.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,parCameraService.getTableName(),parCamera.getId());
        return Result.ofUpdateSuccess(parCamera.convert(ParCameraVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "监控信息id", required = true) @PathVariable String id) {
        ParCamera parCamera = parCameraService.findById(id);
        parCameraService.deleteById(id);
        String  msg= parCameraService.actionLog("删除","[监控信息]", parCamera.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,parCameraService.getTableName(),parCamera.getId());
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<ParCameraVO>> list(@ApiParam(value = "监控信息查询条件", required = true) @RequestBody ParCameraSearchable parCameraSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<ParCamera> parCameraList = parCameraService.findAll(parCameraSearchable, sort);
        List<ParCameraVO> parCameraVOList = ParCamera.convert(parCameraList, ParCameraVO.class);
        return Result.of(parCameraVOList);
    }

    @Override
    public Result<Page<ParCameraVO>> page(@ApiParam(value = "监控信息查询条件", required = true) @RequestBody ParCameraSearchable parCameraSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<ParCamera> parCameraPage = parCameraService.findAll(parCameraSearchable, pageable);
        Page<ParCameraVO> parCameraVOPage = ParCamera.convert(parCameraPage, ParCameraVO.class);
        return Result.of(parCameraVOPage);
    }

    @Override
    @Authorization(required = false)
    public Result<ParCameraVO> findByNumber(@PathVariable String number) {
        ParCamera byNumber = parCameraService.findByNumber(number);
        return Result.of(byNumber.convert(ParCameraVO.class));
    }

    @Authorization(required = true)
    @Override
    public Result<ParCamera> findRedisIp(String key){
        ParCamera parCamera = parCameraService.redisIp(key);
        return Result.of(parCamera);
    }

}
