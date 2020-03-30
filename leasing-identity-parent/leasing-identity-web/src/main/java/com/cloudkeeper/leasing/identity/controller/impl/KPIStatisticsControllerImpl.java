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
        String sql = "SELECT districtId,districtName FROM SYS_District WHERE districtType = 'Party' and districtLevel = '3' order by districtId desc";
        List<DistrictsVo> sysDistricts = sysDistrictService.findAllBySql(DistrictsVo.class,sql);
        for (DistrictsVo d : sysDistricts){
            KPIStatistics kpiStatistics = new KPIStatistics();
            kpiStatistics.setDistrictId(d.getDistrictId());
            kpiStatistics.setDistrictName(d.getDistrictName());
            kpiStatistics.setAbilityJudgement(String.valueOf((int) (Math.random() * 100000) % 100));
            kpiStatistics.setComprehensiveEvaluation(String.valueOf((int) (Math.random() * 100000) % 100));
            kpiStatistics.setComprehensiveEvaluationABC(String.valueOf((int) (Math.random() * 100000) % 100));
            kpiStatistics.setDvm(String.valueOf((int) (Math.random() * 100000) % 100));
            kpiStatistics.setMonitoringIndex(String.valueOf((int) (Math.random() * 100000) % 100));
            kpiStatistics.setRoutine(String.valueOf((int) (Math.random() * 100000) % 100));
            kpiStatistics.setSatisfactionDegree(String.valueOf((int) (Math.random() * 100000) % 100));
            kpiStatistics.setVillagePerformance(String.valueOf((int) (Math.random() * 100000) % 100));
            kPIStatisticsService.save(kpiStatistics);
        }
        return null;
    }

}
