package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.config.ExcelUtils;
import com.cloudkeeper.leasing.identity.controller.KPIVillageStatisticsController;
import com.cloudkeeper.leasing.identity.domain.CadreTask;
import com.cloudkeeper.leasing.identity.domain.KPITownQuota;
import com.cloudkeeper.leasing.identity.domain.KPIVillageQuota;
import com.cloudkeeper.leasing.identity.domain.KPIVillageStatistics;
import com.cloudkeeper.leasing.identity.dto.kpivillagestatistics.KPIVillageStatisticsDTO;
import com.cloudkeeper.leasing.identity.dto.kpivillagestatistics.KPIVillageStatisticsSearchable;
import com.cloudkeeper.leasing.identity.service.CadreTaskService;
import com.cloudkeeper.leasing.identity.service.KPITownQuotaService;
import com.cloudkeeper.leasing.identity.service.KPIVillageStatisticsService;
import com.cloudkeeper.leasing.identity.vo.ImportVO;
import com.cloudkeeper.leasing.identity.vo.KPIVillageStatisticsVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsStringVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nonnull;
import javax.management.Query;
import java.util.*;

/**
 * 村一级指标统计 controller
 * @author yujian
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KPIVillageStatisticsControllerImpl implements KPIVillageStatisticsController {

    /** 村一级指标统计 service */
    private final KPIVillageStatisticsService kPIVillageStatisticsService;

    private final KPITownQuotaService kPITownQuotaService;

    private final CadreTaskService cadreTaskService;

    @Override
    public Result<KPIVillageStatisticsVO> findOne(@ApiParam(value = "村一级指标统计id", required = true) @PathVariable String id) {
        Optional<KPIVillageStatistics> kPIVillageStatisticsOptional = kPIVillageStatisticsService.findOptionalById(id);
        return kPIVillageStatisticsOptional.map(kPIVillageStatistics -> Result.of(kPIVillageStatistics.convert(KPIVillageStatisticsVO.class))).orElseGet(Result::ofNotFound);
    }

    @Override
    public Result<KPIVillageStatisticsVO> add(@ApiParam(value = "村一级指标统计 DTO", required = true) @RequestBody @Validated KPIVillageStatisticsDTO kPIVillageStatisticsDTO) {
        KPIVillageStatistics kPIVillageStatistics = kPIVillageStatisticsService.save(kPIVillageStatisticsDTO.convert(KPIVillageStatistics.class));
        return Result.ofAddSuccess(kPIVillageStatistics.convert(KPIVillageStatisticsVO.class));
    }

    @Override
    public Result<KPIVillageStatisticsVO> update(@ApiParam(value = "村一级指标统计id", required = true) @PathVariable String id,
        @ApiParam(value = "村一级指标统计 DTO", required = true) @RequestBody @Validated KPIVillageStatisticsDTO kPIVillageStatisticsDTO) {
        Optional<KPIVillageStatistics> kPIVillageStatisticsOptional = kPIVillageStatisticsService.findOptionalById(id);
        if (!kPIVillageStatisticsOptional.isPresent()) {
            return Result.ofLost();
        }
        KPIVillageStatistics kPIVillageStatistics = kPIVillageStatisticsOptional.get();
        BeanUtils.copyProperties(kPIVillageStatisticsDTO, kPIVillageStatistics);
        kPIVillageStatistics = kPIVillageStatisticsService.save(kPIVillageStatistics);
        return Result.ofUpdateSuccess(kPIVillageStatistics.convert(KPIVillageStatisticsVO.class));
    }

    @Override
    public Result delete(@ApiParam(value = "村一级指标统计id", required = true) @PathVariable String id) {
        kPIVillageStatisticsService.deleteById(id);
        return Result.ofDeleteSuccess();
    }

    @Override
    public Result<List<KPIVillageStatisticsVO>> list(@ApiParam(value = "村一级指标统计查询条件", required = true) @RequestBody KPIVillageStatisticsSearchable kPIVillageStatisticsSearchable,
        @ApiParam(value = "排序条件", required = true) Sort sort) {
        List<KPIVillageStatistics> kPIVillageStatisticsList = kPIVillageStatisticsService.findAll(kPIVillageStatisticsSearchable, sort);
        List<KPIVillageStatisticsVO> kPIVillageStatisticsVOList = KPIVillageStatistics.convert(kPIVillageStatisticsList, KPIVillageStatisticsVO.class);
        return Result.of(kPIVillageStatisticsVOList);
    }

    @Override
    public Result<Page<KPIVillageStatisticsVO>> page(@ApiParam(value = "村一级指标统计查询条件", required = true) @RequestBody KPIVillageStatisticsSearchable kPIVillageStatisticsSearchable,
        @ApiParam(value = "分页参数", required = true) Pageable pageable) {
        Page<KPIVillageStatistics> kPIVillageStatisticsPage = kPIVillageStatisticsService.findAll(kPIVillageStatisticsSearchable, pageable);
        Page<KPIVillageStatisticsVO> kPIVillageStatisticsVOPage = KPIVillageStatistics.convert(kPIVillageStatisticsPage, KPIVillageStatisticsVO.class);
        return Result.of(kPIVillageStatisticsVOPage);
    }

    @Override
    public Result<Boolean> generateStatistics(String taskId) {
        if (kPIVillageStatisticsService.generateVillageStatistic(taskId)) {
            Result.of(200, true);
        }
        return Result.of(500, false);
    }

    @Override
    @Authorization(required = false)
    public Object importExcel(@RequestParam("file") MultipartFile file) {
        List<ImportVO> list = ExcelUtils.readExcel("", ImportVO.class, file);
        for (ImportVO importVO : list){
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPIVillageStatistics.class);
            detachedCriteria.add(Restrictions.eq("parentQuotaId","0"));
            detachedCriteria.add(Restrictions.like("districtName",importVO.getDistrictName().trim(), MatchMode.ANYWHERE));
            detachedCriteria.add(Restrictions.eq("quotaId","01"));
            List<KPIVillageStatistics> ls =  kPIVillageStatisticsService.findAll(detachedCriteria);
            if (CollectionUtils.isEmpty(ls)){
                System.out.println(importVO.getDistrictName().trim());
            }
            KPIVillageStatistics l = ls.get(0);
            l.setScore(importVO.getRcgz());
            kPIVillageStatisticsService.save(l);
        }
        for (ImportVO importVO : list){
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(KPIVillageStatistics.class);
            detachedCriteria.add(Restrictions.eq("parentQuotaId","0"));
            detachedCriteria.add(Restrictions.like("districtName",importVO.getDistrictName().trim(), MatchMode.ANYWHERE));
            detachedCriteria.add(Restrictions.eq("quotaId","02"));
            List<KPIVillageStatistics> ls =  kPIVillageStatisticsService.findAll(detachedCriteria);
            KPIVillageStatistics l = ls.get(0);
            l.setScore(importVO.getCjsj());
            kPIVillageStatisticsService.save(l);
        }
        return Result.of(list);
    }

    @Override
    public Result<Object> getStatistics(String taskYear) {
        String taskId = getCurrentTaskId(taskYear);
        if (taskId == null) {
            return Result.of(500, "当前年份对应的考核任务不存在！");
        }

        String top10Sql = "SELECT TOP 10 * FROM KPI_Village_Statistics kvs WHERE quotaLevel ='0' and taskId = '" +taskId+ "' ORDER BY cast(kvs.score as FLOAT) DESC";
        List<KPIVillageStatistics> top10 = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class, top10Sql);


        String last10Sql = "SELECT TOP 10 * FROM KPI_Village_Statistics kvs WHERE quotaLevel ='0' and taskId = '" +taskId+ "' ORDER BY cast(kvs.score as FLOAT) ASC";
        List<KPIVillageStatistics> last10 = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class, last10Sql);

        String lastGroupByTownSql = "SELECT\n" +
                "\t* \n" +
                "FROM\n" +
                "\tKPI_Village_Statistics kvs \n" +
                "WHERE\n" +
                "\tkvs.score IN ( SELECT MIN ( CAST ( score AS FLOAT ) ) AS score FROM KPI_Village_Statistics WHERE " +
                "quotaLevel = '0' and taskId = '" + taskId + "' GROUP BY parentDistrictId, parentDistrictName ) \n" +
                "ORDER BY\n" +
                "\tscore DESC";
        List<KPIVillageStatistics> lastGroupByTown = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class, lastGroupByTownSql);

        String top3ByQuotasSql = "SELECT * from (SELECT top 3 * from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '1' and quotaId = '" + taskYear+ "04' and taskId = '" +taskId+ "' ORDER BY cast(score as FLOAT) desc) as temp1\n" +
                "UNION ALL\n" +
                "SELECT * from (SELECT top 3 * from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '1' and quotaId = '" + taskYear+ "05' and taskId = '" +taskId+ "' ORDER BY cast(score as FLOAT) desc) as temp2\n" +
                "UNION ALL\n" +
                "SELECT * FROM (SELECT top 3 * from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '2' and quotaId = '" + taskYear+ "0301' and taskId = '" +taskId+ "' ORDER BY cast(score as FLOAT) desc) as temp3\n" +
                "UNION ALL\n" +
                "SELECT * FROM (SELECT top 3 * from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '2' and quotaId = '" + taskYear+ "0302' and taskId = '" +taskId+ "' ORDER BY cast(score as FLOAT) desc) as temp4\n" +
                "UNION ALL\n" +
                "SELECT * FROM (SELECT top 3 * from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '2' and quotaId = '" + taskYear+ "0303' and taskId = '" +taskId+ "' ORDER BY cast(score as FLOAT) desc) as temp5\n" +
                "UNION ALL\n" +
                "SELECT * FROM (SELECT top 3 * from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '2' and quotaId = '" + taskYear+ "0304' and taskId = '" +taskId+ "' ORDER BY cast(score as FLOAT) desc) as temp6\n" +
                "UNION ALL\n" +
                "SELECT * FROM (SELECT top 3 * from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '2' and quotaId = '" + taskYear+ "0305' and taskId = '" +taskId+ "' ORDER BY cast(score as FLOAT) desc) as temp7";
        List<KPIVillageStatistics> top3ByQuotas = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class, top3ByQuotasSql);

        Map<String, List<KPIVillageStatistics>> map = new HashMap<>();
        for (KPIVillageStatistics item : top3ByQuotas) {
            if (!map.containsKey(item.getQuotaName())) {
                map.put(item.getQuotaName(), new ArrayList<>());
            }
            List<KPIVillageStatistics> kpiVillageStatisticsList = map.get(item.getQuotaName());
            kpiVillageStatisticsList.add(item);
        }

        String allSql = "SELECT * FROM KPI_Village_Statistics kvs WHERE quotaLevel ='0' and taskId = '" +taskId+ "' ORDER BY cast(kvs.score as FLOAT) DESC";
        List<KPIVillageStatistics> allBySql = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class, allSql);

        List<KPIVillageStatistics> good = new ArrayList<>();
        List<KPIVillageStatistics> competent = new ArrayList<>();
        List<KPIVillageStatistics> basicCompetent = new ArrayList<>();
        for (KPIVillageStatistics item : allBySql) {
            if (Integer.valueOf(item.getRanking()) <= 24) {
                good.add(item);
            } else if (Integer.valueOf(item.getRanking()) <= 106) {
                competent.add(item);
            } else {
                basicCompetent.add(item);
            }
        }

        String incompetenceSql = "SELECT * FROM KPI_Village_Statistics kvs WHERE quotaLevel ='0' and CAST (kvs.score as FLOAT) > 0 and cast(kvs.score as FLOAT) < 60  ORDER BY kvs.score asc";
        List<KPIVillageStatistics> incompetence = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class, incompetenceSql);

        Map<String,Object> res = new HashMap<>();
        res.put("top10", top10);
        res.put("last10", last10);
        res.put("lastGroupByTown", lastGroupByTown);
        res.put("top3ByQuotas", map);
        res.put("good", good);
        res.put("competent", competent);
        res.put("basicCompetent", basicCompetent);
        res.put("incompetence", incompetence);
        return Result.of(res);
    }

    @Override
    public Result<Object> getStatisticsOnAverage(@PathVariable String quotaId) {
        String  sql = "SELECT (cast (CONVERT(decimal(18, 2),a.val * 1.0 / (SELECT count(1) FROM SYS_District WHERE parentName = a.name AND districtType = 'Party')) as VARCHAR)) val,a.name as name FROM (SELECT COUNT(1) AS val,parentDistrictName as name FROM KPI_Village_Statistics WHERE CAST (score as FLOAT) > (SELECT AVG(CAST (score as FLOAT)) FROM KPI_Village_Statistics  WHERE quotaId = '"+quotaId+"') AND quotaId = '"+quotaId+"' AND parentDistrictName != '广电测试镇党委' AND districtId != '-1' GROUP BY parentDistrictName) a";;
        if ("02".equals(quotaId)){
            sql = "SELECT (cast (count(1) as VARCHAR)) AS val,parentDistrictName as name FROM KPI_Village_Statistics WHERE CAST (score as FLOAT) > (SELECT AVG(CAST (score as FLOAT)) FROM KPI_Village_Statistics  WHERE quotaId = '02') AND quotaId = '02' AND parentDistrictName != '广电测试镇党委' AND districtId != '-1' GROUP BY parentDistrictName";
        }
        List<StatisticsStringVO> buchengzhi = kPIVillageStatisticsService.findAllBySql(StatisticsStringVO.class,sql);
        return Result.of(buchengzhi);
    }

    @Override
    public Result<List<KPIVillageStatistics>> getExcellent(String taskYear) {
        String currentTaskId = getCurrentTaskId(taskYear);
        if (currentTaskId == null) {
            return Result.of(500, "当前年份对应的考核任务不存在！");
        }
        String allSql = "SELECT * FROM KPI_Village_Statistics kvs WHERE quotaLevel ='0' and taskId = '" +currentTaskId+ "' ORDER BY cast(kvs.score as FLOAT) DESC";
        List<KPIVillageStatistics> all = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class, allSql);
        return Result.of(all);
    }

    @GetMapping("/getTotalScore")
    public Result<Map<String, List<String>>> getTotalScore(@Nonnull String taskYear) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CadreTask.class);
        detachedCriteria.add(Restrictions.eq("taskYear", taskYear));
        detachedCriteria.add(Restrictions.eq("type", "综合年度考核"));
        List<CadreTask> all = cadreTaskService.findAll(detachedCriteria);
        if (all.size() == 0) {
            return Result.of(500, "当前任务不存在!");
        }
        CadreTask cadreTask = all.get(0);

        String sql = "SELECT * FROM KPI_Village_Statistics kvs WHERE quotaLevel ='0' and kvs.taskId = '" +cadreTask.getId()+ "' ORDER BY cast(kvs.score as FLOAT) asc";

        List<KPIVillageStatistics> allBySql = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class, sql);

        List<String> allScore = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for (KPIVillageStatistics item : allBySql) {
            if (!map.containsKey(item.getParentDistrictName())) {
                map.put(item.getParentDistrictName(), new ArrayList<>());
            }
            List<String> strings = map.get(item.getParentDistrictName());
            strings.add(item.getScore());
            allScore.add(item.getScore());
        }
        map.put("全市", allScore);
        return Result.of(map);
    }

    @GetMapping("/reserveTalents")
    public List<KPIVillageStatistics> reserveTalents(String taskYear) {
        String sql = "SELECT top 20 b.* from (\n" +
                "SELECT cast(sum(calScore) as varchar) as score, a.districtId, a.districtName FROM (\n" +
                "SELECT CAST(score AS FLOAT) * 0.15 AS calScore, kvs.* from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '1' and kvs.quotaId = '" + taskYear+ "02'\n" +
                "UNION ALL\n" +
                "SELECT CAST(score AS FLOAT) * 0.25 AS calScore, kvs.* from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '1' and kvs.quotaId = '" + taskYear+ "03'\n" +
                "UNION ALL\n" +
                "SELECT CAST(score AS FLOAT) * 0.25 AS calScore, kvs.* from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '1' and kvs.quotaId = '" + taskYear+ "04'\n" +
                "UNION ALL\n" +
                "SELECT CAST(score AS FLOAT) * 0.35 AS calScore, kvs.* from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '1' and kvs.quotaId = '" + taskYear+ "05'\n" +
                ") a GROUP BY a.districtId, a.districtName ) b ORDER BY cast(score as FLOAT) desc";
        return kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class, sql);
    }

    public String getCurrentTaskId(String taskYear) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CadreTask.class);
        detachedCriteria.add(Restrictions.eq("taskYear", taskYear));
        detachedCriteria.add(Restrictions.eq("type", "综合年度考核"));
        List<CadreTask> all = cadreTaskService.findAll(detachedCriteria);
        if (all.size() == 0) {
            return null;
        }
        CadreTask cadreTask = all.get(0);
        return cadreTask.getId();
    }

}
