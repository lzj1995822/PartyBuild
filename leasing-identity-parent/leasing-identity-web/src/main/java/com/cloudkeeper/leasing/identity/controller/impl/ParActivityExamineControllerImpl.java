package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.ParActivityExamineController;
import com.cloudkeeper.leasing.identity.domain.ParActivityExamine;
import com.cloudkeeper.leasing.identity.dto.paractivityexamine.ParActivityExamineDTO;
import com.cloudkeeper.leasing.identity.dto.paractivityexamine.ParActivityExamineSearchable;
import com.cloudkeeper.leasing.identity.service.ParActivityExamineService;
import com.cloudkeeper.leasing.identity.vo.ParActivityExamineVO;
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
 * 活动考核记录 controller
 * @author lxw
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityExamineControllerImpl implements ParActivityExamineController {

    /** 活动考核记录 service */
    private final ParActivityExamineService parActivityExamineService;

    @Override
    public Result<ParActivityExamineVO> findOne(@ApiParam(value = "活动考核记录id", required = true) @PathVariable String id) {
        Optional<ParActivityExamine> parActivityExamineOptional = parActivityExamineService.findOptionalById(id);
        return parActivityExamineOptional.map(parActivityExamine -> Result.of(parActivityExamine.convert(ParActivityExamineVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<ParActivityExamineVO> add(@ApiParam(value = "活动考核记录 DTO", required = true) @RequestBody @Validated ParActivityExamineDTO parActivityExamineDTO) {
        ParActivityExamine parActivityExamine = parActivityExamineService.save(parActivityExamineDTO.convert(ParActivityExamine.class));
        return Result.ofAddSuccess(parActivityExamine.convert(ParActivityExamineVO.class));
    }

    @Override
    public Result<ParActivityExamineVO> update(@ApiParam(value = "活动考核记录id", required = true) @PathVariable String id,
        @ApiParam(value = "活动考核记录 DTO", required = true) @RequestBody @Validated ParActivityExamineDTO parActivityExamineDTO) {
        Optional<ParActivityExamine> parActivityExamineOptional = parActivityExamineService.findOptionalById(id);
        if (!parActivityExamineOptional.isPresent()) {
            return Result.ofLost();
        }
        ParActivityExamine parActivityExamine = parActivityExamineOptional.get();
        BeanUtils.copyProperties(parActivityExamineDTO, parActivityExamine);
        parActivityExamine = parActivityExamineService.save(parActivityExamine);
        return Result.ofUpdateSuccess(parActivityExamine.convert(ParActivityExamineVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "活动考核记录id", required = true) @PathVariable String id) {
        parActivityExamineService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<ParActivityExamineVO>> list(@ApiParam(value = "活动考核记录查询条件", required = true) @RequestBody ParActivityExamineSearchable parActivityExamineSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<ParActivityExamine> parActivityExamineList = parActivityExamineService.findAll(parActivityExamineSearchable, sort);
        List<ParActivityExamineVO> parActivityExamineVOList = ParActivityExamine.convert(parActivityExamineList, ParActivityExamineVO.class);
        return Result.of(parActivityExamineVOList);
    }

    @Override
    public Result<Page<ParActivityExamineVO>> page(@ApiParam(value = "活动考核记录查询条件", required = true) @RequestBody ParActivityExamineSearchable parActivityExamineSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<ParActivityExamine> parActivityExaminePage = parActivityExamineService.findAll(parActivityExamineSearchable, pageable);
        Page<ParActivityExamineVO> parActivityExamineVOPage = ParActivityExamine.convert(parActivityExaminePage, ParActivityExamineVO.class);
        return Result.of(parActivityExamineVOPage);
    }

}