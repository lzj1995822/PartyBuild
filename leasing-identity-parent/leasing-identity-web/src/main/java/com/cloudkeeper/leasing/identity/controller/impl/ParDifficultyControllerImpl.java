package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.ParDifficultyController;
import com.cloudkeeper.leasing.identity.domain.ParDifficulty;
import com.cloudkeeper.leasing.identity.domain.ParMember;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.identity.dto.pardifficulty.ParDifficultyDTO;
import com.cloudkeeper.leasing.identity.dto.pardifficulty.ParDifficultySearchable;
import com.cloudkeeper.leasing.identity.service.ParDifficultyService;
import com.cloudkeeper.leasing.identity.service.ParMemberService;
import com.cloudkeeper.leasing.identity.service.SysDistrictService;
import com.cloudkeeper.leasing.identity.vo.ParDifficultyVO;
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
 * 困难党员 controller
 * @author cqh
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParDifficultyControllerImpl implements ParDifficultyController {

    /** 困难党员 service */
    private final ParDifficultyService parDifficultyService;

    private final ParMemberService parMemberService;

    private final SysDistrictService sysDistrictService;

    @Override
    public Result<ParDifficultyVO> findOne(@ApiParam(value = "困难党员id", required = true) @PathVariable String id) {
        Optional<ParDifficulty> parDifficultyOptional = parDifficultyService.findOptionalById(id);
        return parDifficultyOptional.map(parDifficulty -> Result.of(parDifficulty.convert(ParDifficultyVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<ParDifficultyVO> add(@ApiParam(value = "困难党员 DTO", required = true) @RequestBody @Validated ParDifficultyDTO parDifficultyDTO) {
        ParMember memberServiceById = parMemberService.findById(parDifficultyDTO.getPartyMemberId());
        List<SysDistrict> sysDistricts = sysDistrictService.sysDistrictsByDistrictId(memberServiceById.getDistrictId());
        parDifficultyDTO.setDistrictId(memberServiceById.getDistrictId());
        parDifficultyDTO.setMemberName(memberServiceById.getName());
        parDifficultyDTO.setDistrictName(sysDistricts.get(0).getDistrictName());
        ParDifficulty parDifficulty = parDifficultyService.save(parDifficultyDTO.convert(ParDifficulty.class));
        return Result.ofAddSuccess(parDifficulty.convert(ParDifficultyVO.class));
    }

    @Override
    public Result<ParDifficultyVO> update(@ApiParam(value = "困难党员id", required = true) @PathVariable String id,
        @ApiParam(value = "困难党员 DTO", required = true) @RequestBody @Validated ParDifficultyDTO parDifficultyDTO) {
        Optional<ParDifficulty> parDifficultyOptional = parDifficultyService.findOptionalById(id);
        if (!parDifficultyOptional.isPresent()) {
            return Result.ofLost();
        }
        ParDifficulty parDifficulty = parDifficultyOptional.get();
        ParMember memberServiceById = parMemberService.findById(parDifficultyDTO.getPartyMemberId());
        List<SysDistrict> sysDistricts = sysDistrictService.sysDistrictsByDistrictId(memberServiceById.getDistrictId());
        parDifficultyDTO.setDistrictId(memberServiceById.getDistrictId());
        parDifficultyDTO.setMemberName(memberServiceById.getName());
        parDifficultyDTO.setDistrictName(sysDistricts.get(0).getDistrictName());
        BeanUtils.copyProperties(parDifficultyDTO, parDifficulty);
        parDifficulty = parDifficultyService.save(parDifficulty);
        return Result.ofUpdateSuccess(parDifficulty.convert(ParDifficultyVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "困难党员id", required = true) @PathVariable String id) {
        parDifficultyService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<ParDifficultyVO>> list(@ApiParam(value = "困难党员查询条件", required = true) @RequestBody ParDifficultySearchable parDifficultySearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<ParDifficulty> parDifficultyList = parDifficultyService.findAll(parDifficultySearchable, sort);
        List<ParDifficultyVO> parDifficultyVOList = ParDifficulty.convert(parDifficultyList, ParDifficultyVO.class);
        return Result.of(parDifficultyVOList);
    }

    @Override
    public Result<Page<ParDifficultyVO>> page(@ApiParam(value = "困难党员查询条件", required = true) @RequestBody ParDifficultySearchable parDifficultySearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<ParDifficulty> parDifficultyPage = parDifficultyService.findAll(parDifficultySearchable, pageable);
        Page<ParDifficultyVO> parDifficultyVOPage = ParDifficulty.convert(parDifficultyPage, ParDifficultyVO.class);
        return Result.of(parDifficultyVOPage);
    }

}