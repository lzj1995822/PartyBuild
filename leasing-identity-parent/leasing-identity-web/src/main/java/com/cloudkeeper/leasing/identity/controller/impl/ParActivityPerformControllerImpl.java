package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.ParActivityPerformController;
import com.cloudkeeper.leasing.identity.domain.ParActivity;
import com.cloudkeeper.leasing.identity.domain.ParActivityPerform;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.identity.dto.paractivityperform.ParActivityPerformDTO;
import com.cloudkeeper.leasing.identity.dto.paractivityperform.ParActivityPerformSearchable;
import com.cloudkeeper.leasing.identity.service.ParActivityPerformService;
import com.cloudkeeper.leasing.identity.service.ParActivityService;
import com.cloudkeeper.leasing.identity.service.SysDistrictService;
import com.cloudkeeper.leasing.identity.service.SysLogService;
import com.cloudkeeper.leasing.identity.vo.ParActivityPerformVO;
import com.cloudkeeper.leasing.identity.vo.PassPercentVO;
import com.cloudkeeper.leasing.identity.vo.TownDetailVO;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * 任务执行记录 controller
 * @author lxw
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityPerformControllerImpl implements ParActivityPerformController {

    /**
     * 任务执行记录 service
     */
    private final ParActivityPerformService parActivityPerformService;

    private final SysLogService sysLogService;

    private final ParActivityService parActivityService;

    private final SysDistrictService sysDistrictService;

    @Override
    public Result<ParActivityPerformVO> findOne(@ApiParam(value = "任务执行记录id", required = true) @PathVariable String id) {
        Optional<ParActivityPerform> parActivityPerformOptional = parActivityPerformService.findOptionalById(id);
        return parActivityPerformOptional.map(parActivityPerform -> Result.of(parActivityPerform.convert(ParActivityPerformVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<ParActivityPerformVO> add(@ApiParam(value = "任务执行记录 DTO", required = true) @RequestBody @Validated ParActivityPerformDTO parActivityPerformDTO) {
        ParActivityPerform parActivityPerform = parActivityPerformService.save(parActivityPerformDTO.convert(ParActivityPerform.class));
        return Result.ofAddSuccess(parActivityPerform.convert(ParActivityPerformVO.class));
    }

    @Override
    public Result<ParActivityPerformVO> update(@ApiParam(value = "任务执行记录id", required = true) @PathVariable String id,
                                               @ApiParam(value = "任务执行记录 DTO", required = true) @RequestBody @Validated ParActivityPerformDTO parActivityPerformDTO) {
        Optional<ParActivityPerform> parActivityPerformOptional = parActivityPerformService.findOptionalById(id);
        if (!parActivityPerformOptional.isPresent()) {
            return Result.ofLost();
        }
        ParActivityPerform parActivityPerform = parActivityPerformOptional.get();
        BeanUtils.copyProperties(parActivityPerformDTO, parActivityPerform);
        parActivityPerform = parActivityPerformService.save(parActivityPerform);
        return Result.ofUpdateSuccess(parActivityPerform.convert(ParActivityPerformVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "任务执行记录id", required = true) @PathVariable String id) {
        parActivityPerformService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<ParActivityPerformVO>> list(@ApiParam(value = "任务执行记录查询条件", required = true) @RequestBody ParActivityPerformSearchable parActivityPerformSearchable,
                                                   @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<ParActivityPerform> parActivityPerformList = parActivityPerformService.findAll(parActivityPerformSearchable, sort);
        List<ParActivityPerformVO> parActivityPerformVOList = ParActivityPerform.convert(parActivityPerformList, ParActivityPerformVO.class);
        return Result.of(parActivityPerformVOList);
    }

    @Override
    public Result<Page<ParActivityPerformVO>> page(@ApiParam(value = "任务执行记录查询条件", required = true) @RequestBody ParActivityPerformSearchable parActivityPerformSearchable,
                                                   @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<ParActivityPerform> parActivityPerformPage = parActivityPerformService.findAll(parActivityPerformSearchable, pageable);
        Page<ParActivityPerformVO> parActivityPerformVOPage = ParActivityPerform.convert(parActivityPerformPage, ParActivityPerformVO.class);
        return Result.of(parActivityPerformVOPage);
    }

    //    @Override
//    public Result<Page<ParActivityPerformVO>>listAll(@ApiParam(value = "活动id", required = true) @PathVariable String activityId,@ApiParam(value = "组织id", required = true) @PathVariable String orgId,
//                                                     @ApiParam(value = "分页参数", required = true) Pageable pageable){
//        Page<ParActivityPerform> parActivityPerformPage = parActivityPerformService.listAll(activityId, orgId,pageable);
//        Page<ParActivityPerformVO> parActivityPerformVOPage = ParActivityPerform.convert(parActivityPerformPage, ParActivityPerformVO.class);
//        return  Result.of(parActivityPerformVOPage);
//    }
//
    @Override
    public Result<List<PassPercentVO>> percent(@ApiParam(value = "活动ID", required = true) @PathVariable String activityId) {
        List<PassPercentVO> passPercentVOList = parActivityPerformService.percent(activityId);
        return Result.of(passPercentVOList);
    }

    @Override
    public Result<List<TownDetailVO>> townDetail(@ApiParam(value = "活动ID", required = true) @PathVariable String activityId,
    @ApiParam(value = "活动ID", required = true) @PathVariable String town) {
        List<TownDetailVO> townDetailVOList = parActivityPerformService.townDetail(activityId,town);
        return Result.of(townDetailVOList);
    }
    @Override
    public Result<ParActivityPerformVO> check(@ApiParam(value = "审核记录 DTO", required = true) @RequestBody @Validated ParActivityPerformDTO parActivityPerformDTO){
        ParActivityPerformVO parActivityPerformVO = parActivityPerformService.check(parActivityPerformDTO);
        ParActivity parActivity = parActivityService.findById(parActivityPerformVO.getActivityID());
        SysDistrict sysDistrict = sysDistrictService.findById(parActivityPerformVO.getOrganizationId());
        String action;
        if(parActivityPerformVO.getStatus().equals("2")){
            action = "审核通过";
        }else{
            action = "审核驳回";
        }
        String  msg = parActivityPerformService.actionLog(action +'"'+sysDistrict.getDistrictName()+'"'+"执行的",parActivity.getTaskType(), parActivity.getTitle());
        sysLogService.pushLog(this.getClass().getName(),msg,parActivityPerformService.getTableName(),parActivityPerformVO.getId());
        return Result.ofUpdateSuccess(parActivityPerformVO);
    }

    @Override
    public Result<Integer> countAll(@RequestBody ParActivityPerformSearchable parActivityPerformSearchable) {
        Integer integer = parActivityPerformService.countAll(parActivityPerformSearchable.getDistrictId(), String.valueOf(LocalDate.now().getYear()));
        return Result.of(integer);
    }

}
