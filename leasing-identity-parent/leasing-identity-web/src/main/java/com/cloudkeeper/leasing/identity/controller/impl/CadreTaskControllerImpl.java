package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.CadreTaskController;
import com.cloudkeeper.leasing.identity.domain.CadreTask;
import com.cloudkeeper.leasing.identity.domain.PromotionCadres;
import com.cloudkeeper.leasing.identity.dto.cadretask.CadreTaskDTO;
import com.cloudkeeper.leasing.identity.dto.cadretask.CadreTaskSearchable;
import com.cloudkeeper.leasing.identity.dto.cadretask.PromotionCadresDTO;
import com.cloudkeeper.leasing.identity.service.CadreTaskService;
import com.cloudkeeper.leasing.identity.service.PromotionCadresService;
import com.cloudkeeper.leasing.identity.vo.CadreTaskObjectVO;
import com.cloudkeeper.leasing.identity.vo.CadreTaskVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 村书记模块任务 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadreTaskControllerImpl implements CadreTaskController {

    /** 村书记模块任务 service */
    private final CadreTaskService cadreTaskService;

    /** 拟晋升人员名单 service */
    private final PromotionCadresService promotionCadresService;

    @Override
    public Result<CadreTaskVO> findOne(@ApiParam(value = "村书记模块任务id", required = true) @PathVariable String id) {
        Optional<CadreTask> cadreTaskOptional = cadreTaskService.findOptionalById(id);
        return cadreTaskOptional.map(cadreTask -> Result.of(cadreTask.convert(CadreTaskVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<CadreTaskVO> add(@ApiParam(value = "村书记模块任务 DTO", required = true) @RequestBody @Validated CadreTaskDTO cadreTaskDTO) {
        CadreTask cadreTask = cadreTaskService.save(cadreTaskDTO);
        return Result.ofAddSuccess(cadreTask.convert(CadreTaskVO.class));
    }

    @Override
    public Result<CadreTaskVO> update(@ApiParam(value = "村书记模块任务id", required = true) @PathVariable String id,
        @ApiParam(value = "村书记模块任务 DTO", required = true) @RequestBody @Validated CadreTaskDTO cadreTaskDTO) {
        Optional<CadreTask> cadreTaskOptional = cadreTaskService.findOptionalById(id);
        if (!cadreTaskOptional.isPresent()) {
            return Result.ofLost();
        }
        CadreTask cadreTask = cadreTaskOptional.get();
        BeanUtils.copyProperties(cadreTaskDTO, cadreTask);
        cadreTask = cadreTaskService.save(cadreTask);
        return Result.ofUpdateSuccess(cadreTask.convert(CadreTaskVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "村书记模块任务id", required = true) @PathVariable String id) {
        cadreTaskService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<CadreTaskVO>> list(@ApiParam(value = "村书记模块任务查询条件", required = true) @RequestBody CadreTaskSearchable cadreTaskSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<CadreTask> cadreTaskList = cadreTaskService.findAll(cadreTaskSearchable, sort);
        List<CadreTaskVO> cadreTaskVOList = CadreTask.convert(cadreTaskList, CadreTaskVO.class);
        return Result.of(cadreTaskVOList);
    }

    @Override
    public Result<Page<CadreTaskVO>> page(@ApiParam(value = "村书记模块任务查询条件", required = true) @RequestBody CadreTaskSearchable cadreTaskSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<CadreTask> cadreTaskPage = cadreTaskService.findAll(cadreTaskSearchable, pageable);
        Page<CadreTaskVO> cadreTaskVOPage = CadreTask.convert(cadreTaskPage, CadreTaskVO.class);
        return Result.of(cadreTaskVOPage);
    }

    @Override
    public Result<CadreTaskVO> getCurrentTask(@PathVariable @Nonnull String type) {
        CadreTask cadreTask = cadreTaskService.getCurrentTaskByType(type);
        if (cadreTask == null) {
            return Result.of(null);
        }
        return Result.of(cadreTask.convert(CadreTaskVO.class));
    }

    @Override
    public Result<Map<String, List>> activitiesCompletion(String year, String objectType,String taskType) {
        return Result.of(cadreTaskService.activitiesCompletion(year,objectType,taskType));
    }

    /**
     * 更新
     * @param  taskId
     * @return 村书记模块任务 VO
     */
    @ApiOperation(value = "更新", notes = "更新", position = 3)
    @GetMapping("/{taskId}taskId")
    public Result<List<CadreTaskObjectVO>> getDetailByTaskId(@PathVariable @Nonnull String taskId) {
        List<CadreTaskObjectVO> detailByTaskId = cadreTaskService.getDetailByTaskId(taskId);
        if (detailByTaskId == null) {
            return Result.of(null);
        }
        return Result.of(detailByTaskId);
    }

    @ApiOperation(value = "发布职级评定任务", notes = "发布职级评定任务", position = 3)
    @PostMapping("/publishJudgeTask")
    @Transactional
    public Result<CadreTaskVO> publishJudgeTask(@RequestBody CadreTaskDTO cadreTaskDTO) {
        CadreTask save = cadreTaskService.save(cadreTaskDTO);
        List<PromotionCadresDTO> promotionCadres = cadreTaskDTO.getPromotionCadres();
        for(PromotionCadresDTO item: promotionCadres) {
            PromotionCadres convert = item.convert(PromotionCadres.class);
            convert.setTaskId(save.getId());
            convert.setStatus("0");
            promotionCadresService.save(convert);
        }
        return Result.of(save.convert(CadreTaskVO.class));
    }
}
