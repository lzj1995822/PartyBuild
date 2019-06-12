package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.ParActivityPictureController;
import com.cloudkeeper.leasing.identity.domain.ParActivityPicture;
import com.cloudkeeper.leasing.identity.dto.paractivitypicture.ParActivityPictureDTO;
import com.cloudkeeper.leasing.identity.dto.paractivitypicture.ParActivityPictureSearchable;
import com.cloudkeeper.leasing.identity.service.ParActivityPictureService;
import com.cloudkeeper.leasing.identity.vo.ParActivityPictureVO;
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
 * 手机截图 controller
 * @author lxw
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParActivityPictureControllerImpl implements ParActivityPictureController {

    /** 手机截图 service */
    private final ParActivityPictureService parActivityPictureService;

    @Override
    public Result<ParActivityPictureVO> findOne(@ApiParam(value = "手机截图id", required = true) @PathVariable String id) {
        Optional<ParActivityPicture> parActivityPictureOptional = parActivityPictureService.findOptionalById(id);
        return parActivityPictureOptional.map(parActivityPicture -> Result.of(parActivityPicture.convert(ParActivityPictureVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<ParActivityPictureVO> add(@ApiParam(value = "手机截图 DTO", required = true) @RequestBody @Validated ParActivityPictureDTO parActivityPictureDTO) {
        ParActivityPicture parActivityPicture = parActivityPictureService.save(parActivityPictureDTO.convert(ParActivityPicture.class));
        return Result.ofAddSuccess(parActivityPicture.convert(ParActivityPictureVO.class));
    }

    @Override
    public Result<ParActivityPictureVO> update(@ApiParam(value = "手机截图id", required = true) @PathVariable String id,
        @ApiParam(value = "手机截图 DTO", required = true) @RequestBody @Validated ParActivityPictureDTO parActivityPictureDTO) {
        Optional<ParActivityPicture> parActivityPictureOptional = parActivityPictureService.findOptionalById(id);
        if (!parActivityPictureOptional.isPresent()) {
            return Result.ofLost();
        }
        ParActivityPicture parActivityPicture = parActivityPictureOptional.get();
        BeanUtils.copyProperties(parActivityPictureDTO, parActivityPicture);
        parActivityPicture = parActivityPictureService.save(parActivityPicture);
        return Result.ofUpdateSuccess(parActivityPicture.convert(ParActivityPictureVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "手机截图id", required = true) @PathVariable String id) {
        parActivityPictureService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<ParActivityPictureVO>> list(@ApiParam(value = "手机截图查询条件", required = true) @RequestBody ParActivityPictureSearchable parActivityPictureSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<ParActivityPicture> parActivityPictureList = parActivityPictureService.findAll(parActivityPictureSearchable, sort);
        List<ParActivityPictureVO> parActivityPictureVOList = ParActivityPicture.convert(parActivityPictureList, ParActivityPictureVO.class);
        return Result.of(parActivityPictureVOList);
    }

    @Override
    public Result<Page<ParActivityPictureVO>> page(@ApiParam(value = "手机截图查询条件", required = true) @RequestBody ParActivityPictureSearchable parActivityPictureSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<ParActivityPicture> parActivityPicturePage = parActivityPictureService.findAll(parActivityPictureSearchable, pageable);
        Page<ParActivityPictureVO> parActivityPictureVOPage = ParActivityPicture.convert(parActivityPicturePage, ParActivityPictureVO.class);
        return Result.of(parActivityPictureVOPage);
    }

}