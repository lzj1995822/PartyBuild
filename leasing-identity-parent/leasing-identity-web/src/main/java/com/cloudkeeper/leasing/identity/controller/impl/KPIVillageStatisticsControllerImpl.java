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
import com.cloudkeeper.leasing.identity.vo.TrainingForecastVO;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
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
        CadreTask cadreTask = checkCurrentTask(taskYear);

        if (cadreTask == null) {
            return Result.of(200, "当前年份对应的考核任务不存在！", null);
        }

        if (StringUtils.isEmpty(cadreTask.getHasGenerateResult()) || "0".equals(cadreTask.getHasGenerateResult())) {
            return Result.of(200, "当前年份考核结果尚未生成，请前往考核实施-任务管理生成！", null);
        }
        String taskId = cadreTask.getId();

        String allRankSql = "SELECT * FROM KPI_Village_Statistics kvs WHERE quotaLevel ='0' and taskId = '" +taskId+ "' ORDER BY cast(kvs.score as FLOAT) DESC";
        List<KPIVillageStatistics> allRank = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class, allRankSql);


        String last10Sql = "SELECT TOP 10 * FROM KPI_Village_Statistics kvs WHERE quotaLevel ='0' and taskId = '" +taskId+ "' ORDER BY cast(kvs.score as FLOAT) ASC";
        List<KPIVillageStatistics> last10 = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class, last10Sql);

        String lastGroupByTownSql = "SELECT\n" +
                "\t* \n" +
                "FROM\n" +
                "\tKPI_Village_Statistics kvs \n" +
                "WHERE\n" +
                "\tkvs.score IN ( SELECT CAST( MIN ( CAST ( score AS FLOAT ) ) as varchar) AS score FROM KPI_Village_Statistics WHERE " +
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


        String good10Sql = "SELECT top  10 cast(score as VARCHAR) as score, c.cadresName FROM (\n" +
                "SELECT sum(score) as score, b.cadresName from  (\n" +
                "select  cast(kvs.score as FLOAT) * 0.8 as score, kvs.cadresName from KPI_Village_Statistics kvs WHERE quotaLevel = '0' and taskId = '" + taskId + "'\n" +
                "union all\n" +
                "select  cast(kvs.score as FLOAT) * 0.2 as score, kvs.cadresName from KPI_Village_Statistics kvs WHERE quotaLevel = '2' and taskId = '" + taskId + "' and quotaId = '" + taskYear + "0502' \n" +
                ") b GROUP BY b.cadresName ) c ORDER BY score desc";
        List<KPIVillageStatistics> good10 = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class, good10Sql);

        Map<String,Object> res = new HashMap<>();
        res.put("all", allRank);
        res.put("last10", last10);
        res.put("lastGroupByTown", lastGroupByTown);
        res.put("top3ByQuotas", map);
        res.put("good10", good10);
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
            return Result.of(200, "当前年份对应的考核任务不存在！", null);
        }
        String allSql = "SELECT * FROM KPI_Village_Statistics kvs WHERE quotaLevel ='0' and taskId = '" +currentTaskId+ "' ORDER BY cast(kvs.score as FLOAT) DESC";
        List<KPIVillageStatistics> all = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class, allSql);
        return Result.of(all);
    }

    @GetMapping("/getTotalScore")
    public Result<Map<String, List<String>>> getTotalScore(@Nonnull String taskYear) {
        CadreTask cadreTask = checkCurrentTask(taskYear);
        if (cadreTask == null) {
            return Result.of(200, "当前任务不存在!", null);
        }

        if (StringUtils.isEmpty(cadreTask.getHasGenerateResult()) || "0".equals(cadreTask.getHasGenerateResult())) {
            return Result.of(200, "当前年份考核结果尚未生成，请前往考核实施-任务管理生成！", null);
        }

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
    public Result<Map> reserveTalents(@Nonnull String taskYear,@Nonnull String limit) {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("45岁以下", generateReserveTalentsSql(null, "45", taskYear, limit));
        info.put("45岁-55岁", generateReserveTalentsSql("45", "55", taskYear, limit));
        info.put("55岁以上", generateReserveTalentsSql("55", null, taskYear, limit));
        Map<String, Object> chart = new LinkedHashMap<>();
        chart.put("45岁以下", generateReserveTalentByAge(null, "45", taskYear));
        chart.put("45岁-55岁", generateReserveTalentByAge("45", "55", taskYear));
        chart.put("55岁以上", generateReserveTalentByAge("55", null, taskYear));
        return Result.of(new LinkedHashMap(){{put("info", info);put("chart", chart);}});
    }

    private List<KPIVillageStatisticsVO> generateReserveTalentByAge(String startAge, String endAge, String taskYear) {
        String sql =
                "SELECT cast(d.ranking as int) as ranking, d.districtId, d.districtName, vc.name as cadresName, cast(FLOOR(datediff(DY,vc.birth,getdate())/365.25) as int) as age from  (\n" +
                "SELECT rank() over ( ORDER BY score desc) as ranking, c.* from (\n" +
                "SELECT sum(calScore) as score, a.districtId, a.districtName FROM  \n" +
                "(\n" +
                "SELECT CAST(score AS FLOAT) * 0.15 AS calScore, kvs.* from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '1' and kvs.quotaId = '" + taskYear + "02' \n" +
                "UNION ALL \n" +
                "SELECT CAST(score AS FLOAT) * 0.25 AS calScore, kvs.* from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '1' and kvs.quotaId = '" + taskYear + "03' \n" +
                "UNION ALL \n" +
                "SELECT CAST(score AS FLOAT) * 0.25 AS calScore, kvs.* from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '1' and kvs.quotaId = '" + taskYear + "04' \n" +
                "UNION ALL \n" +
                "SELECT CAST(score AS FLOAT) * 0.35 AS calScore, kvs.* from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '1' and kvs.quotaId = '" + taskYear + "05' \n" +
                ") a \n" +
                "GROUP BY a.districtId, a.districtName\n" +
                ") c\n" +
                ") d\n" +
                "LEFT JOIN village_cadres vc on vc.districtId = d.districtId and vc.cadresType = 'SECRETARY'\n";
        sql = handleQueryParam(startAge, endAge, sql);
        sql += "ORDER BY cast(score as FLOAT) desc";
        return kPIVillageStatisticsService.findAllBySql(KPIVillageStatisticsVO.class, sql);
    }
    private List<KPIVillageStatisticsVO> generateReserveTalentsSql(String startAge, String endAge, String taskYear, String limit) {
        String sql = "SELECT top " + limit +" cast(b.score as varchar) as score,b.districtId, b.districtName , b.name as cadresName from  (\n" +
                "SELECT cast(sum(calScore) as varchar) as score, a.districtId, a.districtName, vc.name FROM  \n" +
                "(\n" +
                "SELECT CAST(score AS FLOAT) * 0.15 AS calScore, kvs.* from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '1' and kvs.quotaId = '" + taskYear + "02' \n" +
                "UNION ALL \n" +
                "SELECT CAST(score AS FLOAT) * 0.25 AS calScore, kvs.* from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '1' and kvs.quotaId = '" + taskYear + "03' \n" +
                "UNION ALL \n" +
                "SELECT CAST(score AS FLOAT) * 0.25 AS calScore, kvs.* from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '1' and kvs.quotaId = '" + taskYear + "04' \n" +
                "UNION ALL \n" +
                "SELECT CAST(score AS FLOAT) * 0.35 AS calScore, kvs.* from KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '1' and kvs.quotaId = '" + taskYear + "05' \n" +
                ") a \n" +
                "LEFT JOIN village_cadres vc on vc.districtId = a.districtId and vc.cadresType = 'SECRETARY'\n";
        sql = handleQueryParam(startAge, endAge, sql);
        sql += "GROUP BY a.districtId, a.districtName, vc.name ) b ORDER BY cast(score as FLOAT) desc";
        return kPIVillageStatisticsService.findAllBySql(KPIVillageStatisticsVO.class, sql);
    }

    private String handleQueryParam(String startAge, String endAge, String sql) {
        if (!StringUtils.isEmpty(startAge) && !StringUtils.isEmpty(endAge)) {
            sql += "WHERE FLOOR(datediff(DY,vc.birth,getdate())/365.25) >= " + startAge + " " +
                    "and  FLOOR(datediff(DY,vc.birth,getdate())/365.25) <= " + endAge + "\n";
        } else if (!StringUtils.isEmpty(startAge)){
            sql += "WHERE FLOOR(datediff(DY,vc.birth,getdate())/365.25) > " + startAge + "\n";
        } else if (!StringUtils.isEmpty(endAge)) {
            sql += "WHERE FLOOR(datediff(DY,vc.birth,getdate())/365.25) < " + endAge + "\n";
        }
        return sql;
    }

    public String getCurrentTaskId(String taskYear) {
        CadreTask cadreTask = checkCurrentTask(taskYear);
        if (cadreTask == null) {
            return null;
        }
        return cadreTask.getId();
    }


    @GetMapping("/trainingForecast")
    public Result trainingForecast(@Nonnull String taskYear) {
        CadreTask cadreTask = checkCurrentTask(taskYear);
        if (cadreTask == null) {
            return Result.of(200, "当前任务不存在!");
        }
        String taskId = cadreTask.getId();
        ArrayList<Map> res = new ArrayList<>();
        String industrySql = "SELECT av.*,vc.name as cadresName FROM (\n" +
                "SELECT ROUND(SUM(totalOut)/sum(economicIncome) , 2) as val, v.districtId, v.districtName from (\n" +
                "SELECT sum(cast(ri.totalReward as FLOAT)) as totalOut, 0 as economicIncome, vc.districtId,vc.districtName  " +
                "FROM Reward_Info ri LEFT JOIN village_cadres vc on vc.id = ri.cadresId WHERE vc.districtId is not null " +
                "and YEAR(ri.achieveTime) = '" + taskYear + "' GROUP BY vc.districtId, vc.districtName\n" +
                "UNION all\n" +
                "SELECT  0 as totalOut, cast(ISNULL(di.economicIncome, 0) AS FLOAT) AS economicIncome, di.districtId, di.districtName " +
                "from Detection_Index di  WHERE di.tasKId = '" + taskId+ "' ) v GROUP BY v.districtId, v.districtName ) " +
                "av LEFT JOIN village_cadres vc on vc.districtId = av.districtId AND vc.cadresType = 'SECRETARY'\n" +
                "WHERE vc.name is not null\n" +
                "ORDER BY av.val asc\n";
        List<TrainingForecastVO> industryForecast = kPIVillageStatisticsService.findAllBySql(TrainingForecastVO.class, industrySql);
        res.add(calAvgAndLast40(industryForecast, "0.5", "1" , ">", "产业类"));

        String partySql = "SELECT sum(cast(kvs.score as float)) as val, kvs.districtId, kvs.districtName, kvs.cadresName \n" +
                "from KPI_Village_Statistics kvs where kvs.quotaLevel = '2' and kvs.quotaId in ('" + taskYear + "0501', '" + taskYear + "0402', '" + taskYear + "0302') and kvs.cadresName is not null\n" +
                "GROUP BY kvs.districtId, kvs.districtName, kvs.cadresName ORDER BY val asc";
        List<TrainingForecastVO> partyForecast = kPIVillageStatisticsService.findAllBySql(TrainingForecastVO.class, partySql);
        res.add(calAvgAndLast40(partyForecast, "11.3", "0", "<", "党建类"));

        String lawSql = "SELECT cast(score as Float) as val, kvs.districtId, kvs.districtName, kvs.cadresName " +
                "FROM KPI_Village_Statistics kvs WHERE kvs.quotaLevel = '1' " +
                "and kvs.quotaId = '" + taskYear + "04' and kvs.cadresName is not null ORDER BY val asc";
        List<TrainingForecastVO> lawForecast = kPIVillageStatisticsService.findAllBySql(TrainingForecastVO.class, lawSql);
        res.add(calAvgAndLast40(lawForecast, "7", "0", "<", "政策法规类"));

        String mangeSql = "SELECT sum(cast(kvs.score as float)) as val, kvs.districtId, kvs.districtName, kvs.cadresName \n" +
                "from KPI_Village_Statistics kvs where kvs.quotaLevel = '2' and kvs.quotaId in ('" + taskYear + "0305', '" + taskYear + "0303', '" + taskYear + "0502') and kvs.cadresName is not null\n" +
                "GROUP BY kvs.districtId, kvs.districtName, kvs.cadresName ORDER BY val asc";
        List<TrainingForecastVO> mangeForecast = kPIVillageStatisticsService.findAllBySql(TrainingForecastVO.class, lawSql);
        res.add(calAvgAndLast40(mangeForecast, "12.6", "0", "<", "治理类"));
        return Result.of(res);
    }

    private CadreTask checkCurrentTask(@Nonnull String taskYear) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CadreTask.class);
        detachedCriteria.add(Restrictions.eq("taskYear", taskYear));
        detachedCriteria.add(Restrictions.eq("type", "综合年度考核"));
        List<CadreTask> all = cadreTaskService.findAll(detachedCriteria);
        if (all.size() == 0) {
            return null;
        }
        return all.get(0);
    }

    private Map calAvgAndLast40(@Nonnull List<TrainingForecastVO> trainingForecastVOS, String standard, String isPercent, String operate, String label) {
        Double sum = 0.0;
        int size = 0;
        List<TrainingForecastVO> last40 = new ArrayList<>();
        for (TrainingForecastVO item : trainingForecastVOS) {
            sum += item.getVal();
            if (size < 40) {
                last40.add(item);
                size++;
            }
        }
        BigDecimal sumDecimal = new BigDecimal(sum);
        BigDecimal avg = sumDecimal.divide(new BigDecimal(trainingForecastVOS.size()), 2, RoundingMode.FLOOR);
        Map<String, Object> res = new HashMap<>();
        res.put("avg", avg.toString());
        res.put("standard", standard);
        res.put("isPercent", isPercent);
        res.put("last40", last40);
        res.put("label", label);
        if (operate.equals("<")) {
            res.put("isNeedOperate", avg.doubleValue() < Double.valueOf(standard));
        } else {
            res.put("isNeedOperate", avg.doubleValue() > Double.valueOf(standard));
        }
        return res;
    }
}
