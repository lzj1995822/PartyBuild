package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.VolunteerController;
import com.cloudkeeper.leasing.identity.domain.Volunteer;
import com.cloudkeeper.leasing.identity.dto.volunteer.VolunteerDTO;
import com.cloudkeeper.leasing.identity.dto.volunteer.VolunteerSearchable;
import com.cloudkeeper.leasing.identity.service.SysLogService;
import com.cloudkeeper.leasing.identity.service.VolunteerService;
import com.cloudkeeper.leasing.identity.vo.VolunteerVO;
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
 * 志愿者 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VolunteerControllerImpl implements VolunteerController {

    /** 志愿者 service */
    private final VolunteerService volunteerService;

    private final SysLogService sysLogService;

    @Override
    public Result<VolunteerVO> findOne(@ApiParam(value = "志愿者id", required = true) @PathVariable String id) {
        Optional<Volunteer> volunteerOptional = volunteerService.findOptionalById(id);
        return volunteerOptional.map(volunteer -> Result.of(volunteer.convert(VolunteerVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<VolunteerVO> add(@ApiParam(value = "志愿者 DTO", required = true) @RequestBody @Validated VolunteerDTO volunteerDTO) {
        Volunteer volunteer = volunteerService.save(volunteerDTO.convert(Volunteer.class));
        String  msg= volunteerService.actionLog("新增","[党员志愿者信息]", volunteer.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,volunteerService.getTableName(),volunteer.getId());
        return Result.ofAddSuccess(volunteer.convert(VolunteerVO.class));
    }

    @Override
    public Result<VolunteerVO> update(@ApiParam(value = "志愿者id", required = true) @PathVariable String id,
        @ApiParam(value = "志愿者 DTO", required = true) @RequestBody @Validated VolunteerDTO volunteerDTO) {
        Optional<Volunteer> volunteerOptional = volunteerService.findOptionalById(id);
        if (!volunteerOptional.isPresent()) {
            return Result.ofLost();
        }
        Volunteer volunteer = volunteerOptional.get();
        BeanUtils.copyProperties(volunteerDTO, volunteer);
        volunteer = volunteerService.save(volunteer);
        String  msg= volunteerService.actionLog("修改","[党员志愿者信息]", volunteer.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,volunteerService.getTableName(),volunteer.getId());
        return Result.ofUpdateSuccess(volunteer.convert(VolunteerVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "志愿者id", required = true) @PathVariable String id) {
        Volunteer volunteer = volunteerService.findById(id);
        volunteerService.deleteById(id);
        String  msg= volunteerService.actionLog("删除","[党员志愿者信息]", volunteer.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,volunteerService.getTableName(),volunteer.getId());
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<VolunteerVO>> list(@ApiParam(value = "志愿者查询条件", required = true) @RequestBody VolunteerSearchable volunteerSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<Volunteer> volunteerList = volunteerService.findAll(volunteerSearchable, sort);
        List<VolunteerVO> volunteerVOList = Volunteer.convert(volunteerList, VolunteerVO.class);
        return Result.of(volunteerVOList);
    }

    @Override
    public Result<Page<VolunteerVO>> page(@ApiParam(value = "志愿者查询条件", required = true) @RequestBody VolunteerSearchable volunteerSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<Volunteer> volunteerPage = volunteerService.findAll(volunteerSearchable, pageable);
        Page<VolunteerVO> volunteerVOPage = Volunteer.convert(volunteerPage, VolunteerVO.class);
        return Result.of(volunteerVOPage);
    }

}