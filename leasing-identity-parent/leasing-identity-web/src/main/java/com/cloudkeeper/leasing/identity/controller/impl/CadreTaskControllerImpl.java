package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.CadreTaskController;
import com.cloudkeeper.leasing.identity.domain.*;
import com.cloudkeeper.leasing.identity.dto.cadretask.CadreTaskDTO;
import com.cloudkeeper.leasing.identity.dto.cadretask.CadreTaskSearchable;
import com.cloudkeeper.leasing.identity.dto.cadretask.PromotionCadresDTO;
import com.cloudkeeper.leasing.identity.service.CadreTaskObjectService;
import com.cloudkeeper.leasing.identity.service.CadreTaskService;
import com.cloudkeeper.leasing.identity.service.PromotionCadresService;
import com.cloudkeeper.leasing.identity.service.SysUserService;
import com.cloudkeeper.leasing.identity.vo.CadreTaskObjectVO;
import com.cloudkeeper.leasing.identity.vo.CadreTaskVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import java.time.LocalDate;
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

    /** 任务对象 service */
    private final CadreTaskObjectService cadreTaskObjectService;

    /** 拟晋升人员名单 service */
    private final PromotionCadresService promotionCadresService;

    /** 用户名单 service */
    private final SysUserService sysUserService;

    @Override
    public Result<CadreTaskVO> findOne(@ApiParam(value = "村书记模块任务id", required = true) @PathVariable String id) {
        Optional<CadreTask> cadreTaskOptional = cadreTaskService.findOptionalById(id);
        return cadreTaskOptional.map(cadreTask -> Result.of(cadreTask.convert(CadreTaskVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<CadreTaskVO> add(@ApiParam(value = "村书记模块任务 DTO", required = true) @RequestBody @Validated CadreTaskDTO cadreTaskDTO) {
        if (StringUtils.isEmpty(cadreTaskDTO.getType())) {
            return Result.of(500, "任务类型不能为空");
        }
        CadreTask currentTask = cadreTaskService.getCurrentTaskByType(cadreTaskDTO.getType(), cadreTaskDTO.getTaskYear(), cadreTaskDTO.getTaskQuarter());
        if (currentTask != null) {
            return Result.of(500, "发布失败！当前已存在有效任务！");
        }
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
    public Result<CadreTaskVO> getCurrentTask(@PathVariable @Nonnull String type, String year) {
        if (StringUtils.isEmpty(year)) {
            year = String.valueOf(LocalDate.now().getYear());
        }
        CadreTask cadreTask = cadreTaskService.getCurrentTaskByType(type, year, null);
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
        CadreTask currentTask = cadreTaskService.getCurrentLevelJudgeTask();
        if (currentTask != null) {
            return Result.of(500, "发布失败！当前已存在有效任务！");
        }
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

    @PostMapping("/currentTask/list")
    public Result currentTaskList(@Nonnull @RequestBody List<String> types,@RequestParam("isReview") String isReview,Pageable pageable) {
        String currentPrincipalId = cadreTaskService.getCurrentPrincipalId();
        Optional<SysUser> optionalById = sysUserService.findOptionalById(currentPrincipalId);
        if (!optionalById.isPresent()) {
            return Result.of(500, "当前用户不存在！");
        }
        SysDistrict sysDistrict = optionalById.get().getSysDistrict();
        if (sysDistrict.getDistrictLevel() == 2 || "1".equals(isReview)) {
            // 镇的待执行列表有状态
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CadreTaskObject.class);
            if ("1".equals(isReview)) {
                detachedCriteria.add(Restrictions.eq("status", "1"));
            } else {
                detachedCriteria.add(Restrictions.eq("status", "0"));
                detachedCriteria.add(Restrictions.eq("objectId", sysDistrict.getDistrictId()));
            }
            detachedCriteria.createAlias("cadreTask", "c");
            detachedCriteria.add(Restrictions.in("c.type", types));
            detachedCriteria.add(Restrictions.gt("c.endTime", LocalDate.now()));
            Integer totalCount = cadreTaskObjectService.getTotalCount(detachedCriteria);
            detachedCriteria.addOrder(Order.desc("c.taskYear"));
            detachedCriteria.addOrder(Order.desc("c.endTime"));
            return Result.of(CadreTaskObject.convert(cadreTaskObjectService.findAll(detachedCriteria, pageable, totalCount), CadreTaskObjectVO.class));
        }
        // 市委的正在执行列表
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CadreTask.class);
        if (CollectionUtils.isEmpty(types)) {
            return Result.of(500, "参数不能为空！");
        }
        detachedCriteria.add(Restrictions.in("type", types));
        detachedCriteria.add(Restrictions.gt("endTime", LocalDate.now()));
        if (types.contains("考核指标内容制定")) {
            detachedCriteria.add(Restrictions.ne("hasConfirm", "1"));
        }
        Integer totalCount = cadreTaskObjectService.getTotalCount(detachedCriteria);
        detachedCriteria.addOrder(Order.desc("taskYear"));
        detachedCriteria.addOrder(Order.desc("endTime"));
        return Result.of(CadreTask.convert(cadreTaskService.findAll(detachedCriteria, pageable, totalCount), CadreTaskVO.class));
    }
}
