package com.cloudkeeper.leasing.identity.controller.impl;

import com.cloudkeeper.leasing.base.annotation.Authorization;
import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.config.ExcelUtils;
import com.cloudkeeper.leasing.identity.controller.KPIVillageStatisticsController;
import com.cloudkeeper.leasing.identity.domain.KPITownQuota;
import com.cloudkeeper.leasing.identity.domain.KPIVillageQuota;
import com.cloudkeeper.leasing.identity.domain.KPIVillageStatistics;
import com.cloudkeeper.leasing.identity.dto.kpivillagestatistics.KPIVillageStatisticsDTO;
import com.cloudkeeper.leasing.identity.dto.kpivillagestatistics.KPIVillageStatisticsSearchable;
import com.cloudkeeper.leasing.identity.service.KPITownQuotaService;
import com.cloudkeeper.leasing.identity.service.KPIVillageStatisticsService;
import com.cloudkeeper.leasing.identity.vo.ImportVO;
import com.cloudkeeper.leasing.identity.vo.KPIVillageStatisticsVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsVO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public Result<Boolean> setSatistcs() {
        List<KPITownQuota> list = kPITownQuotaService.findAll();
        List<KPIVillageStatistics> res = new ArrayList<>();
        String taskId = "";
        for (KPITownQuota kpiTownQuota : list) {
            for (KPIVillageQuota kpiVillageQuota : kpiTownQuota.getKpiVillageQuotas()) {
                //整合至统计表
                KPIVillageStatistics k = new KPIVillageStatistics();
                k.setDistrictId(kpiVillageQuota.getDistrictId());
                k.setDistrictName(kpiVillageQuota.getDistrictName());
                k.setParentDistrictId(kpiTownQuota.getDistrictId());
                k.setParentDistrictName(kpiTownQuota.getDistrictName());
                k.setParentQuotaId(kpiTownQuota.getParentQuotaId());
                k.setParentQuotaName(kpiTownQuota.getParentQuotaName());
                k.setQuotaName(kpiTownQuota.getQuotaName());
                k.setQuarter(kpiTownQuota.getQuarter());
                k.setScore(StringUtils.isNotBlank(kpiVillageQuota.getScore()) ? kpiVillageQuota.getScore() : "0");
                k.setTaskId(taskId);
                k.setWeight(kpiVillageQuota.getWeight());
                res.add(k);
            }
        }
        kPIVillageStatisticsService.addStatistics(res);//三级统计


        //二级统计
        String sql = "SELECT cast(sum(cast(score as FLOAT)) as VARCHAR) as score,districtId,districtName, parentDistrictName,parentDistrictId,parentQuotaId as quotaId,parentQuotaName as quotaName FROM KPI_Village_Statistics GROUP BY districtId,districtName, parentDistrictName,parentDistrictId,parentQuotaId,parentQuotaName";
        res = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class, sql);
        List<KPIVillageStatistics> s = new ArrayList<>();
        for (KPIVillageStatistics kpiVillageStatistics : res){
            kpiVillageStatistics.setTaskId(taskId);
            kpiVillageStatistics.setParentQuotaId(kpiVillageStatistics.getQuotaId().substring(0,2));
            s.add(kpiVillageStatistics);
        }
        kPIVillageStatisticsService.addStatistics(s);


        //一级考核
        String sql1 = "SELECT cast(sum(cast(score as FLOAT)) as VARCHAR) as score,districtId,districtName, parentDistrictName,parentDistrictId,parentQuotaId as quotaId FROM KPI_Village_Statistics where parentQuotaName is null  GROUP BY districtId,districtName, parentDistrictName,parentDistrictId,parentQuotaId";
        res = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class, sql1);
        //获取所有村，生成MAP
        List<KPIVillageStatistics> t = new ArrayList<>();
        for (KPIVillageStatistics kpiVillageStatistics : res){
            kpiVillageStatistics.setTaskId(taskId);
            kpiVillageStatistics.setParentQuotaId("0");
            t.add(kpiVillageStatistics);
        }
        kPIVillageStatisticsService.addStatistics(t);
        return Result.of(true);
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
    public Result<Object> getStatistics(@PathVariable String districtId) {

        String sql = "SELECT top 10 * FROM(SELECT  districtName,parentDistrictName,CAST(SUM(CAST (score as FLOAT)) as varchar) as  score,cadresName FROM KPI_Village_Statistics WHERE quotaName IS null GROUP BY districtName,parentDistrictName,cadresName) a ORDER BY a.score DESC";
        List<KPIVillageStatistics> qianshi = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class,sql);


        String sql1 = "SELECT top 10 * FROM(SELECT  districtName,parentDistrictName,CAST(SUM(CAST (score as FLOAT)) as varchar) as  score,cadresName FROM KPI_Village_Statistics WHERE quotaName IS null GROUP BY districtName,parentDistrictName,cadresName) a where CAST (a.score as FLOAT) >0 ORDER BY a.score asc";
        List<KPIVillageStatistics> houshi = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class,sql1);

        String sql2 = "SELECT * FROM(SELECT  districtName,parentDistrictName,CAST(SUM(CAST (score as FLOAT)) as varchar) as  score,cadresName FROM KPI_Village_Statistics WHERE quotaName IS null GROUP BY districtName,parentDistrictName,cadresName) a ORDER BY a.score DESC";
        List<KPIVillageStatistics> quanbu = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class,sql2);

        String sql3 = "SELECT * FROM(SELECT  districtName,parentDistrictName,CAST(SUM(CAST (score as FLOAT)) as varchar) as  score,cadresName FROM KPI_Village_Statistics WHERE parentDistrictId = '"+districtId+"' and quotaName IS null GROUP BY districtName,parentDistrictName,cadresName) a ORDER BY a.score DESC";
        List<KPIVillageStatistics> quanzhen = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class,sql3);

        //String sql4 = "SELECT top 24 a.* FROM(SELECT  districtName,parentDistrictName,SUM(CAST (score as FLOAT)) score,cadresName FROM KPI_Village_Statistics WHERE quotaName IS null GROUP BY districtName,parentDistrictName,cadresName) a ORDER BY a.score DESC";
        List<KPIVillageStatistics> youxiu = new ArrayList<>();

        //String sql5 = "SELECT top 106 a.* FROM(SELECT  districtName,parentDistrictName,SUM(CAST (score as FLOAT)) score,cadresName FROM KPI_Village_Statistics WHERE quotaName IS null GROUP BY districtName,parentDistrictName,cadresName) a ORDER BY a.score DESC";
        //List<KPIVillageStatistics> c = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class,sql5);
        List<KPIVillageStatistics> chenzhi = new ArrayList<>();
        List<KPIVillageStatistics> jibenchenzhi = new ArrayList<>();
        for (int i = 0;i < quanbu.size()-1;i++){
            if (i < 24){
                youxiu.add(quanbu.get(i));
            }else if (i < 106){
                chenzhi.add(quanbu.get(i));
            }else {
                jibenchenzhi.add(quanbu.get(i));
            }
        }
        String sql5 = "SELECT * FROM(SELECT  districtName,parentDistrictName,CAST(SUM(CAST (score as FLOAT)) as varchar) as  score,cadresName FROM KPI_Village_Statistics WHERE quotaName IS null GROUP BY districtName,parentDistrictName,cadresName) a where (CAST (a.score as FLOAT) > 0 and cast(a.score as FLOAT) <60)   ORDER BY a.score asc";
        List<KPIVillageStatistics> buchengzhi = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class,sql5);

        Map<String,Object> map = new HashMap<>();
        map.put("qianshi",qianshi);
        map.put("houshi",houshi);
        map.put("quanbu",quanbu);
        map.put("quanzhen",quanzhen);
        map.put("youxiu",youxiu);
        map.put("chenzhi",chenzhi);
        map.put("jibenchenzhi",jibenchenzhi);
        map.put("buchengzhi",buchengzhi);
        return Result.of(map);
    }

    @Override
    public Result<Object> getStatisticsOnAverage(@PathVariable String quotaId) {
        String sql = "SELECT COUNT(1) AS val,parentDistrictName as name FROM KPI_Village_Statistics WHERE CAST (score as FLOAT) < (SELECT AVG(CAST (score as FLOAT)) FROM KPI_Village_Statistics  WHERE quotaId = '"+quotaId+"') AND quotaId = '"+quotaId+"' AND parentDistrictName != '广电测试镇党委' AND districtId != '-1' GROUP BY parentDistrictName";
        List<StatisticsVO> buchengzhi = kPIVillageStatisticsService.findAllBySql(StatisticsVO.class,sql);
        return Result.of(buchengzhi);
    }

    @Override
    public Result<Object> getExcellent() {
        String sql2 = "SELECT * FROM(SELECT  districtName,parentDistrictName,CAST(SUM(CAST (score as FLOAT)) as varchar) as  score,cadresName FROM KPI_Village_Statistics WHERE quotaName IS null GROUP BY districtName,parentDistrictName,cadresName HAVING SUM(CAST (score as FLOAT) )> 0) a ORDER BY a.score DESC";
        List<KPIVillageStatistics> quanbu = kPIVillageStatisticsService.findAllBySql(KPIVillageStatistics.class,sql2);
        return Result.of(quanbu);
    }


}
