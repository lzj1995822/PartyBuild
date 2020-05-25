package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.KpiQuotaController;
import com.cloudkeeper.leasing.identity.domain.KPIAttachment;
import com.cloudkeeper.leasing.identity.domain.KPITownQuota;
import com.cloudkeeper.leasing.identity.domain.KpiQuota;
import com.cloudkeeper.leasing.identity.dto.kpiquota.KpiQuotaDTO;
import com.cloudkeeper.leasing.identity.dto.kpiquota.KpiQuotaSearchable;
import com.cloudkeeper.leasing.identity.service.KPIAttachmentService;
import com.cloudkeeper.leasing.identity.service.KPITownQuotaService;
import com.cloudkeeper.leasing.identity.service.KpiQuotaService;
import com.cloudkeeper.leasing.identity.vo.KPIAttachmentVO;
import com.cloudkeeper.leasing.identity.vo.KPITownQuotaVO;
import com.cloudkeeper.leasing.identity.vo.KpiQuotaVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

/**
 * 村主任考核指标 controller
 * @author yujian
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KpiQuotaControllerImpl implements KpiQuotaController {

    /** 村主任考核指标 service */
    private final KpiQuotaService kpiQuotaService;

    private final KPIAttachmentService kpiAttachmentService;

    private final KPITownQuotaService kpiTownQuotaService;

    @Override
    public Result<KpiQuotaVO> findOne(@ApiParam(value = "村主任考核指标id", required = true) @PathVariable String id) {
        Optional<KpiQuota> kpiQuotaOptional = kpiQuotaService.findOptionalById(id);
        return kpiQuotaOptional.map(kpiQuota -> Result.of(kpiQuota.convert(KpiQuotaVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<KpiQuotaVO> add(@ApiParam(value = "村主任考核指标 DTO", required = true) @RequestBody @Validated KpiQuotaDTO kpiQuotaDTO) {
        KpiQuota kpiQuota = kpiQuotaService.save(kpiQuotaDTO.convert(KpiQuota.class));
        return Result.ofAddSuccess(kpiQuota.convert(KpiQuotaVO.class));
    }

    @Override
    public Result<KpiQuotaVO> update(@ApiParam(value = "村主任考核指标id", required = true) @PathVariable String id,
        @ApiParam(value = "村主任考核指标 DTO", required = true) @RequestBody @Validated KpiQuotaDTO kpiQuotaDTO) {
        Optional<KpiQuota> kpiQuotaOptional = kpiQuotaService.findOptionalById(id);
        if (!kpiQuotaOptional.isPresent()) {
            return Result.ofLost();
        }
        KpiQuota kpiQuota = kpiQuotaOptional.get();
        BeanUtils.copyProperties(kpiQuotaDTO, kpiQuota);
        kpiQuota = kpiQuotaService.save(kpiQuota);
        return Result.ofUpdateSuccess(kpiQuota.convert(KpiQuotaVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "村主任考核指标id", required = true) @PathVariable String id) {
        kpiQuotaService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<KpiQuotaVO>> list(@ApiParam(value = "村主任考核指标查询条件", required = true) @RequestBody KpiQuotaSearchable kpiQuotaSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<KpiQuota> kpiQuotaList = kpiQuotaService.findAll(kpiQuotaSearchable, sort);
        List<KpiQuotaVO> kpiQuotaVOList = KpiQuota.convert(kpiQuotaList, KpiQuotaVO.class);
        for (KpiQuotaVO item : kpiQuotaVOList) {
            KPIAttachment byQuota = kpiAttachmentService.findByQuota(item.getQuotaId(), kpiQuotaSearchable.getDistrictId(), kpiQuotaSearchable.getQuarter(), kpiQuotaSearchable.getTaskId());
            if (byQuota == null) {
                continue;
            }
            item.setAttachment(byQuota.convert(KPIAttachmentVO.class));
        }
        return Result.of(kpiQuotaVOList);
    }

    @Override
    public Result<Page<KpiQuotaVO>> page(@ApiParam(value = "村主任考核指标查询条件", required = true) @RequestBody KpiQuotaSearchable kpiQuotaSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<KpiQuota> kpiQuotaPage = kpiQuotaService.findAll(kpiQuotaSearchable, pageable);
        Page<KpiQuotaVO> kpiQuotaVOPage = KpiQuota.convert(kpiQuotaPage, KpiQuotaVO.class);
        return Result.of(kpiQuotaVOPage);
    }

    @PostMapping("/blukSave")
    @Transactional
    public Result<List<KpiQuotaVO>> blukSave(@RequestBody List<KpiQuotaDTO> kpiQuotaDTOS, @Nonnull String quotaYear) {
        kpiQuotaService.deleteAllByQuotaYear(quotaYear);
        for (KpiQuotaDTO item :kpiQuotaDTOS) {
            KpiQuota convert = item.convert(KpiQuota.class);
            convert.setKpiQuotas(null);
            kpiQuotaService.save(convert);
            for (KpiQuotaDTO subItem : item.getKpiQuotas()) {
                KpiQuota convert1 = subItem.convert(KpiQuota.class);
                convert1.setKpiQuotas(null);
                kpiQuotaService.save(convert1);
            }
        }
        KpiQuotaSearchable kpiQuotaSearchable = new KpiQuotaSearchable();
        kpiQuotaSearchable.setQuotaYear(kpiQuotaDTOS.get(0).getQuotaYear());
        List<KpiQuota> all = kpiQuotaService.findAll(kpiQuotaSearchable);
        List<KpiQuotaVO> convert = KpiQuota.convert(all, KpiQuotaVO.class);
        return Result.of(convert);
    }

    @GetMapping("/getBlowSecondsByQuotaId")
    public Result<List<KpiQuotaVO>> getBlowSecondsByQuotaId(@Nonnull String quotaId, @Nonnull String districtId, String isDepart) {
        KpiQuota kpiQuota = kpiQuotaService.findByQuotaId(quotaId);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KpiQuota.class);
        detachedCriteria.add(Restrictions.eq("parentQuotaId", quotaId));
        detachedCriteria.add(Restrictions.like("quotaMakeDepartId", districtId, MatchMode.ANYWHERE));
        detachedCriteria.addOrder(Order.asc("quotaId"));
        List<KpiQuota> kpiQuotas = kpiQuotaService.findAll(detachedCriteria);
        List<KpiQuotaVO> convert = KpiQuota.convert(kpiQuotas, KpiQuotaVO.class);
        if ("1".equals(isDepart)) {
            districtId = "0101";
        }
        for (KpiQuotaVO item : convert) {
            List<KPITownQuota> kpiTownQuotas = kpiTownQuotaService.findAllByParentQuotaIdAndDistrictId(item.getQuotaId(), districtId);
            item.setKpiTownQuotas(KPITownQuota.convert(kpiTownQuotas, KPITownQuotaVO.class)); ;
        }
        return Result.of(convert);
    }

}
