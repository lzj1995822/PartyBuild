package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.InformationAuditController;
import com.cloudkeeper.leasing.identity.domain.Information;
import com.cloudkeeper.leasing.identity.domain.InformationAudit;
import com.cloudkeeper.leasing.identity.domain.ParActivity;
import com.cloudkeeper.leasing.identity.domain.ParPictureInfro;
import com.cloudkeeper.leasing.identity.dto.InformationAudit.InformationAuditDTO;
import com.cloudkeeper.leasing.identity.dto.InformationAuditSearchable;
import com.cloudkeeper.leasing.identity.dto.information.InformationDTO;
import com.cloudkeeper.leasing.identity.dto.information.InformationSearchable;
import com.cloudkeeper.leasing.identity.dto.paractivity.ParActivityDTO;
import com.cloudkeeper.leasing.identity.dto.paractivity.ParActivitySearchable;
import com.cloudkeeper.leasing.identity.dto.parpictureinfro.ParPictureInfroDTO;
import com.cloudkeeper.leasing.identity.service.InformationAuditService;
import com.cloudkeeper.leasing.identity.service.InformationService;
import com.cloudkeeper.leasing.identity.service.SysLogService;
import com.cloudkeeper.leasing.identity.vo.InformationAuditVO;
import com.cloudkeeper.leasing.identity.vo.InformationVO;
import com.cloudkeeper.leasing.identity.vo.ParActivityVO;
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
 * 消息通知 controller
 *
 * @author zdw
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InformationAuditControllerImpl implements InformationAuditController {

    /**
     * 村干部审核记录 service
     */
    private final InformationAuditService informationAuditService;

    private final SysLogService sysLogService;

    @Override
    public Result<InformationAuditVO> findOne(String informationAuditId) {
        Optional<InformationAudit> optionalById = informationAuditService.findOptionalById(informationAuditId);
        if (optionalById.isPresent()) {
            InformationAudit information = optionalById.get();
            sysLogService.pushLog(this.getClass().getName(), "村干部审核信息查询", informationAuditService.getTableName(), information.getId());
            return Result.of(information.convert(InformationAuditVO.class));
        }
        return Result.ofNotFound();
    }

    @Override
    public Result delete(String id) {
        InformationAudit byId = informationAuditService.findById(id);
        informationAuditService.deleteById(id);
        sysLogService.pushLog(this.getClass().getName(), "村干部审核信息删除", informationAuditService.getTableName(), byId.getId());
        return Result.ofDeleteSuccess();
    }


    @Override
    public Result<InformationAuditVO> update(@ApiParam(value = "活动id", required = true) @PathVariable String id,
                                             @ApiParam(value = "活动 DTO", required = true) @RequestBody @Validated InformationAuditDTO informationAuditDTO) {
        Optional<InformationAudit> informationAuditOptional = informationAuditService.findOptionalById(id);
        if (!informationAuditOptional.isPresent()) {
            return Result.ofLost();
        }
        InformationAudit save = informationAuditService.save(informationAuditDTO.convert(InformationAudit.class));
        sysLogService.pushLog(this.getClass().getName(), "村干部审核更新", informationAuditService.getTableName(), save.getId());
        return Result.ofUpdateSuccess(save.convert(InformationAuditVO.class));
    }


    @Override
    public Result<List<InformationAuditVO>> list(InformationAuditSearchable informationAuditSearchable, Sort sort) {
        List<InformationAudit> informationAudits = informationAuditService.findAll(informationAuditSearchable, sort);
        List<InformationAuditVO> informationAuditVOList = Information.convert(informationAudits, InformationAuditVO.class);
        return Result.of(informationAuditVOList);
    }

    @Override
    public Result<Page<InformationAuditVO>> page(InformationAuditSearchable informationAuditSearchable, Pageable pageable) {
        Page<InformationAudit> informationAuditPage = informationAuditService.findAll(informationAuditSearchable, pageable);
        Page<InformationAuditVO> informationVOPage = Information.convert(informationAuditPage, InformationAuditVO.class);
        return Result.of(informationVOPage);
    }

}
