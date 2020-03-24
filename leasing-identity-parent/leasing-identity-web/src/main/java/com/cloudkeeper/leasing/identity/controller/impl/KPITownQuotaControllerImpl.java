package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.KPITownQuotaController;
import com.cloudkeeper.leasing.identity.domain.KPITownQuota;
import com.cloudkeeper.leasing.identity.domain.KpiQuota;
import com.cloudkeeper.leasing.identity.dto.kpiquota.KpiQuotaSearchable;
import com.cloudkeeper.leasing.identity.dto.kpitownquota.KPITownQuotaDTO;
import com.cloudkeeper.leasing.identity.dto.kpitownquota.KPITownQuotaSearchable;
import com.cloudkeeper.leasing.identity.service.KPITownQuotaService;
import com.cloudkeeper.leasing.identity.service.KpiQuotaService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * 镇考核指标 controller
 * @author yujian
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KPITownQuotaControllerImpl implements KPITownQuotaController {

    /** 镇考核指标 service */
    private final KPITownQuotaService kPITownQuotaService;
    /** 村主任考核指标 service */
    private final KpiQuotaService kpiQuotaService;
    @Override
    public Result<KPITownQuotaVO> findOne(@ApiParam(value = "镇考核指标id", required = true) @PathVariable String id) {
        Optional<KPITownQuota> kPITownQuotaOptional = kPITownQuotaService.findOptionalById(id);
        return kPITownQuotaOptional.map(kPITownQuota -> Result.of(kPITownQuota.convert(KPITownQuotaVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<KPITownQuotaVO> add(@ApiParam(value = "镇考核指标 DTO", required = true) @RequestBody @Validated KPITownQuotaDTO kPITownQuotaDTO) {
        KPITownQuota kPITownQuota = kPITownQuotaService.save(kPITownQuotaDTO.convert(KPITownQuota.class));
        return Result.ofAddSuccess(kPITownQuota.convert(KPITownQuotaVO.class));
    }

    @Override
    public Result<KPITownQuotaVO> update(@ApiParam(value = "镇考核指标id", required = true) @PathVariable String id,
        @ApiParam(value = "镇考核指标 DTO", required = true) @RequestBody @Validated KPITownQuotaDTO kPITownQuotaDTO) {
        Optional<KPITownQuota> kPITownQuotaOptional = kPITownQuotaService.findOptionalById(id);
        if (!kPITownQuotaOptional.isPresent()) {
            return Result.ofLost();
        }
        KPITownQuota kPITownQuota = kPITownQuotaOptional.get();
        BeanUtils.copyProperties(kPITownQuotaDTO, kPITownQuota);
        kPITownQuota = kPITownQuotaService.save(kPITownQuota);
        return Result.ofUpdateSuccess(kPITownQuota.convert(KPITownQuotaVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "镇考核指标id", required = true) @PathVariable String id) {
        kPITownQuotaService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<KPITownQuotaVO>> list(@ApiParam(value = "镇考核指标查询条件", required = true) @RequestBody KPITownQuotaSearchable kPITownQuotaSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<KPITownQuota> kPITownQuotaList = kPITownQuotaService.findAll(kPITownQuotaSearchable, sort);
        List<KPITownQuotaVO> kPITownQuotaVOList = KPITownQuota.convert(kPITownQuotaList, KPITownQuotaVO.class);
        return Result.of(kPITownQuotaVOList);
    }

    @Override
    public Result<Page<KPITownQuotaVO>> page(@ApiParam(value = "镇考核指标查询条件", required = true) @RequestBody KPITownQuotaSearchable kPITownQuotaSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<KPITownQuota> kPITownQuotaPage = kPITownQuotaService.findAll(kPITownQuotaSearchable, pageable);
        Page<KPITownQuotaVO> kPITownQuotaVOPage = KPITownQuota.convert(kPITownQuotaPage, KPITownQuotaVO.class);
        return Result.of(kPITownQuotaVOPage);
    }

    @Override
    public Result<Object> getAll(String districtId) {
        KpiQuotaSearchable kpiQuotaSearchable = new KpiQuotaSearchable();
        kpiQuotaSearchable.setParentQuotaId("0");
        List<KpiQuota> kpiQuotas = kpiQuotaService.findAll(kpiQuotaSearchable,new Sort(Sort.Direction.DESC,"parentQuotaId"));
        List<KpiQuotaVO> quotaVOList = KpiQuota.convert(kpiQuotas,KpiQuotaVO.class);
        for (KpiQuotaVO k : quotaVOList){
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPITownQuota.class);
            detachedCriteria.add(Restrictions.ilike("parentQuotaId", k.getQuotaId(), MatchMode.START));
            detachedCriteria.add(Restrictions.eq("districtId",districtId));
            detachedCriteria.addOrder(Order.desc("createdAt"));
            List<KPITownQuota> kpiTownQuotas = kPITownQuotaService.findAll(detachedCriteria);
            List<KPITownQuotaVO> kpiTownQuotaVOS = KPITownQuota.convert(kpiTownQuotas,KPITownQuotaVO.class);
            k.setKpiTownQuotaVOS(kpiTownQuotaVOS);
        }
        return Result.of(quotaVOList);
    }

}
