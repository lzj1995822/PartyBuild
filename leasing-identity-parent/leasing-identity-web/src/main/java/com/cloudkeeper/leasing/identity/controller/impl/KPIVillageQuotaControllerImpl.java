package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.KPIVillageQuotaController;
import com.cloudkeeper.leasing.identity.domain.KPIVillageQuota;
import com.cloudkeeper.leasing.identity.dto.kpivillagequota.KPIVillageQuotaDTO;
import com.cloudkeeper.leasing.identity.dto.kpivillagequota.KPIVillageQuotaSearchable;
import com.cloudkeeper.leasing.identity.service.KPIVillageQuotaService;
import com.cloudkeeper.leasing.identity.vo.KPIVillageQuotaVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 村考核指标 controller
 * @author yujian
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KPIVillageQuotaControllerImpl implements KPIVillageQuotaController {

    /** 村考核指标 service */
    private final KPIVillageQuotaService kPIVillageQuotaService;

    @Override
    public Result<KPIVillageQuotaVO> findOne(@ApiParam(value = "村考核指标id", required = true) @PathVariable String id) {
        Optional<KPIVillageQuota> kPIVillageQuotaOptional = kPIVillageQuotaService.findOptionalById(id);
        return kPIVillageQuotaOptional.map(kPIVillageQuota -> Result.of(kPIVillageQuota.convert(KPIVillageQuotaVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<KPIVillageQuotaVO> add(@ApiParam(value = "村考核指标 DTO", required = true) @RequestBody @Validated KPIVillageQuotaDTO kPIVillageQuotaDTO) {
        KPIVillageQuota kPIVillageQuota = kPIVillageQuotaService.save(kPIVillageQuotaDTO.convert(KPIVillageQuota.class));
        return Result.ofAddSuccess(kPIVillageQuota.convert(KPIVillageQuotaVO.class));
    }

    @Override
    public Result<KPIVillageQuotaVO> update(@ApiParam(value = "村考核指标id", required = true) @PathVariable String id,
        @ApiParam(value = "村考核指标 DTO", required = true) @RequestBody @Validated KPIVillageQuotaDTO kPIVillageQuotaDTO) {
        Optional<KPIVillageQuota> kPIVillageQuotaOptional = kPIVillageQuotaService.findOptionalById(id);
        if (!kPIVillageQuotaOptional.isPresent()) {
            return Result.ofLost();
        }
        KPIVillageQuota kPIVillageQuota = kPIVillageQuotaOptional.get();
        BeanUtils.copyProperties(kPIVillageQuotaDTO, kPIVillageQuota);
        kPIVillageQuota = kPIVillageQuotaService.save(kPIVillageQuota);
        return Result.ofUpdateSuccess(kPIVillageQuota.convert(KPIVillageQuotaVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "村考核指标id", required = true) @PathVariable String id) {
        kPIVillageQuotaService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<KPIVillageQuotaVO>> list(@ApiParam(value = "村考核指标查询条件", required = true) @RequestBody KPIVillageQuotaSearchable kPIVillageQuotaSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<KPIVillageQuota> kPIVillageQuotaList = kPIVillageQuotaService.findAll(kPIVillageQuotaSearchable, sort);
        List<KPIVillageQuotaVO> kPIVillageQuotaVOList = KPIVillageQuota.convert(kPIVillageQuotaList, KPIVillageQuotaVO.class);
        return Result.of(kPIVillageQuotaVOList);
    }

    @Override
    public Result<Page<KPIVillageQuotaVO>> page(@ApiParam(value = "村考核指标查询条件", required = true) @RequestBody KPIVillageQuotaSearchable kPIVillageQuotaSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<KPIVillageQuota> kPIVillageQuotaPage = kPIVillageQuotaService.findAll(kPIVillageQuotaSearchable, pageable);
        Page<KPIVillageQuotaVO> kPIVillageQuotaVOPage = KPIVillageQuota.convert(kPIVillageQuotaPage, KPIVillageQuotaVO.class);
        return Result.of(kPIVillageQuotaVOPage);
    }

    @GetMapping("/loadVillageAllQuota")
    public Result<Map<String, Object>> loadAllVillageAllQuota(@NonNull String districtId, @NonNull String taskId) {
        Map<String, Object> res = new HashMap<>();
        res.put("commonWork", kPIVillageQuotaService.buildCommonWorkData(districtId, taskId));
        res.put("villageActual", kPIVillageQuotaService.buildCommonData(districtId, taskId, "02"));
        res.put("judgement", kPIVillageQuotaService.buildCommonData(districtId, taskId, "04"));
        res.put("watchQuota", kPIVillageQuotaService.buildWatchQuotaData(districtId, taskId));
        res.put("comment", kPIVillageQuotaService.buildCommentQuotaData(districtId, taskId));
        res.put("plusMinus", kPIVillageQuotaService.buildCommonData(districtId, taskId, "06"));
        return Result.of(res);
    }
}