package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.AuthorizationTokenController;
import com.cloudkeeper.leasing.identity.domain.AuthorizationToken;
import com.cloudkeeper.leasing.identity.dto.authorizationtoken.AuthorizationTokenDTO;
import com.cloudkeeper.leasing.identity.dto.authorizationtoken.AuthorizationTokenSearchable;
import com.cloudkeeper.leasing.identity.service.AuthorizationTokenService;
import com.cloudkeeper.leasing.identity.vo.AuthorizationTokenVO;
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
 * 访问权限 controller
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizationTokenControllerImpl implements AuthorizationTokenController {

    /** 访问权限 service */
    private final AuthorizationTokenService authorizationTokenService;

    @Override
    public Result<AuthorizationTokenVO> findOne(@ApiParam(value = "访问权限id", required = true) @PathVariable String id) {
        Optional<AuthorizationToken> authorizationTokenOptional = authorizationTokenService.findOptionalById(id);
        return authorizationTokenOptional.map(authorizationToken -> Result.of(authorizationToken.convert(AuthorizationTokenVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<AuthorizationTokenVO> add(@ApiParam(value = "访问权限 DTO", required = true) @RequestBody @Validated AuthorizationTokenDTO authorizationTokenDTO) {
        AuthorizationToken authorizationToken = authorizationTokenService.save(authorizationTokenDTO);
        if (authorizationToken == null) {
            return Result.of(Result.ResultCode.FAIL.getCode(),"未输入code");
        }
        return Result.ofAddSuccess(authorizationToken.convert(AuthorizationTokenVO.class));
    }

    @Override
    public Result<AuthorizationTokenVO> update(@ApiParam(value = "访问权限id", required = true) @PathVariable String id,
        @ApiParam(value = "访问权限 DTO", required = true) @RequestBody @Validated AuthorizationTokenDTO authorizationTokenDTO) {
        Optional<AuthorizationToken> authorizationTokenOptional = authorizationTokenService.findOptionalById(id);
        if (!authorizationTokenOptional.isPresent()) {
            return Result.ofLost();
        }
        AuthorizationToken authorizationToken = authorizationTokenService.save(authorizationTokenDTO);
        if (authorizationToken == null) {
            return Result.of(Result.ResultCode.FAIL.getCode(),"未输入code");
        }
        return Result.ofUpdateSuccess(authorizationToken.convert(AuthorizationTokenVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "访问权限id", required = true) @PathVariable String id) {
        Boolean delete = authorizationTokenService.delete(id);
        if( delete ){
            return Result.of(Result.ResultCode.OK.getCode(),"删除成功",delete);
        }
        return Result.of(Result.ResultCode.OK.getCode(),"删除失败",delete);
    }

    @Override
    public Result<List<AuthorizationTokenVO>> list(@ApiParam(value = "访问权限查询条件", required = true) @RequestBody AuthorizationTokenSearchable authorizationTokenSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<AuthorizationToken> authorizationTokenList = authorizationTokenService.findAll(authorizationTokenSearchable, sort);
        List<AuthorizationTokenVO> authorizationTokenVOList = AuthorizationToken.convert(authorizationTokenList, AuthorizationTokenVO.class);
        return Result.of(authorizationTokenVOList);
    }

    @Override
    public Result<Page<AuthorizationTokenVO>> page(@ApiParam(value = "访问权限查询条件", required = true) @RequestBody AuthorizationTokenSearchable authorizationTokenSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<AuthorizationToken> authorizationTokenPage = authorizationTokenService.findAll(authorizationTokenSearchable, pageable);
        Page<AuthorizationTokenVO> authorizationTokenVOPage = AuthorizationToken.convert(authorizationTokenPage, AuthorizationTokenVO.class);
        return Result.of(authorizationTokenVOPage);
    }

    @Override
    public Result<Boolean> updateRedis(@PathVariable String id, @PathVariable String isUse) {
        Boolean aBoolean = authorizationTokenService.updateRedis(id, isUse);
        if (aBoolean) {
            if (isUse.equals("1")){
                return Result.of(Result.ResultCode.OK.getCode(), "启用成功", aBoolean);
            }else {
                return Result.of(Result.ResultCode.OK.getCode(), "禁用成功", aBoolean);
            }
        }
        return Result.of(Result.ResultCode.FAIL.getCode(), "设置失败",aBoolean);
    }

}