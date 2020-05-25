package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.*;
import com.cloudkeeper.leasing.identity.repository.KPIVillageStatisticsRepository;
import com.cloudkeeper.leasing.identity.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 村一级指标统计 service
 * @author yujian
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KPIVillageStatisticsServiceImpl extends BaseServiceImpl<KPIVillageStatistics> implements KPIVillageStatisticsService {

    /** 村一级指标统计 repository */
    private final KPIVillageStatisticsRepository kPIVillageStatisticsRepository;

    private final CadreTaskService cadreTaskService;

    private final KpiQuotaService kpiQuotaService;

    private final KPITownQuotaService kPITownQuotaService;

    private final KPIVillageQuotaService kpiVillageQuotaService;


    @Override
    protected BaseRepository<KPIVillageStatistics> getBaseRepository() {
        return kPIVillageStatisticsRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("districtName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("parentDistrictName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("quotaName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("parentQuotaName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("score", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("weight", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("quarter", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public void addStatistics(List<KPIVillageStatistics> kpiVillageStatistics) {
        kPIVillageStatisticsRepository.saveAll(kpiVillageStatistics);
        kPIVillageStatisticsRepository.flush();
    }

    private String level2Statistic(String levelCode, String taskYear, String taskId) {
        String sql = "SELECT cast(b.score as varchar) as score, b.districtId, b.districtName, b.parentQuotaId as quotaId, '2' as quotaLevel, " +
                "vc.name as cadresName, sd.orgParent as parentDistrictId, sd.orgParentName as parentDistrictName, " +
                "kq.quotaName as quotaName, kq.parentQuotaId, kqp.quotaName as parentQuotaName, '" +taskId+ "' as taskId FROM (\n" +
                "SELECT ROUND(avg(cast(score as float)), 2) as score, parentQuotaId, districtId, " +
                "districtName from KPI_village_Quota kvq WHERE kvq.parentQuotaId like '" + taskYear + levelCode+ "%'  " +
                "GROUP BY parentQuotaId,districtId,districtName ) b LEFT JOIN village_cadres vc on vc.districtId = b.districtId \n" +
                "LEFT JOIN SYS_District sd on sd.districtId = b.districtId\n" +
                "LEFT JOIN KPI_Quota kq on kq.quotaId = b.parentQuotaId\n" +
                "LEFT JOIN KPI_Quota kqp on kq.parentQuotaId = kqp.quotaId\n" +
                "WHERE vc.cadresType = 'SECRETARY'";
        return sql;
    }
    @Override
    public Boolean generateVillageStatistic(@Nonnull String taskId) {
        CadreTask byId = cadreTaskService.findById(taskId);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CadreTask.class);
        detachedCriteria.add(Restrictions.eq("taskYear", byId.getTaskYear()));
        detachedCriteria.add(Restrictions.eq("type", "考核指标内容制定"));
        List<CadreTask> all = cadreTaskService.findAll(detachedCriteria);
        if (all.size() == 0) {
            return false;
        }
        CadreTask quotaTask = all.get(0);

        DetachedCriteria detachedCriteria1 = DetachedCriteria.forClass(KpiQuota.class);
        detachedCriteria1.add(Restrictions.eq("quotaYear", quotaTask.getTaskYear()));
        detachedCriteria1.add(Restrictions.eq("quotaLevel", "2"));
        detachedCriteria1.addOrder(Order.asc("quotaId"));
        List<KpiQuota> seconds = kpiQuotaService.findAll(detachedCriteria1);

        String taskYear = byId.getTaskYear();
        // 每个二级指标对应每个村的总分sql
        String sql = "SELECT cast(b.score as varchar) as score, b.districtId, b.districtName, b.parentQuotaId as quotaId, '2' as quotaLevel, " +
                "vc.name as cadresName, sd.orgParent as parentDistrictId, sd.orgParentName as parentDistrictName, " +
                "kq.quotaName as quotaName, kq.parentQuotaId, kqp.quotaName as parentQuotaName, " +
                "'" +taskId+ "' as taskId FROM (\n" +
                "SELECT ROUND(avg(score), 2) as score, parentQuotaId, districtId, districtName from (\n" +
                "SELECT sum(cast(score as float)) as score, districtId, districtName,quarter,parentQuotaId " +
                "from KPI_village_Quota kvq WHERE kvq.parentQuotaId like '" + taskYear + "01%' GROUP BY " +
                "quarter,parentQuotaId,districtId,districtName ) a\n" +
                "GROUP BY parentQuotaId, districtId, districtName\n" +
                ") b LEFT JOIN village_cadres vc on vc.districtId = b.districtId \n" +
                "LEFT JOIN SYS_District sd on sd.districtId = b.districtId\n" +
                "LEFT JOIN KPI_Quota kq on kq.quotaId = b.parentQuotaId\n" +
                "LEFT JOIN KPI_Quota kqp on kq.parentQuotaId = kqp.quotaId\n" +
                "WHERE vc.cadresType = 'SECRETARY'";
        List<KPIVillageStatistics> allBySql = super.findAllBySql(KPIVillageStatistics.class, sql);
        allBySql.addAll(super.findAllBySql(KPIVillageStatistics.class, level2Statistic("02", taskYear, taskId)));
        allBySql.addAll(super.findAllBySql(KPIVillageStatistics.class, level2Statistic("03", taskYear, taskId)));
        allBySql.addAll(super.findAllBySql(KPIVillageStatistics.class, level2Statistic("04", taskYear, taskId)));
        allBySql.addAll(super.findAllBySql(KPIVillageStatistics.class, level2Statistic("05", taskYear, taskId)));
        allBySql.addAll(super.findAllBySql(KPIVillageStatistics.class, level2Statistic("06", taskYear, taskId)));
        kPIVillageStatisticsRepository.saveAll(allBySql);//二级统计


        //一级统计
        sql = "SELECT\n" +
                "\tvc.name as cadresName,\n" +
                "\tcast(b.score as varchar) as score,\n" +
                "\tb.districtId,\n" +
                "\tsd.districtName,\n" +
                "\tsd.orgParent AS parentDistrictId,\n" +
                "\tsd.orgParentName AS parentDistrictName,\n" +
                "\tb.parentQuotaId AS quotaId,\n" +
                "\tkq.quotaName,\n" +
                "\tkq.parentQuotaId,\n" +
                "\tpkq.quotaName AS parentQuotaName,\n" +
                "\t'1' AS quotaLevel,\n" +
                "\tcast(rank ( ) OVER ( partition BY b.parentQuotaId ORDER BY b.score DESC ) as int)  AS ranking, \n" +
                "\tcast(rank ( ) OVER ( partition BY b.parentQuotaId, sd.orgParent ORDER BY b.score DESC) as varchar) AS townRanking, " +
                "'" +taskId+ "' as taskId " +
                "FROM\n" +
                "\t(\n" +
                "\tSELECT SUM\n" +
                "\t\t( CAST ( kvs.score AS FLOAT ) ) AS score,\n" +
                "\t\tkvs.parentQuotaId,\n" +
                "\t\tkvs.districtId \n" +
                "\tFROM\n" +
                "\t\tKPI_Village_Statistics kvs \n" +
                "\tWHERE\n" +
                "\t\tkvs.taskId = '" + taskId + "'" +
                "\tGROUP BY\n" +
                "\t\tkvs.parentQuotaId,\n" +
                "\t\tkvs.districtId \n" +
                "\t) b\n" +
                "\tLEFT JOIN SYS_District sd ON sd.districtId = b.districtId\n" +
                "\tLEFT JOIN KPI_Quota kq ON kq.quotaId = b.parentQuotaId\n" +
                "\tLEFT JOIN KPI_Quota pkq ON kq.parentQuotaId = pkq.quotaId" +
                "\tLEFT JOIN village_cadres vc ON vc.districtId = sd.districtId where vc.cadresType = 'SECRETARY' \n";
        List<KPIVillageStatistics> level1 = super.findAllBySql(KPIVillageStatistics.class, sql);
        handlePartitionLevel(level1, quotaTask.getTaskYear());
        kPIVillageStatisticsRepository.saveAll(level1);

        sql = "SELECT\n" +
                "\tvc.name as cadresName,\n" +
                "\tsd.districtName,\n" +
                "\tsd.orgParent AS parentDistrictId,\n" +
                "\tsd.orgParentName AS parentDistrictName,\n" +
                "\t'0' AS quotaId,\n" +
                "\t'最终得分' as quotaName,\n" +
                "\t'0' as parentQuotaId,\n" +
                "\t'0' AS parentQuotaName,\n" +
                "\t'0' AS quotaLevel,\n" +
                "\tcast(a.score as varchar) as score,\n" +
                "\ta.districtId,\n" +
                "\tcast(rank ( ) OVER ( ORDER BY a.score DESC ) as int) AS ranking, \n" +
                "'" +taskId+ "' as taskId " +
                "FROM\n" +
                "\t(\n" +
                "\tSELECT SUM\n" +
                "\t\t( CAST ( kvs.score AS FLOAT ) ) AS score,\n" +
                "\t\tkvs.districtId \n" +
                "\tFROM\n" +
                "\t\tKPI_Village_Statistics kvs \n" +
                "\tWHERE\n" +
                "\t\ttaskId = '" +taskId+ "' \n" +
                "\t\tand quotaLevel = '1' \n" +
                "\tGROUP BY\n" +
                "\t\tkvs.districtId \n" +
                "\t) a \n" +
                "\tLEFT JOIN SYS_District sd ON sd.districtId = a.districtId\n" +
                "\tLEFT JOIN village_cadres vc ON vc.districtId = sd.districtId and vc.cadresType = 'SECRETARY' \n" +
                "ORDER BY\n" +
                "\tranking ASC";
        List<KPIVillageStatistics> level0 = super.findAllBySql(KPIVillageStatistics.class, sql);
        kPIVillageStatisticsRepository.saveAll(level0);
        return true;
    }

    List<KPIVillageStatistics> handlePartitionLevel(List<KPIVillageStatistics> kpiVillageStatistics, String taskYear) {
        Map<String, Map<String, Long>> map = new HashMap<>();
        for (KPIVillageStatistics item : kpiVillageStatistics) {
            if (item.getQuotaId().contains(taskYear + "02")) {
                if (!map.containsKey(item.getParentDistrictId())) {
                    HashMap<String, Long> temp = new HashMap<>();
                    temp.put("max", 0l);
                    temp.put("aClass", 0l);
                    temp.put("bClass", 0l);
                    temp.put("cClass", 0l);
                    map.put(item.getParentDistrictId(), temp);
                }
                Map<String, Long> stringDoubleMap = map.get(item.getParentDistrictId());
                Long max = stringDoubleMap.get("max");
                max++;
                stringDoubleMap.put("max", max);
                stringDoubleMap.put("aClass", Math.round(max * 0.1));
                stringDoubleMap.put("bClass", Math.round(max * 0.4));
                stringDoubleMap.put("cClass", Math.round(max * 0.8));
                map.put(item.getParentDistrictId(), stringDoubleMap);
            }
        }
        for (KPIVillageStatistics item : kpiVillageStatistics) {
            if (item.getQuotaId().contains(taskYear + "02")) {
                Map<String, Long> stringLongMap = map.get(item.getParentDistrictId());
                Long max = stringLongMap.get("max");
                Long aClass = stringLongMap.get("aClass");
                Long bClass = stringLongMap.get("bClass");
                Long cClass = stringLongMap.get("cClass");
                String townRanking = item.getTownRanking();
                Long rank = Long.valueOf(townRanking);
                item.setOldScore(item.getScore());
                if (rank <= aClass) {
                    item.setPartitionLevel("A");
                    item.setScore("40");
                } else if (rank > aClass && rank <= bClass) {
                    item.setPartitionLevel("B");
                    item.setScore("36");
                } else if (rank > bClass && rank <= cClass) {
                    item.setPartitionLevel("C");
                    item.setScore("32");
                } else {
                    item.setPartitionLevel("D");
                    item.setScore("28");
                }
            }
        }
        return kpiVillageStatistics;
    }
}
