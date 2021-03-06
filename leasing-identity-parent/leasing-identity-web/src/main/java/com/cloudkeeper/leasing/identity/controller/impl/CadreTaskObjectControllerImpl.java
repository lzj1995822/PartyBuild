package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.CadreTaskObjectController;
import com.cloudkeeper.leasing.identity.domain.CadreTask;
import com.cloudkeeper.leasing.identity.domain.CadreTaskObject;
import com.cloudkeeper.leasing.identity.dto.cadretaskobject.CadreTaskObjectDTO;
import com.cloudkeeper.leasing.identity.dto.cadretaskobject.CadreTaskObjectSearchable;
import com.cloudkeeper.leasing.identity.service.CadreTaskObjectService;
import com.cloudkeeper.leasing.identity.service.CadreTaskService;
import com.cloudkeeper.leasing.identity.vo.CadreTaskObjectVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

/**
 * 村书记模块发布任务对象记录 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadreTaskObjectControllerImpl implements CadreTaskObjectController {

    /** 村书记模块发布任务对象记录 service */
    private final CadreTaskService cadreTaskService;

    /** 村书记模块发布任务对象记录 service */
    private final CadreTaskObjectService cadreTaskObjectService;

    @Override
    public Result<CadreTaskObjectVO> findOne(@ApiParam(value = "村书记模块发布任务对象记录id", required = true) @PathVariable String id) {
        Optional<CadreTaskObject> cadreTaskObjectOptional = cadreTaskObjectService.findOptionalById(id);
        return cadreTaskObjectOptional.map(cadreTaskObject -> Result.of(cadreTaskObject.convert(CadreTaskObjectVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<CadreTaskObjectVO> add(@ApiParam(value = "村书记模块发布任务对象记录 DTO", required = true) @RequestBody @Validated CadreTaskObjectDTO cadreTaskObjectDTO) {
        CadreTaskObject cadreTaskObject = cadreTaskObjectService.save(cadreTaskObjectDTO.convert(CadreTaskObject.class));
        return Result.ofAddSuccess(cadreTaskObject.convert(CadreTaskObjectVO.class));
    }

    @Override
    public Result<CadreTaskObjectVO> update(@ApiParam(value = "村书记模块发布任务对象记录id", required = true) @PathVariable String id,
        @ApiParam(value = "村书记模块发布任务对象记录 DTO", required = true) @RequestBody @Validated CadreTaskObjectDTO cadreTaskObjectDTO) {
        Optional<CadreTaskObject> cadreTaskObjectOptional = cadreTaskObjectService.findOptionalById(id);
        if (!cadreTaskObjectOptional.isPresent()) {
            return Result.ofLost();
        }
        CadreTaskObject cadreTaskObject = cadreTaskObjectOptional.get();
        BeanUtils.copyProperties(cadreTaskObjectDTO, cadreTaskObject);
        cadreTaskObject = cadreTaskObjectService.save(cadreTaskObject);
        return Result.ofUpdateSuccess(cadreTaskObject.convert(CadreTaskObjectVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "村书记模块发布任务对象记录id", required = true) @PathVariable String id) {
        cadreTaskObjectService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<CadreTaskObjectVO>> list(@ApiParam(value = "村书记模块发布任务对象记录查询条件", required = true) @RequestBody CadreTaskObjectSearchable cadreTaskObjectSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<CadreTaskObject> cadreTaskObjectList = cadreTaskObjectService.findAll(cadreTaskObjectSearchable, sort);
        List<CadreTaskObjectVO> cadreTaskObjectVOList = CadreTaskObject.convert(cadreTaskObjectList, CadreTaskObjectVO.class);
        return Result.of(cadreTaskObjectVOList);
    }

    @Override
    public Result<Page<CadreTaskObjectVO>> page(@ApiParam(value = "村书记模块发布任务对象记录查询条件", required = true) @RequestBody CadreTaskObjectSearchable cadreTaskObjectSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<CadreTaskObject> cadreTaskObjectPage = cadreTaskObjectService.findAll(cadreTaskObjectSearchable, pageable);
        Page<CadreTaskObjectVO> cadreTaskObjectVOPage = CadreTaskObject.convert(cadreTaskObjectPage, CadreTaskObjectVO.class);
        return Result.of(cadreTaskObjectVOPage);
    }

    @Override
    public Result<CadreTaskObjectVO> progressNext(@ApiParam(value = "走任务流程", required = true) String taskObjectId, String isSuccess, String auditor, String auditAdvice) {
        CadreTaskObjectVO submit = cadreTaskObjectService.submit(taskObjectId, isSuccess, auditor, auditAdvice);
        return Result.of(submit);
    }

    @GetMapping("/quota/confirm")
    @Transactional
    public Result<Boolean> quotaConfirm(@Nonnull String taskId) {
        Optional<CadreTask> optional = cadreTaskService.findOptionalById(taskId);
        if (!optional.isPresent()) {
            return Result.of(Boolean.FALSE);
        }
        CadreTask cadreTask = optional.get();
        cadreTask.setHasConfirm("1");
        cadreTaskService.save(cadreTask);
        List<CadreTaskObject> allByTaskId = cadreTaskObjectService.findAllByTaskId(taskId);
        for (CadreTaskObject item : allByTaskId) {
            item.setStatus("3");
            cadreTaskObjectService.save(item);
        }
        return Result.of(Boolean.TRUE);
    }

}