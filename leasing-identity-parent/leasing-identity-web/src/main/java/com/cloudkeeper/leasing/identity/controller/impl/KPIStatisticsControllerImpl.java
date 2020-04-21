package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.controller.KPIStatisticsController;
import com.cloudkeeper.leasing.identity.domain.KPIStatistics;
import com.cloudkeeper.leasing.identity.dto.kpistatistics.KPIStatisticsDTO;
import com.cloudkeeper.leasing.identity.dto.kpistatistics.KPIStatisticsSearchable;
import com.cloudkeeper.leasing.identity.service.KPIStatisticsService;
import com.cloudkeeper.leasing.identity.service.SysDistrictService;
import com.cloudkeeper.leasing.identity.vo.DistrictsVo;
import com.cloudkeeper.leasing.identity.vo.KPIStatisticsVO;
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
    public Result<List<KPIStatisticsVO>> list(@ApiParam(value = "双向印证查询条件", required = true) @RequestBody KPIStatisticsSearchable kPIStatisticsSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<KPIStatistics> kPIStatisticsList = kPIStatisticsService.findAll(kPIStatisticsSearchable, sort);
        List<KPIStatisticsVO> kPIStatisticsVOList = KPIStatistics.convert(kPIStatisticsList, KPIStatisticsVO.class);
        for (KPIStatisticsVO k : kPIStatisticsVOList){
            k.setMargin1(Math.abs(Integer.valueOf(k.getVillagePerformance()) - Integer.valueOf(k.getMonitoringIndex())));
            k.setMargin2(Math.abs(Integer.valueOf(k.getDvm()) - Integer.valueOf(k.getAbilityJudgement())));
            k.setMargin3(Math.abs(Integer.valueOf(k.getRoutine()) - Integer.valueOf(k.getComprehensiveEvaluation())));
            k.setMargin4(Math.abs(Integer.valueOf(k.getComprehensiveEvaluationABC()) - Integer.valueOf(k.getSatisfactionDegree())));
        }
        return Result.of(kPIStatisticsVOList);
    }

    @Override
    public Result<Page<KPIStatisticsVO>> page(@ApiParam(value = "双向印证查询条件", required = true) @RequestBody KPIStatisticsSearchable kPIStatisticsSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<KPIStatistics> kPIStatisticsPage = kPIStatisticsService.findAll(kPIStatisticsSearchable, pageable);
        Page<KPIStatisticsVO> kPIStatisticsVOPage = KPIStatistics.convert(kPIStatisticsPage, KPIStatisticsVO.class);
        return Result.of(kPIStatisticsVOPage);
    }

    @Override
    public Result<Boolean> init() {
        //总排名
        //String sql = "SELECT cast( row_number()over(ORDER BY t.val) as int) AS val,t.districtName as districtName,t.districtId as districtId FROM (SELECT SUM(CAST (score AS float)) as val,districtName,districtId FROM KPI_Village_Statistics WHERE parentQuotaId = '0' GROUP BY districtName,districtId) t";

        //村级实绩排名
        String sql = "SELECT cast( RANK()over(ORDER BY t.val) as int) AS val,t.districtName as districtName,t.districtId as districtId FROM (SELECT SUM(CAST (score AS float)) as val,districtName,districtId FROM KPI_Village_Statistics WHERE parentQuotaId = '0' AND quotaId = '02' GROUP BY districtName,districtId) t";
        List<DistrictsVo> sysDistricts = sysDistrictService.findAllBySql(DistrictsVo.class,sql);
        for (DistrictsVo d : sysDistricts){
            KPIStatisticsSearchable kPIStatisticsSearchable = new KPIStatisticsSearchable();
            kPIStatisticsSearchable.setDistrictId(d.getDistrictId());
            kPIStatisticsSearchable.setDistrictName(d.getDistrictName());
            List<KPIStatistics> kPIStatisticsList = kPIStatisticsService.findAll(kPIStatisticsSearchable);
            KPIStatistics k = kPIStatisticsList.get(0);
            k.setVillagePerformance(d.getVal().toString());
            kPIStatisticsService.save(k);
        }

        //村级实绩排名
        String sql1 = "SELECT cast( RANK()over(ORDER BY t.val) as int) AS val,t.districtName as districtName,t.districtId as districtId FROM (SELECT SUM(CAST (score AS float)) as val,districtName,districtId FROM KPI_Village_Statistics WHERE parentQuotaId = '0' AND quotaId = '03' GROUP BY districtName,districtId) t";
        List<DistrictsVo> sysDistricts1 = sysDistrictService.findAllBySql(DistrictsVo.class,sql1);
        for (DistrictsVo d : sysDistricts1){
            KPIStatisticsSearchable kPIStatisticsSearchable = new KPIStatisticsSearchable();
            kPIStatisticsSearchable.setDistrictId(d.getDistrictId());
            kPIStatisticsSearchable.setDistrictName(d.getDistrictName());
            List<KPIStatistics> kPIStatisticsList = kPIStatisticsService.findAll(kPIStatisticsSearchable);
            KPIStatistics k = kPIStatisticsList.get(0);
            k.setMonitoringIndex(d.getVal().toString());
            kPIStatisticsService.save(k);
        }

        //日常工作村级实绩监测指标排名
        String sql2 = "SELECT cast( RANK()over(ORDER BY t.val) as int) AS val,t.districtName as districtName,t.districtId as districtId FROM (SELECT SUM(CAST (score AS float)) as val,districtName,districtId FROM KPI_Village_Statistics WHERE parentQuotaId = '0' AND (quotaId = '01' or quotaId = '02'or quotaId = '03') GROUP BY districtName,districtId) t";
        List<DistrictsVo> sysDistricts2 = sysDistrictService.findAllBySql(DistrictsVo.class,sql2);
        for (DistrictsVo d : sysDistricts2){
            KPIStatisticsSearchable kPIStatisticsSearchable = new KPIStatisticsSearchable();
            kPIStatisticsSearchable.setDistrictId(d.getDistrictId());
            kPIStatisticsSearchable.setDistrictName(d.getDistrictName());
            List<KPIStatistics> kPIStatisticsList = kPIStatisticsService.findAll(kPIStatisticsSearchable);
            KPIStatistics k = kPIStatisticsList.get(0);
            k.setDvm(d.getVal().toString());
            kPIStatisticsService.save(k);
        }

        //能力研判排名
        String sql3 = "SELECT cast( RANK()over(ORDER BY t.val) as int) AS val,t.districtName as districtName,t.districtId as districtId FROM (SELECT SUM(CAST (score AS float)) as val,districtName,districtId FROM KPI_Village_Statistics WHERE quotaId = '0402' GROUP BY districtName,districtId) t";
        List<DistrictsVo> sysDistricts3 = sysDistrictService.findAllBySql(DistrictsVo.class,sql3);
        for (DistrictsVo d : sysDistricts3){
            KPIStatisticsSearchable kPIStatisticsSearchable = new KPIStatisticsSearchable();
            kPIStatisticsSearchable.setDistrictId(d.getDistrictId());
            kPIStatisticsSearchable.setDistrictName(d.getDistrictName());
            List<KPIStatistics> kPIStatisticsList = kPIStatisticsService.findAll(kPIStatisticsSearchable);
            KPIStatistics k = kPIStatisticsList.get(0);
            k.setAbilityJudgement(d.getVal().toString());
            kPIStatisticsService.save(k);
        }
        //日常工作
        String sql4 = "SELECT cast( RANK()over(ORDER BY t.val) as int) AS val,t.districtName as districtName,t.districtId as districtId FROM (SELECT SUM(CAST (score AS float)) as val,districtName,districtId FROM KPI_Village_Statistics WHERE parentQuotaId = '0' AND quotaId = '01' GROUP BY districtName,districtId) t";
        List<DistrictsVo> sysDistricts4 = sysDistrictService.findAllBySql(DistrictsVo.class,sql4);
        for (DistrictsVo d : sysDistricts4){
            KPIStatisticsSearchable kPIStatisticsSearchable = new KPIStatisticsSearchable();
            kPIStatisticsSearchable.setDistrictId(d.getDistrictId());
            kPIStatisticsSearchable.setDistrictName(d.getDistrictName());
            List<KPIStatistics> kPIStatisticsList = kPIStatisticsService.findAll(kPIStatisticsSearchable);
            KPIStatistics k = kPIStatisticsList.get(0);
            k.setRoutine(d.getVal().toString());
            kPIStatisticsService.save(k);
        }

        //综合评议
        String sql5 = "SELECT cast( RANK()over(ORDER BY t.val) as int) AS val,t.districtName as districtName,t.districtId as districtId FROM (SELECT SUM(CAST (score AS float)) as val,districtName,districtId FROM KPI_Village_Statistics WHERE parentQuotaId = '0' AND quotaId = '05' GROUP BY districtName,districtId) t";
        List<DistrictsVo> sysDistricts5 = sysDistrictService.findAllBySql(DistrictsVo.class,sql5);
        for (DistrictsVo d : sysDistricts5){
            KPIStatisticsSearchable kPIStatisticsSearchable = new KPIStatisticsSearchable();
            kPIStatisticsSearchable.setDistrictId(d.getDistrictId());
            kPIStatisticsSearchable.setDistrictName(d.getDistrictName());
            List<KPIStatistics> kPIStatisticsList = kPIStatisticsService.findAll(kPIStatisticsSearchable);
            KPIStatistics k = kPIStatisticsList.get(0);
            k.setComprehensiveEvaluation(d.getVal().toString());
            kPIStatisticsService.save(k);
        }
        //综合评议镇
        String sql6 = "SELECT cast( RANK()over(ORDER BY t.val) as int) AS val,t.districtName as districtName,t.districtId as districtId FROM (SELECT SUM(CAST (score AS float)) as val,districtName,districtId FROM KPI_Village_Statistics WHERE quotaId = '0501' GROUP BY districtName,districtId) t";
        List<DistrictsVo> sysDistricts6 = sysDistrictService.findAllBySql(DistrictsVo.class,sql6);
        for (DistrictsVo d : sysDistricts6){
            KPIStatisticsSearchable kPIStatisticsSearchable = new KPIStatisticsSearchable();
            kPIStatisticsSearchable.setDistrictId(d.getDistrictId());
            kPIStatisticsSearchable.setDistrictName(d.getDistrictName());
            List<KPIStatistics> kPIStatisticsList = kPIStatisticsService.findAll(kPIStatisticsSearchable);
            KPIStatistics k = kPIStatisticsList.get(0);
            k.setComprehensiveEvaluationABC(d.getVal().toString());
            kPIStatisticsService.save(k);
        }
        //满意度
        String sql7 = "SELECT cast( RANK()over(ORDER BY t.val) as int) AS val,t.districtName as districtName,t.districtId as districtId FROM (SELECT SUM(CAST (score AS float)) as val,districtName,districtId FROM KPI_Village_Statistics WHERE quotaId = '0503' GROUP BY districtName,districtId) t";
        List<DistrictsVo> sysDistricts7 = sysDistrictService.findAllBySql(DistrictsVo.class,sql7);
        for (DistrictsVo d : sysDistricts7){
            KPIStatisticsSearchable kPIStatisticsSearchable = new KPIStatisticsSearchable();
            kPIStatisticsSearchable.setDistrictId(d.getDistrictId());
            kPIStatisticsSearchable.setDistrictName(d.getDistrictName());
            List<KPIStatistics> kPIStatisticsList = kPIStatisticsService.findAll(kPIStatisticsSearchable);
            KPIStatistics k = kPIStatisticsList.get(0);
            k.setSatisfactionDegree(d.getVal().toString());
            kPIStatisticsService.save(k);
        }
        return new Result<>();
    }

}
