package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.ParPictureInfroController;
import com.cloudkeeper.leasing.identity.domain.ParPictureInfro;
import com.cloudkeeper.leasing.identity.dto.parpictureinfro.ParPictureInfroDTO;
import com.cloudkeeper.leasing.identity.dto.parpictureinfro.ParPictureInfroSearchable;
import com.cloudkeeper.leasing.identity.service.ParPictureInfroService;
import com.cloudkeeper.leasing.identity.vo.ParPictureInfroVO;
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
 * 电视截图 controller
 * @author lxw
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParPictureInfroControllerImpl implements ParPictureInfroController {

    /** 电视截图 service */
    private final ParPictureInfroService parPictureInfroService;

    @Override
    public Result<ParPictureInfroVO> findOne(@ApiParam(value = "电视截图id", required = true) @PathVariable String id) {
        Optional<ParPictureInfro> parPictureInfroOptional = parPictureInfroService.findOptionalById(id);
        return parPictureInfroOptional.map(parPictureInfro -> Result.of(parPictureInfro.convert(ParPictureInfroVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<ParPictureInfroVO> add(@ApiParam(value = "电视截图 DTO", required = true) @RequestBody @Validated ParPictureInfroDTO parPictureInfroDTO) {
        ParPictureInfro parPictureInfro = parPictureInfroService.save(parPictureInfroDTO.convert(ParPictureInfro.class));
        return Result.ofAddSuccess(parPictureInfro.convert(ParPictureInfroVO.class));
    }

    @Override
    public Result<ParPictureInfroVO> update(@ApiParam(value = "电视截图id", required = true) @PathVariable String id,
        @ApiParam(value = "电视截图 DTO", required = true) @RequestBody @Validated ParPictureInfroDTO parPictureInfroDTO) {
        Optional<ParPictureInfro> parPictureInfroOptional = parPictureInfroService.findOptionalById(id);
        if (!parPictureInfroOptional.isPresent()) {
            return Result.ofLost();
        }
        ParPictureInfro parPictureInfro = parPictureInfroOptional.get();
        BeanUtils.copyProperties(parPictureInfroDTO, parPictureInfro);
        parPictureInfro = parPictureInfroService.save(parPictureInfro);
        return Result.ofUpdateSuccess(parPictureInfro.convert(ParPictureInfroVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "电视截图id", required = true) @PathVariable String id) {
        parPictureInfroService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<ParPictureInfroVO>> list(@ApiParam(value = "电视截图查询条件", required = true) @RequestBody ParPictureInfroSearchable parPictureInfroSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<ParPictureInfro> parPictureInfroList = parPictureInfroService.findAll(parPictureInfroSearchable, sort);
        List<ParPictureInfroVO> parPictureInfroVOList = ParPictureInfro.convert(parPictureInfroList, ParPictureInfroVO.class);
        return Result.of(parPictureInfroVOList);
    }

    @Override
    public Result<Page<ParPictureInfroVO>> page(@ApiParam(value = "电视截图查询条件", required = true) @RequestBody ParPictureInfroSearchable parPictureInfroSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<ParPictureInfro> parPictureInfroPage = parPictureInfroService.findAll(parPictureInfroSearchable, pageable);
        Page<ParPictureInfroVO> parPictureInfroVOPage = ParPictureInfro.convert(parPictureInfroPage, ParPictureInfroVO.class);
        return Result.of(parPictureInfroVOPage);
    }

}