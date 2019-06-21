package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.ExaExamineController;
import com.cloudkeeper.leasing.identity.domain.ExaExamine;
import com.cloudkeeper.leasing.identity.dto.exaexamine.ExaExamineDTO;
import com.cloudkeeper.leasing.identity.dto.exaexamine.ExaExamineSearchable;
import com.cloudkeeper.leasing.identity.service.ExaExamineService;
import com.cloudkeeper.leasing.identity.vo.ExaExamineVO;
import com.cloudkeeper.leasing.identity.vo.ExamScoreVO;
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
 * 考核审核 controller
 * @author lxw
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExaExamineControllerImpl implements ExaExamineController {

    /** 考核审核 service */
    private final ExaExamineService exaExamineService;

    @Override
    public Result<ExaExamineVO> findOne(@ApiParam(value = "考核审核id", required = true) @PathVariable String id) {
        Optional<ExaExamine> exaExamineOptional = exaExamineService.findOptionalById(id);
        return exaExamineOptional.map(exaExamine -> Result.of(exaExamine.convert(ExaExamineVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<ExaExamineVO> add(@ApiParam(value = "考核审核 DTO", required = true) @RequestBody @Validated ExaExamineDTO exaExamineDTO) {
        ExaExamine exaExamine = exaExamineService.save(exaExamineDTO.convert(ExaExamine.class));
        return Result.ofAddSuccess(exaExamine.convert(ExaExamineVO.class));
    }

    @Override
    public Result<ExaExamineVO> update(@ApiParam(value = "考核审核id", required = true) @PathVariable String id,
        @ApiParam(value = "考核审核 DTO", required = true) @RequestBody @Validated ExaExamineDTO exaExamineDTO) {
        Optional<ExaExamine> exaExamineOptional = exaExamineService.findOptionalById(id);
        if (!exaExamineOptional.isPresent()) {
            return Result.ofLost();
        }
        ExaExamine exaExamine = exaExamineOptional.get();
        BeanUtils.copyProperties(exaExamineDTO, exaExamine);
        exaExamine = exaExamineService.save(exaExamine);
        return Result.ofUpdateSuccess(exaExamine.convert(ExaExamineVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "考核审核id", required = true) @PathVariable String id) {
        exaExamineService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<ExaExamineVO>> list(@ApiParam(value = "考核审核查询条件", required = true) @RequestBody ExaExamineSearchable exaExamineSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<ExaExamine> exaExamineList = exaExamineService.findAll(exaExamineSearchable, sort);
        List<ExaExamineVO> exaExamineVOList = ExaExamine.convert(exaExamineList, ExaExamineVO.class);
        return Result.of(exaExamineVOList);
    }

    @Override
    public Result<Page<ExaExamineVO>> page(@ApiParam(value = "考核审核查询条件", required = true) @RequestBody ExaExamineSearchable exaExamineSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<ExaExamine> exaExaminePage = exaExamineService.findAll(exaExamineSearchable, pageable);
        Page<ExaExamineVO> exaExamineVOPage = ExaExamine.convert(exaExaminePage, ExaExamineVO.class);
        return Result.of(exaExamineVOPage);
    }

    @Override
    public Result<List<ExamScoreVO>> scoreTown(String year){
        List<ExamScoreVO> examScoreVOList = exaExamineService.scoreTown(year);
        return Result.of(examScoreVOList);
    }

    @Override
    public Result<List<ExamScoreVO>> scoreCun(@ApiParam(value = "年份", required = true) @PathVariable String year,@ApiParam(value = "镇名", required = true) @PathVariable String townName){
        List<ExamScoreVO> examScoreVOList = exaExamineService.scoreCun(year,townName);
        return Result.of(examScoreVOList);
    }



}
