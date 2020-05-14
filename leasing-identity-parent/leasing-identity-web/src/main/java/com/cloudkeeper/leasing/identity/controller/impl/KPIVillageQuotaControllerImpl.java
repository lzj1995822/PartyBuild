package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.KPIVillageQuotaController;
import com.cloudkeeper.leasing.identity.domain.*;
import com.cloudkeeper.leasing.identity.dto.kpievaluation.KPIEvaluationDTO;
import com.cloudkeeper.leasing.identity.dto.kpivillagequota.KPIVillageQuotaDTO;
import com.cloudkeeper.leasing.identity.dto.kpivillagequota.KPIVillageQuotaSearchable;
import com.cloudkeeper.leasing.identity.dto.kpivillagequota.ScoringDTO;
import com.cloudkeeper.leasing.identity.service.*;
import com.cloudkeeper.leasing.identity.vo.KPIVillageQuotaVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.*;

/**
 * 村考核指标 controller
 * @author yujian
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KPIVillageQuotaControllerImpl implements KPIVillageQuotaController {

    /** 村考核指标 service */
    private final KPIVillageQuotaService kPIVillageQuotaService;

    /** 考核指标附加 service */
    private final DetectionIndexService detectionIndexService;

    private final KPIEvaluationService kpiEvaluationService;

    private final CadreTaskService cadreTaskService;

    private final KpiQuotaService kpiQuotaService;

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
    public Result<List<Map<String, Object>>> loadAllVillageAllQuota(@NonNull String districtId, @NonNull String taskId,  @NonNull String taskYear) {
        CadreTask byId = cadreTaskService.findById(taskId);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CadreTask.class);
        detachedCriteria.add(Restrictions.eq("taskYear", byId.getTaskYear()));
        detachedCriteria.add(Restrictions.eq("type", "考核指标内容制定"));
        List<CadreTask> all = cadreTaskService.findAll(detachedCriteria);
        if (all.size() == 0) {
            return Result.of(500, "对应考核指标任务不存在！");
        }
        List<Map<String, Object>> res = new ArrayList<>();
        taskId = all.get(0).getId();
        for (int i = 1; i < 7; i ++) {
            String quotaId = taskYear + "0" + i;
            Map<String, Object> temps = new HashMap<>();
            KpiQuota byQuotaId = kpiQuotaService.findByQuotaId(quotaId);
            List<Map<String, Object>> maps = new LinkedList<>();
            if (i == 1) {
                maps = kPIVillageQuotaService.buildCommonWorkData(districtId, taskId, quotaId, null);
            } else if (i == 3) {
                maps = kPIVillageQuotaService.buildWatchQuotaData(districtId, taskId, quotaId);
            } else if (i == 5) {
                maps = kPIVillageQuotaService.buildCommentQuotaData(districtId, taskId, quotaId);
            } else {
                maps = kPIVillageQuotaService.buildCommonData(districtId, taskId, quotaId);
            }
            temps.put("quotaId", quotaId);
            temps.put("quotaName", byQuotaId.getQuotaName());
            temps.put("quotaScore", byQuotaId.getQuotaScore());
            temps.put("isSetWeight", byQuotaId.getIsSetWeight());
            temps.put("kpiQuotas", maps);
            res.add(temps);
        }
        return Result.of(res);
    }

    @GetMapping("/getAllByTownQuotaId")
    public Result<List<KPIVillageQuotaVO>> loadAllVillageAllQuota(@NonNull String townQuotaId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPIVillageQuota.class);
        detachedCriteria.add(Restrictions.eq("townQuotaId", townQuotaId));
        detachedCriteria.addOrder(Order.asc("districtId"));
        List<KPIVillageQuota> all = kPIVillageQuotaService.findAll(detachedCriteria);
        List<KPIVillageQuotaVO> convert = KPIVillageQuota.convert(all, KPIVillageQuotaVO.class);
        return Result.of(convert);
    }

    @PostMapping("/scoring")
    @Transactional
    public Result scoring(@Nonnull String taskId,@Nonnull @RequestBody List<ScoringDTO> scoringDTOS) {
        for (ScoringDTO item : scoringDTOS) {
            String score = item.getScore();
            String weight = item.getWeight();
            BigDecimal multiply = new BigDecimal(score).multiply(new BigDecimal(weight));
            kPIVillageQuotaService.updateScoreById(score, multiply.toString(), item.getId());
            DetectionIndex detectionIndex = detectionIndexService.findByDistrictIdAndTaskId(item.getDistrictId(), taskId);
            if (detectionIndex == null) {
                detectionIndex = new DetectionIndex();
            }
            String[] nullPropertyNames = getNullPropertyNames(item);
            List<String> strings = Arrays.asList(nullPropertyNames);
            List<String> ignoreProperties = new ArrayList<>(strings);
            ignoreProperties.add("id");
            BeanUtils.copyProperties(item, detectionIndex, ignoreProperties.toArray(nullPropertyNames));
            detectionIndexService.save(detectionIndex);

            if (item.getKpiEvaluations() == null) {
                continue;
            }
            for (KPIEvaluationDTO subItem : item.getKpiEvaluations()) {
                subItem.setVillageQuotaId(item.getId());
                kpiEvaluationService.save(subItem.convert(KPIEvaluation.class));
            }
        }
        return Result.of(200,"打分成功！");
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}