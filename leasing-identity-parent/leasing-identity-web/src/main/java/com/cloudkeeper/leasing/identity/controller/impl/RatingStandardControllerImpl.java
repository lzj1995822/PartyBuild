package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.RatingStandardController;
import com.cloudkeeper.leasing.identity.domain.RatingStandard;
import com.cloudkeeper.leasing.identity.dto.ratingstandard.RatingStandardDTO;
import com.cloudkeeper.leasing.identity.dto.ratingstandard.RatingStandardSearchable;
import com.cloudkeeper.leasing.identity.service.RatingStandardService;
import com.cloudkeeper.leasing.identity.vo.RatingStandardVO;
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
 * 村书记评级标准 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RatingStandardControllerImpl implements RatingStandardController {

    /** 村书记评级标准 service */
    private final RatingStandardService ratingStandardService;

    @Override
    public Result<RatingStandardVO> findOne(@ApiParam(value = "村书记评级标准id", required = true) @PathVariable String id) {
        Optional<RatingStandard> ratingStandardOptional = ratingStandardService.findOptionalById(id);
        return ratingStandardOptional.map(ratingStandard -> Result.of(ratingStandard.convert(RatingStandardVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<RatingStandardVO> add(@ApiParam(value = "村书记评级标准 DTO", required = true) @RequestBody @Validated RatingStandardDTO ratingStandardDTO) {
        RatingStandard ratingStandard = ratingStandardService.save(ratingStandardDTO.convert(RatingStandard.class));
        return Result.ofAddSuccess(ratingStandard.convert(RatingStandardVO.class));
    }

    @Override
    public Result<RatingStandardVO> update(@ApiParam(value = "村书记评级标准id", required = true) @PathVariable String id,
        @ApiParam(value = "村书记评级标准 DTO", required = true) @RequestBody @Validated RatingStandardDTO ratingStandardDTO) {
        Optional<RatingStandard> ratingStandardOptional = ratingStandardService.findOptionalById(id);
        if (!ratingStandardOptional.isPresent()) {
            return Result.ofLost();
        }
        RatingStandard ratingStandard = ratingStandardOptional.get();
        BeanUtils.copyProperties(ratingStandardDTO, ratingStandard);
        ratingStandard = ratingStandardService.save(ratingStandard);
        return Result.ofUpdateSuccess(ratingStandard.convert(RatingStandardVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "村书记评级标准id", required = true) @PathVariable String id) {
        ratingStandardService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<RatingStandardVO>> list(@ApiParam(value = "村书记评级标准查询条件", required = true) @RequestBody RatingStandardSearchable ratingStandardSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<RatingStandard> ratingStandardList = ratingStandardService.findAll(ratingStandardSearchable, sort);
        List<RatingStandardVO> ratingStandardVOList = RatingStandard.convert(ratingStandardList, RatingStandardVO.class);
        return Result.of(ratingStandardVOList);
    }

    @Override
    public Result<Page<RatingStandardVO>> page(@ApiParam(value = "村书记评级标准查询条件", required = true) @RequestBody RatingStandardSearchable ratingStandardSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<RatingStandard> ratingStandardPage = ratingStandardService.findAll(ratingStandardSearchable, pageable);
        Page<RatingStandardVO> ratingStandardVOPage = RatingStandard.convert(ratingStandardPage, RatingStandardVO.class);
        return Result.of(ratingStandardVOPage);
    }

}