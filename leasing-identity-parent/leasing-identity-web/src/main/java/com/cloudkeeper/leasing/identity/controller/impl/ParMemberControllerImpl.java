package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.ParMemberController;
import com.cloudkeeper.leasing.identity.domain.ParMember;
import com.cloudkeeper.leasing.identity.dto.parmember.ParMemberDTO;
import com.cloudkeeper.leasing.identity.dto.parmember.ParMemberSearchable;
import com.cloudkeeper.leasing.identity.service.ParMemberService;
import com.cloudkeeper.leasing.identity.service.SysLogService;
import com.cloudkeeper.leasing.identity.vo.ParMemberChartsVo;
import com.cloudkeeper.leasing.identity.vo.ParMemberVO;
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
 * 党员管理 controller
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParMemberControllerImpl implements ParMemberController {

    /** 党员管理 service */
    private final ParMemberService parMemberService;

    private final SysLogService sysLogService;

    @Override
    public Result<ParMemberVO> findOne(@ApiParam(value = "党员管理id", required = true) @PathVariable String id) {
        Optional<ParMember> parMemberOptional = parMemberService.findOptionalById(id);
        return parMemberOptional.map(parMember -> Result.of(parMember.convert(ParMemberVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<ParMemberVO> add(@ApiParam(value = "党员管理 DTO", required = true) @RequestBody @Validated ParMemberDTO parMemberDTO) {
        ParMember parMember = parMemberService.save(parMemberDTO.convert(ParMember.class));
        String  msg= parMemberService.actionLog("新增","[党员信息]", parMember.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,parMemberService.getTableName(),parMember.getId());
        return Result.ofAddSuccess(parMember.convert(ParMemberVO.class));
    }

    @Override
    public Result<ParMemberVO> update(@ApiParam(value = "党员管理id", required = true) @PathVariable String id,
        @ApiParam(value = "党员管理 DTO", required = true) @RequestBody @Validated ParMemberDTO parMemberDTO) {
        Optional<ParMember> parMemberOptional = parMemberService.findOptionalById(id);
        if (!parMemberOptional.isPresent()) {
            return Result.ofLost();
        }
        ParMember parMember = parMemberOptional.get();
        BeanUtils.copyProperties(parMemberDTO, parMember);
        parMember = parMemberService.save(parMember);
        String  msg= parMemberService.actionLog("修改","[党员信息]", parMember.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,parMemberService.getTableName(),parMember.getId());
        return Result.ofUpdateSuccess(parMember.convert(ParMemberVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "党员管理id", required = true) @PathVariable String id) {
        ParMember parMember = parMemberService.findById(id);
        parMemberService.deleteById(id);
        String  msg= parMemberService.actionLog("删除","[党员信息]", parMember.getName());
        sysLogService.pushLog(this.getClass().getName(),msg,parMemberService.getTableName(),parMember.getId());
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<ParMemberVO>> list(@ApiParam(value = "党员管理查询条件", required = true) @RequestBody ParMemberSearchable parMemberSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<ParMember> parMemberList = parMemberService.findAll(parMemberSearchable, sort);
        List<ParMemberVO> parMemberVOList = ParMember.convert(parMemberList, ParMemberVO.class);
        return Result.of(parMemberVOList);
    }

    @Override
    public Result<Page<ParMemberVO>> page(@ApiParam(value = "党员管理查询条件", required = true) @RequestBody ParMemberSearchable parMemberSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<ParMember> parMemberPage = parMemberService.findAll(parMemberSearchable, pageable);
        Page<ParMemberVO> parMemberVOPage = ParMember.convert(parMemberPage, ParMemberVO.class);
        return Result.of(parMemberVOPage);
    }

    @Authorization(required = true)
    @Override
    public Result<List<ParMemberChartsVo>> statisticsSex(@PathVariable String districtId) {
        List<ParMemberChartsVo> parMemberChartsVos = parMemberService.statisticsSex(districtId);
        return Result.of(parMemberChartsVos);
    }

    @Authorization(required = true)
    @Override
    public Result<List<ParMemberChartsVo>> statisticsAge(@PathVariable String districtId) {
        List<ParMemberChartsVo> parMemberChartsVos = parMemberService.statisticsAge(districtId);
        return Result.of(parMemberChartsVos);
    }

    @Authorization(required = true)
    @Override
    public Result<List<ParMemberChartsVo>> statisticsBranch(@PathVariable String districtId) {
        List<ParMemberChartsVo> parMemberChartsVos = parMemberService.statisticsBranch(districtId);
        return Result.of(parMemberChartsVos);
    }

}