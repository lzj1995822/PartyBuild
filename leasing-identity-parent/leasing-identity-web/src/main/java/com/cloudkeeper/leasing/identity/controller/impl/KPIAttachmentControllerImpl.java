package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.KPIAttachmentController;
import com.cloudkeeper.leasing.identity.domain.KPIAttachment;
import com.cloudkeeper.leasing.identity.dto.kpiattachment.KPIAttachmentDTO;
import com.cloudkeeper.leasing.identity.dto.kpiattachment.KPIAttachmentSearchable;
import com.cloudkeeper.leasing.identity.service.KPIAttachmentService;
import com.cloudkeeper.leasing.identity.vo.KPIAttachmentVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * 考核实施佐证材料 controller
 * @author asher
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KPIAttachmentControllerImpl implements KPIAttachmentController {

    /** 考核实施佐证材料 service */
    private final KPIAttachmentService kPIAttachmentService;

    @Override
    public Result<KPIAttachmentVO> findOne(@ApiParam(value = "考核实施佐证材料id", required = true) @PathVariable String id) {
        Optional<KPIAttachment> kPIAttachmentOptional = kPIAttachmentService.findOptionalById(id);
        return kPIAttachmentOptional.map(kPIAttachment -> Result.of(kPIAttachment.convert(KPIAttachmentVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    @Transactional
    public Result<KPIAttachmentVO> add(@ApiParam(value = "考核实施佐证材料 DTO", required = true) @RequestBody @Validated KPIAttachmentDTO kPIAttachmentDTO) {
        kPIAttachmentService.deleteAllByQuotaIdAndDistrictIdAndQuarterAndTaskId(kPIAttachmentDTO.getQuotaId(), kPIAttachmentDTO.getDistrictId(), kPIAttachmentDTO.getQuarter(), kPIAttachmentDTO.getTaskId());
        KPIAttachment kPIAttachment = kPIAttachmentService.save(kPIAttachmentDTO.convert(KPIAttachment.class));
        return Result.ofAddSuccess(kPIAttachment.convert(KPIAttachmentVO.class));
    }

    @Override
    public Result<KPIAttachmentVO> update(@ApiParam(value = "考核实施佐证材料id", required = true) @PathVariable String id,
        @ApiParam(value = "考核实施佐证材料 DTO", required = true) @RequestBody @Validated KPIAttachmentDTO kPIAttachmentDTO) {
        Optional<KPIAttachment> kPIAttachmentOptional = kPIAttachmentService.findOptionalById(id);
        if (!kPIAttachmentOptional.isPresent()) {
            return Result.ofLost();
        }
        KPIAttachment kPIAttachment = kPIAttachmentOptional.get();
        BeanUtils.copyProperties(kPIAttachmentDTO, kPIAttachment);
        kPIAttachment = kPIAttachmentService.save(kPIAttachment);
        return Result.ofUpdateSuccess(kPIAttachment.convert(KPIAttachmentVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "考核实施佐证材料id", required = true) @PathVariable String id) {
        kPIAttachmentService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<KPIAttachmentVO>> list(@ApiParam(value = "考核实施佐证材料查询条件", required = true) @RequestBody KPIAttachmentSearchable kPIAttachmentSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<KPIAttachment> kPIAttachmentList = kPIAttachmentService.findAll(kPIAttachmentSearchable, sort);
        List<KPIAttachmentVO> kPIAttachmentVOList = KPIAttachment.convert(kPIAttachmentList, KPIAttachmentVO.class);
        return Result.of(kPIAttachmentVOList);
    }

    @Override
    public Result<Page<KPIAttachmentVO>> page(@ApiParam(value = "考核实施佐证材料查询条件", required = true) @RequestBody KPIAttachmentSearchable kPIAttachmentSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<KPIAttachment> kPIAttachmentPage = kPIAttachmentService.findAll(kPIAttachmentSearchable, pageable);
        Page<KPIAttachmentVO> kPIAttachmentVOPage = KPIAttachment.convert(kPIAttachmentPage, KPIAttachmentVO.class);
        return Result.of(kPIAttachmentVOPage);
    }

}