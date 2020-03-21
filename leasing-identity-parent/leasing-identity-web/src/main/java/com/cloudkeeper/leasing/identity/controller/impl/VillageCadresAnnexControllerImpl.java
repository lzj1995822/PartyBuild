package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.VillageCadresAnnexController;
import com.cloudkeeper.leasing.identity.domain.VillageCadresAnnex;
import com.cloudkeeper.leasing.identity.dto.villagecadresannex.VillageCadresAnnexDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadresannex.VillageCadresAnnexSearchable;
import com.cloudkeeper.leasing.identity.service.VillageCadresAnnexService;
import com.cloudkeeper.leasing.identity.vo.VillageCadresAnnexVO;
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
 * 村主任信息附件 controller
 * @author yujian
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VillageCadresAnnexControllerImpl implements VillageCadresAnnexController {

    /** 村主任信息附件 service */
    private final VillageCadresAnnexService villageCadresAnnexService;

    @Override
    public Result<VillageCadresAnnexVO> findOne(@ApiParam(value = "村主任信息附件id", required = true) @PathVariable String id) {
        Optional<VillageCadresAnnex> villageCadresAnnexOptional = villageCadresAnnexService.findOptionalById(id);
        return villageCadresAnnexOptional.map(villageCadresAnnex -> Result.of(villageCadresAnnex.convert(VillageCadresAnnexVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<VillageCadresAnnexVO> add(@ApiParam(value = "村主任信息附件 DTO", required = true) @RequestBody @Validated VillageCadresAnnexDTO villageCadresAnnexDTO) {
        VillageCadresAnnex villageCadresAnnex = villageCadresAnnexService.save(villageCadresAnnexDTO.convert(VillageCadresAnnex.class));
        return Result.ofAddSuccess(villageCadresAnnex.convert(VillageCadresAnnexVO.class));
    }

    @Override
    public Result<VillageCadresAnnexVO> update(@ApiParam(value = "村主任信息附件id", required = true) @PathVariable String id,
        @ApiParam(value = "村主任信息附件 DTO", required = true) @RequestBody @Validated VillageCadresAnnexDTO villageCadresAnnexDTO) {
        Optional<VillageCadresAnnex> villageCadresAnnexOptional = villageCadresAnnexService.findOptionalById(id);
        if (!villageCadresAnnexOptional.isPresent()) {
            return Result.ofLost();
        }
        VillageCadresAnnex villageCadresAnnex = villageCadresAnnexOptional.get();
        BeanUtils.copyProperties(villageCadresAnnexDTO, villageCadresAnnex);
        villageCadresAnnex = villageCadresAnnexService.save(villageCadresAnnex);
        return Result.ofUpdateSuccess(villageCadresAnnex.convert(VillageCadresAnnexVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "村主任信息附件id", required = true) @PathVariable String id) {
        villageCadresAnnexService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<VillageCadresAnnexVO>> list(@ApiParam(value = "村主任信息附件查询条件", required = true) @RequestBody VillageCadresAnnexSearchable villageCadresAnnexSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<VillageCadresAnnex> villageCadresAnnexList = villageCadresAnnexService.findAll(villageCadresAnnexSearchable, sort);
        List<VillageCadresAnnexVO> villageCadresAnnexVOList = VillageCadresAnnex.convert(villageCadresAnnexList, VillageCadresAnnexVO.class);
        return Result.of(villageCadresAnnexVOList);
    }

    @Override
    public Result<Page<VillageCadresAnnexVO>> page(@ApiParam(value = "村主任信息附件查询条件", required = true) @RequestBody VillageCadresAnnexSearchable villageCadresAnnexSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<VillageCadresAnnex> villageCadresAnnexPage = villageCadresAnnexService.findAll(villageCadresAnnexSearchable, pageable);
        Page<VillageCadresAnnexVO> villageCadresAnnexVOPage = VillageCadresAnnex.convert(villageCadresAnnexPage, VillageCadresAnnexVO.class);
        return Result.of(villageCadresAnnexVOPage);
    }

}