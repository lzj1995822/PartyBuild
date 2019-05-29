package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.VolunteerGroupController;
import com.cloudkeeper.leasing.identity.domain.VolunteerGroup;
import com.cloudkeeper.leasing.identity.dto.volunteergroup.VolunteerGroupDTO;
import com.cloudkeeper.leasing.identity.dto.volunteergroup.VolunteerGroupSearchable;
import com.cloudkeeper.leasing.identity.service.VolunteerGroupService;
import com.cloudkeeper.leasing.identity.vo.VolunteerGroupVO;
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
 * 志愿者服务队伍 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VolunteerGroupControllerImpl implements VolunteerGroupController {

    /** 志愿者服务队伍 service */
    private final VolunteerGroupService volunteerGroupService;

    @Override
    public Result<VolunteerGroupVO> findOne(@ApiParam(value = "志愿者服务队伍id", required = true) @PathVariable String id) {
        Optional<VolunteerGroup> volunteerGroupOptional = volunteerGroupService.findOptionalById(id);
        return volunteerGroupOptional.map(volunteerGroup -> Result.of(volunteerGroup.convert(VolunteerGroupVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<VolunteerGroupVO> add(@ApiParam(value = "志愿者服务队伍 DTO", required = true) @RequestBody @Validated VolunteerGroupDTO volunteerGroupDTO) {
        VolunteerGroup volunteerGroup = volunteerGroupService.save(volunteerGroupDTO.convert(VolunteerGroup.class));
        return Result.ofAddSuccess(volunteerGroup.convert(VolunteerGroupVO.class));
    }

    @Override
    public Result<VolunteerGroupVO> update(@ApiParam(value = "志愿者服务队伍id", required = true) @PathVariable String id,
        @ApiParam(value = "志愿者服务队伍 DTO", required = true) @RequestBody @Validated VolunteerGroupDTO volunteerGroupDTO) {
        Optional<VolunteerGroup> volunteerGroupOptional = volunteerGroupService.findOptionalById(id);
        if (!volunteerGroupOptional.isPresent()) {
            return Result.ofLost();
        }
        VolunteerGroup volunteerGroup = volunteerGroupOptional.get();
        BeanUtils.copyProperties(volunteerGroupDTO, volunteerGroup);
        volunteerGroup = volunteerGroupService.save(volunteerGroup);
        return Result.ofUpdateSuccess(volunteerGroup.convert(VolunteerGroupVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "志愿者服务队伍id", required = true) @PathVariable String id) {
        volunteerGroupService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<VolunteerGroupVO>> list(@ApiParam(value = "志愿者服务队伍查询条件", required = true) @RequestBody VolunteerGroupSearchable volunteerGroupSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<VolunteerGroup> volunteerGroupList = volunteerGroupService.findAll(volunteerGroupSearchable, sort);
        List<VolunteerGroupVO> volunteerGroupVOList = VolunteerGroup.convert(volunteerGroupList, VolunteerGroupVO.class);
        return Result.of(volunteerGroupVOList);
    }

    @Override
    public Result<Page<VolunteerGroupVO>> page(@ApiParam(value = "志愿者服务队伍查询条件", required = true) @RequestBody VolunteerGroupSearchable volunteerGroupSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<VolunteerGroup> volunteerGroupPage = volunteerGroupService.findAll(volunteerGroupSearchable, pageable);
        Page<VolunteerGroupVO> volunteerGroupVOPage = VolunteerGroup.convert(volunteerGroupPage, VolunteerGroupVO.class);
        return Result.of(volunteerGroupVOPage);
    }

}