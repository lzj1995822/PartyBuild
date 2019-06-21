package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.ExaScoreController;
import com.cloudkeeper.leasing.identity.domain.ExaScore;
import com.cloudkeeper.leasing.identity.dto.exascore.ExaScoreDTO;
import com.cloudkeeper.leasing.identity.dto.exascore.ExaScoreSearchable;
import com.cloudkeeper.leasing.identity.service.ExaScoreService;
import com.cloudkeeper.leasing.identity.vo.ExaScoreVO;
import com.cloudkeeper.leasing.identity.vo.ExamScorePercentVO;
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
 * 考核积分 controller
 * @author lxw
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExaScoreControllerImpl implements ExaScoreController {

    /** 考核积分 service */
    private final ExaScoreService exaScoreService;

    @Override
    public Result<ExaScoreVO> findOne(@ApiParam(value = "考核积分id", required = true) @PathVariable String id) {
        Optional<ExaScore> exaScoreOptional = exaScoreService.findOptionalById(id);
        return exaScoreOptional.map(exaScore -> Result.of(exaScore.convert(ExaScoreVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<ExaScoreVO> add(@ApiParam(value = "考核积分 DTO", required = true) @RequestBody @Validated ExaScoreDTO exaScoreDTO) {
        ExaScore exaScore = exaScoreService.save(exaScoreDTO.convert(ExaScore.class));
        return Result.ofAddSuccess(exaScore.convert(ExaScoreVO.class));
    }

    @Override
    public Result<ExaScoreVO> update(@ApiParam(value = "考核积分id", required = true) @PathVariable String id,
        @ApiParam(value = "考核积分 DTO", required = true) @RequestBody @Validated ExaScoreDTO exaScoreDTO) {
        Optional<ExaScore> exaScoreOptional = exaScoreService.findOptionalById(id);
        if (!exaScoreOptional.isPresent()) {
            return Result.ofLost();
        }
        ExaScore exaScore = exaScoreOptional.get();
        BeanUtils.copyProperties(exaScoreDTO, exaScore);
        exaScore = exaScoreService.save(exaScore);
        return Result.ofUpdateSuccess(exaScore.convert(ExaScoreVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "考核积分id", required = true) @PathVariable String id) {
        exaScoreService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<ExaScoreVO>> list(@ApiParam(value = "考核积分查询条件", required = true) @RequestBody ExaScoreSearchable exaScoreSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<ExaScore> exaScoreList = exaScoreService.findAll(exaScoreSearchable, sort);
        List<ExaScoreVO> exaScoreVOList = ExaScore.convert(exaScoreList, ExaScoreVO.class);
        return Result.of(exaScoreVOList);
    }

    @Override
    public Result<Page<ExaScoreVO>> page(@ApiParam(value = "考核积分查询条件", required = true) @RequestBody ExaScoreSearchable exaScoreSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<ExaScore> exaScorePage = exaScoreService.findAll(exaScoreSearchable, pageable);
        Page<ExaScoreVO> exaScoreVOPage = ExaScore.convert(exaScorePage, ExaScoreVO.class);
        return Result.of(exaScoreVOPage);
    }
    @Override
    public Result<List<ExamScoreVO>> scoreCun(@ApiParam(value = "分页参数", required = true) Pageable pageable, String sort,String year){
        List<ExamScoreVO> examScoreVOList = exaScoreService.scoreCun(pageable,sort,year);
        return Result.of(examScoreVOList);
    }
    @Override
    public Result<List<ExamScorePercentVO>> percentTown(@ApiParam(value = "年份", required = true)String year){
        List<ExamScorePercentVO> list = exaScoreService.percentTown(year);
        return Result.of(list);
    }
    @Override
    public Result<List<ExamScorePercentVO>> percentCun(@ApiParam(value = "年份", required = true) @PathVariable String year,@ApiParam(value = "镇名", required = true) @PathVariable String townName){
        List<ExamScorePercentVO> list = exaScoreService.percentCun(year,townName);
        return Result.of(list);
    }
}
