package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.DetectionIndexController;
import com.cloudkeeper.leasing.identity.domain.DetectionIndex;
import com.cloudkeeper.leasing.identity.dto.detectionindex.DetectionIndexDTO;
import com.cloudkeeper.leasing.identity.dto.detectionindex.DetectionIndexSearchable;
import com.cloudkeeper.leasing.identity.service.DetectionIndexService;
import com.cloudkeeper.leasing.identity.vo.DetectionIndexVO;
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
 * 监测指标 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DetectionIndexControllerImpl implements DetectionIndexController {

    /** 监测指标 service */
    private final DetectionIndexService detectionIndexService;

    @Override
    public Result<DetectionIndexVO> findOne(@ApiParam(value = "监测指标id", required = true) @PathVariable String id) {
        Optional<DetectionIndex> detectionIndexOptional = detectionIndexService.findOptionalById(id);
        return detectionIndexOptional.map(detectionIndex -> Result.of(detectionIndex.convert(DetectionIndexVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<DetectionIndexVO> add(@ApiParam(value = "监测指标 DTO", required = true) @RequestBody @Validated DetectionIndexDTO detectionIndexDTO) {
        DetectionIndex detectionIndex = detectionIndexService.save(detectionIndexDTO.convert(DetectionIndex.class));
        return Result.ofAddSuccess(detectionIndex.convert(DetectionIndexVO.class));
    }

    @Override
    public Result<DetectionIndexVO> update(@ApiParam(value = "监测指标id", required = true) @PathVariable String id,
        @ApiParam(value = "监测指标 DTO", required = true) @RequestBody @Validated DetectionIndexDTO detectionIndexDTO) {
        Optional<DetectionIndex> detectionIndexOptional = detectionIndexService.findOptionalById(id);
        if (!detectionIndexOptional.isPresent()) {
            return Result.ofLost();
        }
        DetectionIndex detectionIndex = detectionIndexOptional.get();
        BeanUtils.copyProperties(detectionIndexDTO, detectionIndex);
        detectionIndex = detectionIndexService.save(detectionIndex);
        return Result.ofUpdateSuccess(detectionIndex.convert(DetectionIndexVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "监测指标id", required = true) @PathVariable String id) {
        detectionIndexService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<DetectionIndexVO>> list(@ApiParam(value = "监测指标查询条件", required = true) @RequestBody DetectionIndexSearchable detectionIndexSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<DetectionIndex> detectionIndexList = detectionIndexService.findAll(detectionIndexSearchable, sort);
        List<DetectionIndexVO> detectionIndexVOList = DetectionIndex.convert(detectionIndexList, DetectionIndexVO.class);
        return Result.of(detectionIndexVOList);
    }

    @Override
    public Result<Page<DetectionIndexVO>> page(@ApiParam(value = "监测指标查询条件", required = true) @RequestBody DetectionIndexSearchable detectionIndexSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<DetectionIndex> detectionIndexPage = detectionIndexService.findAll(detectionIndexSearchable, pageable);
        Page<DetectionIndexVO> detectionIndexVOPage = DetectionIndex.convert(detectionIndexPage, DetectionIndexVO.class);
        return Result.of(detectionIndexVOPage);
    }

}