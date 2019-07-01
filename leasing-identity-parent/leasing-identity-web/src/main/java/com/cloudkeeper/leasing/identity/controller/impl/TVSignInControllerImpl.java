package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.TVSignInController;
import com.cloudkeeper.leasing.identity.domain.TVSignIn;
import com.cloudkeeper.leasing.identity.dto.tvsignin.TVSignInDTO;
import com.cloudkeeper.leasing.identity.dto.tvsignin.TVSignInSearchable;
import com.cloudkeeper.leasing.identity.service.TVSignInService;
import com.cloudkeeper.leasing.identity.vo.TVSignInVO;
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
 * 远教视频签到记录 controller
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TVSignInControllerImpl implements TVSignInController {

    /** 远教视频签到记录 service */
    private final TVSignInService tVSignInService;

    @Override
    public Result<TVSignInVO> findOne(@ApiParam(value = "远教视频签到记录id", required = true) @PathVariable String id) {
        Optional<TVSignIn> tVSignInOptional = tVSignInService.findOptionalById(id);
        return tVSignInOptional.map(tVSignIn -> Result.of(tVSignIn.convert(TVSignInVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<TVSignInVO> add(@ApiParam(value = "远教视频签到记录 DTO", required = true) @RequestBody @Validated TVSignInDTO tVSignInDTO) {
        TVSignIn tVSignIn = tVSignInService.save(tVSignInDTO.convert(TVSignIn.class));
        return Result.ofAddSuccess(tVSignIn.convert(TVSignInVO.class));
    }

    @Override
    public Result<TVSignInVO> update(@ApiParam(value = "远教视频签到记录id", required = true) @PathVariable String id,
        @ApiParam(value = "远教视频签到记录 DTO", required = true) @RequestBody @Validated TVSignInDTO tVSignInDTO) {
        Optional<TVSignIn> tVSignInOptional = tVSignInService.findOptionalById(id);
        if (!tVSignInOptional.isPresent()) {
            return Result.ofLost();
        }
        TVSignIn tVSignIn = tVSignInOptional.get();
        BeanUtils.copyProperties(tVSignInDTO, tVSignIn);
        tVSignIn = tVSignInService.save(tVSignIn);
        return Result.ofUpdateSuccess(tVSignIn.convert(TVSignInVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "远教视频签到记录id", required = true) @PathVariable String id) {
        tVSignInService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<TVSignInVO>> list(@ApiParam(value = "远教视频签到记录查询条件", required = true) @RequestBody TVSignInSearchable tVSignInSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<TVSignIn> tVSignInList = tVSignInService.findAll(tVSignInSearchable, sort);
        List<TVSignInVO> tVSignInVOList = TVSignIn.convert(tVSignInList, TVSignInVO.class);
        return Result.of(tVSignInVOList);
    }

    @Override
    public Result<Page<TVSignInVO>> page(@ApiParam(value = "远教视频签到记录查询条件", required = true) @RequestBody TVSignInSearchable tVSignInSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<TVSignIn> tVSignInPage = tVSignInService.findAll(tVSignInSearchable, pageable);
        Page<TVSignInVO> tVSignInVOPage = TVSignIn.convert(tVSignInPage, TVSignInVO.class);
        return Result.of(tVSignInVOPage);
    }

    @Override
    public Result<TVSignInVO> update(@PathVariable String id, @PathVariable Integer type, @PathVariable String username) {
        TVSignIn save = tVSignInService.save(id, type, username);
        return Result.of(save.convert(TVSignInVO.class));
    }

}