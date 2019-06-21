package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.ParActivityObjectController;
import com.cloudkeeper.leasing.identity.domain.ParActivityObject;
import com.cloudkeeper.leasing.identity.dto.paractivityobject.ParActivityObjectDTO;
import com.cloudkeeper.leasing.identity.dto.paractivityobject.ParActivityObjectSearchable;
import com.cloudkeeper.leasing.identity.service.ParActivityObjectService;
import com.cloudkeeper.leasing.identity.vo.ParActivityObjectVO;
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
 * 任务对象 controller
 * @author lxw
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityObjectControllerImpl implements ParActivityObjectController {

    /** 任务对象 service */
    private final ParActivityObjectService parActivityObjectService;

    @Override
    public Result<ParActivityObjectVO> findOne(@ApiParam(value = "任务对象id", required = true) @PathVariable String id) {
        Optional<ParActivityObject> parActivityObjectOptional = parActivityObjectService.findOptionalById(id);
        return parActivityObjectOptional.map(parActivityObject -> Result.of(parActivityObject.convert(ParActivityObjectVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<ParActivityObjectVO> add(@ApiParam(value = "任务对象 DTO", required = true) @RequestBody @Validated ParActivityObjectDTO parActivityObjectDTO) {
        ParActivityObject parActivityObject = parActivityObjectService.save(parActivityObjectDTO.convert(ParActivityObject.class));
        return Result.ofAddSuccess(parActivityObject.convert(ParActivityObjectVO.class));
    }

    @Override
    public Result<ParActivityObjectVO> update(@ApiParam(value = "任务对象id", required = true) @PathVariable String id,
        @ApiParam(value = "任务对象 DTO", required = true) @RequestBody @Validated ParActivityObjectDTO parActivityObjectDTO) {
        Optional<ParActivityObject> parActivityObjectOptional = parActivityObjectService.findOptionalById(id);
        if (!parActivityObjectOptional.isPresent()) {
            return Result.ofLost();
        }
        ParActivityObject parActivityObject = parActivityObjectOptional.get();
        BeanUtils.copyProperties(parActivityObjectDTO, parActivityObject);
        parActivityObject = parActivityObjectService.save(parActivityObject);
        return Result.ofUpdateSuccess(parActivityObject.convert(ParActivityObjectVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "任务对象id", required = true) @PathVariable String id) {
        parActivityObjectService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<ParActivityObjectVO>> list(@ApiParam(value = "任务对象查询条件", required = true) @RequestBody ParActivityObjectSearchable parActivityObjectSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<ParActivityObject> parActivityObjectList = parActivityObjectService.findAll(parActivityObjectSearchable, sort);
        List<ParActivityObjectVO> parActivityObjectVOList = ParActivityObject.convert(parActivityObjectList, ParActivityObjectVO.class);
        return Result.of(parActivityObjectVOList);
    }

    @Override
    public Result<Page<ParActivityObjectVO>> page(@ApiParam(value = "任务对象查询条件", required = true) @RequestBody ParActivityObjectSearchable parActivityObjectSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<ParActivityObject> parActivityObjectPage = parActivityObjectService.findAll(parActivityObjectSearchable, pageable);
        Page<ParActivityObjectVO> parActivityObjectVOPage = ParActivityObject.convert(parActivityObjectPage, ParActivityObjectVO.class);
        return Result.of(parActivityObjectVOPage);
    }

    /**
     * 根据组织di和活动id查对应的对象记录
     * @param parActivityObjectDTO
     * @return
     */
    @Override
    public Result<ParActivityObjectVO> findByOrganizationIdAndActivityId(@ApiParam(value = "任务对象 DTO", required = true) @RequestBody @Validated ParActivityObjectDTO parActivityObjectDTO) {
        ParActivityObject parActivityObject = parActivityObjectService.findByOrganizationIdAndActivityId(parActivityObjectDTO.getOrganizationId(), parActivityObjectDTO.getActivityId());
        return Result.of(parActivityObject.convert(ParActivityObjectVO.class));
    }

    @Override
    public Result<Boolean> init() {
        parActivityObjectService.initPerActivity();
        return Result.of(true);
    }

}