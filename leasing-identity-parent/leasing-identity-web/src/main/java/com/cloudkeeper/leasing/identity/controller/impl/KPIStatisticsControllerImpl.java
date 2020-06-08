package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.KPIStatisticsController;
import com.cloudkeeper.leasing.identity.domain.CadreTask;
import com.cloudkeeper.leasing.identity.domain.KPIStatistics;
import com.cloudkeeper.leasing.identity.domain.KPIVillageStatistics;
import com.cloudkeeper.leasing.identity.domain.SysDistrict;
import com.cloudkeeper.leasing.identity.dto.kpistatistics.KPIStatisticsDTO;
import com.cloudkeeper.leasing.identity.dto.kpistatistics.KPIStatisticsSearchable;
import com.cloudkeeper.leasing.identity.service.CadreTaskService;
import com.cloudkeeper.leasing.identity.service.KPIStatisticsService;
import com.cloudkeeper.leasing.identity.service.KPIVillageStatisticsService;
import com.cloudkeeper.leasing.identity.service.SysDistrictService;
import com.cloudkeeper.leasing.identity.vo.DistrictsVo;
import com.cloudkeeper.leasing.identity.vo.DoubleVerityVO;
import com.cloudkeeper.leasing.identity.vo.KPIStatisticsVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 双向印证 controller
 * @author yujian
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KPIStatisticsControllerImpl implements KPIStatisticsController {

    /** 双向印证 service */
    private final KPIStatisticsService kPIStatisticsService;

    /** 组织 service */
    private final SysDistrictService sysDistrictService;

    private final CadreTaskService cadreTaskService;

    private final KPIVillageStatisticsService kpiVillageStatisticsService;

    @Override
    public Result<KPIStatisticsVO> findOne(@ApiParam(value = "双向印证id", required = true) @PathVariable String id) {
        Optional<KPIStatistics> kPIStatisticsOptional = kPIStatisticsService.findOptionalById(id);
        return kPIStatisticsOptional.map(kPIStatistics -> Result.of(kPIStatistics.convert(KPIStatisticsVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<KPIStatisticsVO> add(@ApiParam(value = "双向印证 DTO", required = true) @RequestBody @Validated KPIStatisticsDTO kPIStatisticsDTO) {
        KPIStatistics kPIStatistics = kPIStatisticsService.save(kPIStatisticsDTO.convert(KPIStatistics.class));
        return Result.ofAddSuccess(kPIStatistics.convert(KPIStatisticsVO.class));
    }

    @Override
    public Result<KPIStatisticsVO> update(@ApiParam(value = "双向印证id", required = true) @PathVariable String id,
        @ApiParam(value = "双向印证 DTO", required = true) @RequestBody @Validated KPIStatisticsDTO kPIStatisticsDTO) {
        Optional<KPIStatistics> kPIStatisticsOptional = kPIStatisticsService.findOptionalById(id);
        if (!kPIStatisticsOptional.isPresent()) {
            return Result.ofLost();
        }
        KPIStatistics kPIStatistics = kPIStatisticsOptional.get();
        BeanUtils.copyProperties(kPIStatisticsDTO, kPIStatistics);
        kPIStatistics = kPIStatisticsService.save(kPIStatistics);
        return Result.ofUpdateSuccess(kPIStatistics.convert(KPIStatisticsVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "双向印证id", required = true) @PathVariable String id) {
        kPIStatisticsService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<Map> list(@ApiParam(value = "双向印证查询条件", required = true) @RequestBody KPIStatisticsSearchable kPIStatisticsSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        if (StringUtils.isEmpty(kPIStatisticsSearchable.getTaskYear())) {
            return Result.of(200, "任务年度不存在！");
        }
        CadreTask task = cadreTaskService.getCurrentTaskByType("综合年度考核", kPIStatisticsSearchable.getTaskYear(), null);
        if (task == null) {
            return Result.of(200, "年度任务不存在！");
        }
        kPIStatisticsSearchable.setTaskId(task.getId());

        DoubleVerityVO city = kpiVillageStatisticsService.findBySql(DoubleVerityVO.class, generateAvgSqlByDistrictId("01", task.getId()));

        DoubleVerityVO town = kpiVillageStatisticsService.findBySql(DoubleVerityVO.class, generateAvgSqlByDistrictId(kPIStatisticsSearchable.getDistrictId(), task.getId()));

        List<KPIStatistics> kPIStatisticsList = kPIStatisticsService.findAll(kPIStatisticsSearchable, sort);
        List<KPIStatisticsVO> kPIStatisticsVOList = KPIStatistics.convert(kPIStatisticsList, KPIStatisticsVO.class);
        for (KPIStatisticsVO k : kPIStatisticsVOList){
            k.setVillagePerMonitorIndexDistance(Math.abs(Double.valueOf(k.getVillagePerformance()) - Double.valueOf(k.getMonitoringIndex())));
            k.setDvmAndAbilityJudgementDistance(Math.abs(Double.valueOf(k.getDvm()) - Double.valueOf(k.getAbilityJudgement())));
            k.setComprehensiveEvaluationAndCommonWorkDistance(Math.abs(Double.valueOf(k.getRoutine()) - Double.valueOf(k.getComprehensiveEvaluation())));
        }

        Map<String, Object> res = new HashMap<>();
        res.put("city", city);
        res.put("town", town);
        res.put("data", kPIStatisticsVOList);
        return Result.of(res);
    }

    private String generateAvgSqlByDistrictId(@Nonnull String districtId, @Nonnull String taskId) {
        String avgSql = "SELECT Round(avg(villagePerMonitorIndexDistance), 2) as villagePerMonitorIndexDistanceAvg, Round(avg(dvmAndAbilityJudgementDistance),2) as dvmAndAbilityJudgementDistanceAvg, \n" +
                "Round(avg(comprehensiveEvaluationAndCommonWorkDistance),2) as comprehensiveEvaluationAndCommonWorkDistanceAvg from (\n" +
                "SELECT ROUND(ABS(cast(isNUll(ks.villagePerformance, 0) as float) - cast(isnull(ks.monitoringIndex, 0) as float)), 2) as villagePerMonitorIndexDistance, \n" +
                "ROUND(ABS(cast(isNUll(ks.dvm, 0) as float) - cast(isnull(ks.abilityJudgement, 0) as float)), 2) as dvmAndAbilityJudgementDistance,\n" +
                "ROUND(ABS(cast(isNUll(ks.comprehensiveEvaluation, 0) as float) - cast(isnull(ks.routine, 0) as float)), 2) as comprehensiveEvaluationAndCommonWorkDistance,\n" +
                "ks.*  FROM KPI_Statistics ks WHERE ks.taskId = '" + taskId+ "' and ks.districtId like '" + districtId+ "%') a ";
        return avgSql;
    }

    @Override
    public Result<Page<KPIStatisticsVO>> page(@ApiParam(value = "双向印证查询条件", required = true) @RequestBody KPIStatisticsSearchable kPIStatisticsSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<KPIStatistics> kPIStatisticsPage = kPIStatisticsService.findAll(kPIStatisticsSearchable, pageable);
        Page<KPIStatisticsVO> kPIStatisticsVOPage = KPIStatistics.convert(kPIStatisticsPage, KPIStatisticsVO.class);
        return Result.of(kPIStatisticsVOPage);
    }

    private void updateDoubleVerify(String taskId, CadreTask byId) {

        kPIStatisticsService.deleteAllByTaskId(taskId);

        List<SysDistrict> villages = sysDistrictService.findAllVillages();
        Map<String, KPIStatistics> tempRes = new HashMap<>();
        for (SysDistrict item : villages) {
            KPIStatistics kpiStatistics = new KPIStatistics();
            kpiStatistics.setTaskId(taskId);
            kpiStatistics.setDistrictId(item.getDistrictId());
            kpiStatistics.setDistrictName(item.getDistrictName());
            tempRes.put(item.getDistrictId(), kpiStatistics);
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPIVillageStatistics.class);
        detachedCriteria.add(Restrictions.eq("taskId", taskId));
        detachedCriteria.add(Restrictions.eq("quotaLevel", "1"));
        List<KPIVillageStatistics> all = kpiVillageStatisticsService.findAll(detachedCriteria);

        Map<String, Double> dvb = new HashMap<>();
        for (KPIVillageStatistics item : all) {
            BigDecimal score = BigDecimal.ZERO;
            if (!StringUtils.isEmpty(item.getScore())) {
                score = new BigDecimal(item.getScore());
            }
            KPIStatistics kpiStatistics = tempRes.get(item.getDistrictId());
            String quotaId = item.getQuotaId();
            if (!dvb.containsKey(item.getDistrictId())) {
                dvb.put(item.getDistrictId(), 0.00);
            }
            Double aDouble = dvb.get(item.getDistrictId());
            if (quotaId.equals(byId.getTaskYear() + "02")) {
                aDouble += score.doubleValue();
                kpiStatistics.setVillagePerformance(score.divide(new BigDecimal(40),2, RoundingMode.FLOOR)
                        .multiply(new BigDecimal(100)).toString());
            } else if (quotaId.equals(byId.getTaskYear() + "03")) {
                aDouble += score.doubleValue();
                kpiStatistics.setMonitoringIndex(score.divide(new BigDecimal(20),2, RoundingMode.FLOOR)
                        .multiply(new BigDecimal(100)).toString());
            } else if (quotaId.equals(byId.getTaskYear() + "01")) {
                aDouble += score.doubleValue();
                kpiStatistics.setRoutine(score.divide(new BigDecimal(10),2, RoundingMode.FLOOR)
                        .multiply(new BigDecimal(100)).toString());
            } else if (quotaId.equals(byId.getTaskYear() + "05")) {
                kpiStatistics.setComprehensiveEvaluation(score.divide(new BigDecimal(20),2, RoundingMode.FLOOR)
                        .multiply(new BigDecimal(100)).toString());
            }
            dvb.put(item.getDistrictId(), aDouble);
        }

        DetachedCriteria detachedCriteria1 = DetachedCriteria.forClass(KPIVillageStatistics.class);
        detachedCriteria1.add(Restrictions.eq("taskId", taskId));
        detachedCriteria1.add(Restrictions.eq("quotaLevel", "2"));
        detachedCriteria1.add(Restrictions.eq("quotaId", byId.getTaskYear() + "0401"));
        all = kpiVillageStatisticsService.findAll(detachedCriteria1);

        for (KPIVillageStatistics item : all) {
            BigDecimal score = BigDecimal.ZERO;
            if (!StringUtils.isEmpty(item.getScore())) {
                score = new BigDecimal(item.getScore());
            }
            KPIStatistics kpiStatistics = tempRes.get(item.getDistrictId());
            kpiStatistics.setAbilityJudgement(score.divide(new BigDecimal(5),2, RoundingMode.FLOOR)
                    .multiply(new BigDecimal(100)).toString());
            BigDecimal dvm = new BigDecimal(dvb.get(item.getDistrictId()));
            kpiStatistics.setDvm(dvm.divide(new BigDecimal(40 + 10 + 20), 2, RoundingMode.FLOOR)
                    .multiply(new BigDecimal(100)).toString());
        }

        DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(KPIVillageStatistics.class);
        detachedCriteria2.add(Restrictions.eq("taskId", taskId));
        detachedCriteria2.add(Restrictions.eq("quotaLevel", "2"));
        detachedCriteria2.add(Restrictions.eq("quotaId", byId.getTaskYear() + "0501"));
        all = kpiVillageStatisticsService.findAll(detachedCriteria2);

        for (KPIVillageStatistics item : all) {
            BigDecimal score = BigDecimal.ZERO;
            if (!StringUtils.isEmpty(item.getScore())) {
                score = new BigDecimal(item.getScore());
            }
            KPIStatistics kpiStatistics = tempRes.get(item.getDistrictId());
            kpiStatistics.setComprehensiveEvaluationABC(score.divide(new BigDecimal(10),2, RoundingMode.FLOOR)
                    .multiply(new BigDecimal(100)).toString());
        }

        DetachedCriteria detachedCriteria3 = DetachedCriteria.forClass(KPIVillageStatistics.class);
        detachedCriteria3.add(Restrictions.eq("taskId", taskId));
        detachedCriteria3.add(Restrictions.eq("quotaLevel", "2"));
        detachedCriteria3.add(Restrictions.eq("quotaId", byId.getTaskYear() + "0502"));
        all = kpiVillageStatisticsService.findAll(detachedCriteria3);

        for (KPIVillageStatistics item : all) {
            BigDecimal score = BigDecimal.ZERO;
            if (!StringUtils.isEmpty(item.getScore())) {
                score = new BigDecimal(item.getScore());
            }
            KPIStatistics kpiStatistics = tempRes.get(item.getDistrictId());
            kpiStatistics.setSatisfactionDegree(score.divide(new BigDecimal(10),2, RoundingMode.FLOOR)
                    .multiply(new BigDecimal(100)).toString());
        }

        for (KPIStatistics item : tempRes.values()) {
            kPIStatisticsService.save(item);
        }
    }

    @GetMapping("/updateDoubleVerify")
    @Transactional
    public Result<Boolean> updateDoubleVerify(@Nonnull String taskYear) {
        CadreTask byId = cadreTaskService.getCurrentTaskByType("综合年度考核", taskYear, null);
        if (byId == null) {
            return Result.of(200, "当前任务不存在！", null);
        }

        // 双验证数据更新
        this.updateDoubleVerify(byId.getId(), byId);

        return Result.of(200, true);
    }

    @Override
    @Transactional
    public Result<Boolean> generateKpiResult(@Nonnull String taskId) {
        CadreTask byId = cadreTaskService.findById(taskId);
        if (byId == null) {
            return Result.of(200, "当前任务不存在！", null);
        }
        String hasGenerateResult = byId.getHasGenerateResult();
        if ("1".equals(hasGenerateResult)) {
            return Result.of(200, "当前任务考核结果已经生成！", null);
        }

        kpiVillageStatisticsService.generateVillageStatistic(taskId);

        // 双验证数据更新
        this.updateDoubleVerify(taskId, byId);

        cadreTaskService.updateResultStatus(taskId);

        return Result.of(200, true);
    }

}
