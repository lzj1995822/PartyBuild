package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.MessageCenter;
import com.cloudkeeper.leasing.identity.dto.villagecadres.ExportDTO;
import com.cloudkeeper.leasing.identity.dto.villagecadres.VillageCadresStatisticsSearchable;
import com.cloudkeeper.leasing.identity.service.FdfsService;
import com.cloudkeeper.leasing.identity.service.StatisticsService;
import com.cloudkeeper.leasing.identity.vo.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticsServiceImpl extends BaseServiceImpl implements StatisticsService  {
    @Override
    protected BaseRepository<MessageCenter> getBaseRepository() {
        return null;
    }
    private final static Logger log = LoggerFactory.getLogger(StatisticsServiceImpl.class);
    public final FdfsService fdfsService;
    @Override
    public List<StatisticsVO> getSxStatistics(String districtId,String cadresType) {
        String sql = "select count(1) as val,'男' as name from village_cadres where cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0' and hasRetire = '0' and districtId like '"+districtId+"%' and sex = '男'";
        sql += " UNION all ";
        sql += "select count(1) as val,'女' as name from village_cadres where cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and hasRetire = '0' and districtId like '"+districtId+"%' and sex = '女'";
        List<StatisticsVO> list = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        return list;
    }

    @Override
    public List<StatisticsVO> getAgeStatistics(String districtId,String cadresType) {
        String sql = "";
        if("SECRETARY".equals(cadresType)){
            sql =  "select  cast(isNULL(MIN(FLOOR(DATEDIFF(DY, birth, GETDATE()) / 365.25)),0) as int) as val,'最低年龄' as name from village_cadres where cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and districtId like '"+districtId+"%'\n" +
                    "UNION all\n" +
                    "select  isNULL(cast(round(avg(DATEDIFF(DY, birth, GETDATE()) / 365.25),0) as int),0) as val,'平均年龄' as name from village_cadres where cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and districtId like '"+districtId+"%'\n" +
                    "UNION all\n" +
                    "select  count(1) as val,'35周岁以下' as name from village_cadres where cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and DATEDIFF(YEAR,birth,GETDATE()) <= 35 and districtId like '"+districtId+"%'\n" +
                    "UNION all\n" +
                    "select  count(1) as val,'35-40周岁' as name from village_cadres where cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and DATEDIFF(YEAR,birth,GETDATE()) > 35 and DATEDIFF(YEAR,birth,GETDATE()) <= 40 and districtId like '"+districtId+"%'\n" +
                    "UNION all\n" +
                    "select  count(1) as val,'40-45周岁' as name from village_cadres where cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and DATEDIFF(YEAR,birth,GETDATE()) > 40 and DATEDIFF(YEAR,birth,GETDATE()) <= 45 and districtId like '"+districtId+"%'\n" +
                    "UNION all\n" +
                    "select  count(1) as val,'45-50周岁' as name from village_cadres where cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and DATEDIFF(YEAR,birth,GETDATE()) > 45 and DATEDIFF(YEAR,birth,GETDATE()) <= 50 and districtId like '"+districtId+"%'\n" +
                    "UNION all\n" +
                    "select  count(1) as val,'50周岁以上' as name from village_cadres where cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and DATEDIFF(YEAR,birth,GETDATE()) > 50 and districtId like '"+districtId+"%'\n" +
                    "\n";
        }else {
            sql =  "select  cast(isNULL(MIN(FLOOR(DATEDIFF(DY, birth, GETDATE()) / 365.25)),0) as int) as val,'最低年龄' as name from village_cadres where cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and districtId like '"+districtId+"%'\n" +
                    "UNION all\n" +
                    "select  isNULL(cast(round(avg(DATEDIFF(DY, birth, GETDATE()) / 365.25),0) as int),0) as val,'平均年龄' as name from village_cadres where cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and districtId like '"+districtId+"%'\n" +
                    "UNION all\n" +
                    "select  count(1) as val,'35周岁以下' as name from village_cadres where cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and DATEDIFF(YEAR,birth,GETDATE()) <= 35 and districtId like '"+districtId+"%'\n" +
                    "UNION all\n" +
                    "select  count(1) as val,'35-50周岁' as name from village_cadres where cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and DATEDIFF(YEAR,birth,GETDATE()) > 35 and DATEDIFF(YEAR,birth,GETDATE()) <= 50 and districtId like '"+districtId+"%'\n" +
                    "UNION all\n" +
                    "select  count(1) as val,'50周岁以上' as name from village_cadres where cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and DATEDIFF(YEAR,birth,GETDATE()) > 50 and districtId like '"+districtId+"%'\n" +
                    "\n";
        }

        List<StatisticsVO> list = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        return list;
    }

    @Override
    public List<StatisticsVO> getEduStatistics(String districtId,String cadresType) {
        String type = "education";
        if (!StringUtils.isEmpty(cadresType) && "IN_SECRETARY".equals(cadresType)) {
            type = "fullTimeEdu";
        }
        String sql = "SELECT count(1) as val,'研究生' as name from village_cadres WHERE  cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and " + type + " = '研究生' and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'本科' as name from village_cadres WHERE  cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and " + type + " = '本科' and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'大专' as name from village_cadres WHERE  cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and " + type + " = '大专' and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'中专' as name from village_cadres WHERE  cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and " + type + " = '中专' and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'高中及以下' as name from village_cadres WHERE  cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and " + type + " = '高中及以下' and districtId like '"+districtId+"%'";
        List<StatisticsVO> list = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        return list;
    }

    @Override
    public List<StatisticsVO> getcadresTypeStatistics(String districtId,String cadresType) {
        String sql = "SELECT count(1) as val,'公务员' as name from village_cadres WHERE cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and personnelType = '公务员' and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'事业编制' as name from village_cadres WHERE  cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and personnelType = '事业编制' and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'企业主' as name from village_cadres WHERE  cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and personnelType = '企业主' and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'全职村干部' as name from village_cadres WHERE  cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and personnelType = '全职村干部' and districtId like '"+districtId+"%'";
        List<StatisticsVO> list = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        return list;
    }

    @Override
    public List<StatisticsVO> getServingYearStatistics(String districtId,String cadresType) {
        String sql = "SELECT count(1) as val,'不满1年' as name from village_cadres WHERE cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and  cast(onDutyTime as FLOAT) < 1 and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'1-5年' as name from village_cadres WHERE cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and  cast(onDutyTime as FLOAT) >= 1 and cast(onDutyTime as FLOAT) < 5 and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'5-10年' as name from village_cadres WHERE cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and  cast(onDutyTime as FLOAT) >= 5 and cast(onDutyTime as FLOAT) < 10 and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'10-15年' as name from village_cadres WHERE cadresType like '"+cadresType+"%' and isDelete = '0' and hasRetire = '0'  and  cast(onDutyTime as FLOAT) >= 10 and cast(onDutyTime as FLOAT) < 15 and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'15-20年' as name from village_cadres WHERE cadresType like '"+cadresType+"%'  and isDelete = '0' and hasRetire = '0'  and  cast(onDutyTime as FLOAT) >= 15 and cast(onDutyTime as FLOAT) < 20 and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'20年以上' as name from village_cadres WHERE cadresType like '"+cadresType+"%'  and isDelete = '0' and hasRetire = '0'  and  cast(onDutyTime as FLOAT) >= 20 and districtId like '"+districtId+"%'";
        List<StatisticsVO> list = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        return list;
    }

    @Override
    public List<StatisticsVO> getRankStatistics(String districtId,String cadresType) {

        String sql = "SELECT count(1) as val,'一级专职村书记' as name from village_cadres WHERE cadresType like '"+cadresType+"%' and hasRetire = '0'  and  quasiAssessmentRank = '一级专职村书记' and districtId like '"+districtId+"%'  and isDelete = '0'" +
                "UNION all\n" +
                "SELECT count(1) as val,'二级专职村书记' as name from village_cadres WHERE cadresType like '"+cadresType+"%' and hasRetire = '0'  and  quasiAssessmentRank = '二级专职村书记' and districtId like '"+districtId+"%'  and isDelete = '0'" +
                "UNION all\n" +
                "SELECT count(1) as val,'三级专职村书记' as name from village_cadres WHERE cadresType like '"+cadresType+"%' and hasRetire = '0'  and  quasiAssessmentRank = '三级专职村书记' and districtId like '"+districtId+"%'  and isDelete = '0'" +
                "UNION all\n" +
                "SELECT count(1) as val,'四级专职村书记' as name from village_cadres WHERE cadresType like '"+cadresType+"%' and hasRetire = '0'  and  quasiAssessmentRank = '四级专职村书记' and districtId like '"+districtId+"%'  and isDelete = '0'" +
                "UNION all\n" +
                "SELECT count(1) as val,'五级专职村书记' as name from village_cadres WHERE cadresType like '"+cadresType+"%' and hasRetire = '0'  and  quasiAssessmentRank = '五级专职村书记' and districtId like '"+districtId+"%'  and isDelete = '0'";
        List<StatisticsVO> list = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        return list;
    }

    @Override
    public List<StatisticsNotIntegerVO> getSalaryStatistics(String districtId,String cadresType) {
        if (StringUtils.isEmpty(districtId)) {
            districtId = "01";
        }

        String sql = "SELECT cast(avg(cast(Isnull(reward.totalReward, 0) as  decimal(10,2))) as decimal(10,2)) as val," +
                "'总报酬平均值' as name from Reward_Info reward join village_cadres cadres on reward.cadresId = cadres.id " +
                "and cadres.cadresType like '"+cadresType+"%' and cadres.isDelete = '0' and hasRetire = '0' AND " +
                "reward.achieveTime IS NOT null and cadres.districtId like '" + districtId + "%'";
        StatisticsNotIntegerVO totals =  (StatisticsNotIntegerVO)findBySql(StatisticsNotIntegerVO.class,sql);//平均总报酬

        sql = "SELECT cast(avg(cast(Isnull(reward.basicReward, 0) as  decimal(10,2))) as decimal(10,2)) as val," +
                "'平均基本报酬' as name from Reward_Info reward join village_cadres cadres on reward.cadresId = cadres.id " +
                "and cadres.cadresType like '"+cadresType+"%' and cadres.isDelete = '0' and hasRetire = '0' " +
                "AND reward.achieveTime IS NOT null and cadres.districtId like '" + districtId + "%'";
        StatisticsNotIntegerVO basics = (StatisticsNotIntegerVO)findBySql(StatisticsNotIntegerVO.class,sql);//年平均基本报酬

        sql = "SELECT cast(avg(cast(Isnull(reward.basicReward, 0) as  decimal(10,2))) as decimal(10,2)) as val," +
                "'全职村干部基本报酬平均值' as name from Reward_Info reward join village_cadres cadres on " +
                "reward.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and cadres.isDelete = '0' and " +
                "hasRetire = '0' AND reward.achieveTime IS NOT null  and cadres.personnelType = '全职村干部' and cadres.districtId like '" + districtId + "%'";
        StatisticsNotIntegerVO basicsQzcgb = (StatisticsNotIntegerVO)findBySql(StatisticsNotIntegerVO.class,sql);//年平均全职村干部

        sql = "SELECT cast(avg(cast(Isnull(reward.basicReward, 0) as  decimal(10,2))) as decimal(10,2)) as val," +
                "'公务员村书记基本报酬平均值' as name from Reward_Info reward join village_cadres cadres on " +
                "reward.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and cadres.isDelete = '0' " +
                "and hasRetire = '0' AND reward.achieveTime IS NOT null  and cadres.personnelType = '公务员' and cadres.districtId like '" + districtId + "%'";
        StatisticsNotIntegerVO basicsGwy = (StatisticsNotIntegerVO)findBySql(StatisticsNotIntegerVO.class,sql);//年平均公务员

        sql = "SELECT cast(avg(cast(Isnull(reward.basicReward, 0) as  decimal(10,2))) as decimal(10,2)) as val," +
                "'事业编制村书记基本报酬平均值' as name from Reward_Info reward join village_cadres cadres on " +
                "reward.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and cadres.isDelete = '0' and " +
                "hasRetire = '0' AND reward.achieveTime IS NOT null  and cadres.personnelType = '事业编制' and cadres.districtId like '" + districtId + "%'";
        StatisticsNotIntegerVO basicsSybz = (StatisticsNotIntegerVO)findBySql(StatisticsNotIntegerVO.class,sql);//年平均事业编制

        sql = "SELECT cast(avg(cast(Isnull(reward.reviewReward, 0) as  decimal(10,2))) as decimal(10,2)) as val," +
                "'考核报酬平均值' as name from Reward_Info reward join village_cadres cadres on " +
                "reward.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and cadres.isDelete = '0' " +
                "and hasRetire = '0' AND reward.achieveTime IS NOT null and cadres.districtId like '" + districtId + "%'";
        StatisticsNotIntegerVO reviews = (StatisticsNotIntegerVO)findBySql(StatisticsNotIntegerVO.class,sql);//平均考核报酬

        sql = "SELECT cast(avg(cast(Isnull(reward.otherReward, 0) as  decimal(10,2))) as decimal(10,2)) as val," +
                "'增收报酬平均值' as name from Reward_Info reward join village_cadres cadres on " +
                "reward.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and cadres.isDelete = '0' " +
                "and hasRetire = '0' AND reward.achieveTime IS NOT null and cadres.districtId like '" + districtId + "%'";
        StatisticsNotIntegerVO others =  (StatisticsNotIntegerVO)findBySql(StatisticsNotIntegerVO.class,sql);//平均增收报酬

        sql = "SELECT cast(avg(cast(Isnull(reward.des, 0) as  decimal(10,2))) as decimal(10,2)) as val," +
                "'其他报酬平均值' as name from Reward_Info reward join village_cadres cadres on " +
                "reward.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and cadres.isDelete = '0' " +
                "and hasRetire = '0' AND reward.achieveTime IS NOT null and cadres.districtId like '" + districtId + "%'";
        StatisticsNotIntegerVO des =  (StatisticsNotIntegerVO)findBySql(StatisticsNotIntegerVO.class,sql);//平均其他报酬



        List<StatisticsNotIntegerVO> list = new LinkedList<>();
        list.add(totals);
        list.add(basics);
        list.add(basicsQzcgb);
        list.add(basicsGwy);
        list.add(basicsSybz);
        list.add(reviews);
        list.add(others);
        list.add(des);
        return list;
    }

    @Override
    public List<StatisticsVO> getPartyStandingStatistics(String districtId,String cadresType) {
        String sql = "SELECT count(1) as val, '不满1年' as name FROM village_cadres WHERE DATEDIFF(YEAR,partyTime,GETDATE()) < 1 and cadresType like '"+cadresType+"%' and hasRetire = '0' and isDelete = '0'  and districtId like '"+districtId+"%'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val, '1-5年' as name FROM village_cadres WHERE DATEDIFF(YEAR,partyTime,GETDATE()) < 5 and DATEDIFF(YEAR,partyTime,GETDATE()) >= 1 and cadresType like '"+cadresType+"%' and hasRetire = '0' and isDelete = '0'  and districtId like '"+districtId+"%'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val, '5-10年' as name FROM village_cadres WHERE DATEDIFF(YEAR,partyTime,GETDATE()) < 10 and DATEDIFF(YEAR,partyTime,GETDATE()) >= 5 and cadresType like '"+cadresType+"%' and hasRetire = '0' and isDelete = '0'  and districtId like '"+districtId+"%'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val, '10-15年' as name FROM village_cadres WHERE DATEDIFF(YEAR,partyTime,GETDATE()) < 15 and DATEDIFF(YEAR,partyTime,GETDATE()) >= 10 and cadresType like '"+cadresType+"%' and hasRetire = '0' and isDelete = '0'  and districtId like '"+districtId+"%'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val, '15-20年' as name FROM village_cadres WHERE DATEDIFF(YEAR,partyTime,GETDATE()) < 20 and DATEDIFF(YEAR,partyTime,GETDATE()) >= 15 and cadresType like '"+cadresType+"%' and hasRetire = '0' and isDelete = '0'    and districtId like '"+districtId+"%'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val, '20-30年' as name FROM village_cadres WHERE DATEDIFF(YEAR,partyTime,GETDATE()) < 30 and DATEDIFF(YEAR,partyTime,GETDATE()) >= 20 and cadresType like '"+cadresType+"%' and hasRetire = '0'  and districtId like '"+districtId+"%'"+
                "UNION ALL\n" +
                "SELECT count(1) as val, '30-40年' as name FROM village_cadres WHERE DATEDIFF(YEAR,partyTime,GETDATE()) < 40 and DATEDIFF(YEAR,partyTime,GETDATE()) >= 30 and cadresType like '"+cadresType+"%' and hasRetire = '0'  and districtId like '"+districtId+"%'"+
                "UNION ALL\n" +
                "SELECT count(1) as val, '40-50年' as name FROM village_cadres WHERE DATEDIFF(YEAR,partyTime,GETDATE()) < 50 and DATEDIFF(YEAR,partyTime,GETDATE()) >= 40 and cadresType like '"+cadresType+"%' and hasRetire = '0'  and districtId like '"+districtId+"%'"+
                "UNION ALL\n" +
                "SELECT count(1) as val, '50年以上' as name FROM village_cadres WHERE DATEDIFF(YEAR,partyTime,GETDATE()) >= 50 and cadresType like '"+cadresType+"%' and hasRetire = '0'  and districtId like '"+districtId+"%'";
        List<StatisticsVO> list = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        return list;
    }

    @Override
    public List<StatisticsListVO> getRewardsStatistics(String districtId,String cadresType) {
        String sql = "SELECT count(1) as val,'句容市级' as name from Honour_Info honour join village_cadres cadres on honour.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and hasRetire = '0' and cadres.isDelete = '0'    and cadres.districtId like '"+districtId+"%' and rewardsType = '表彰' and honourDescription = '句容市级'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val,'镇江市级' as name from Honour_Info honour join village_cadres cadres on honour.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and hasRetire = '0' and cadres.isDelete = '0'    and cadres.districtId like '"+districtId+"%' and rewardsType = '表彰' and honourDescription = '镇江市级'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val,'省级' as name from Honour_Info honour join village_cadres cadres on honour.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and hasRetire = '0' and cadres.isDelete = '0'    and cadres.districtId like '"+districtId+"%' and rewardsType = '表彰' and honourDescription = '省级'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val,'党中央国务院' as name from Honour_Info honour join village_cadres cadres on honour.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and hasRetire = '0' and cadres.isDelete = '0'    and cadres.districtId like '"+districtId+"%' and rewardsType = '表彰' and honourDescription = '党中央国务院'";
        List<StatisticsVO> rys = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        sql = "SELECT count(1) as val,'句容市级' as name from Honour_Info honour join village_cadres cadres on honour.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and hasRetire = '0' and cadres.isDelete = '0'  and cadres.districtId like '"+districtId+"%' and rewardsType = '处分' and honourDescription = '句容市级'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val,'镇江市级' as name from Honour_Info honour join village_cadres cadres on honour.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and hasRetire = '0' and cadres.isDelete = '0'    and cadres.districtId like '"+districtId+"%' and rewardsType = '处分' and honourDescription = '镇江市级'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val,'省级' as name from Honour_Info honour join village_cadres cadres on honour.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and hasRetire = '0' and cadres.isDelete = '0'    and cadres.districtId like '"+districtId+"%' and rewardsType = '处分' and honourDescription = '省级'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val,'党中央国务院' as name from Honour_Info honour join village_cadres cadres on honour.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and hasRetire = '0' and cadres.isDelete = '0'    and cadres.districtId like '"+districtId+"%' and rewardsType = '处分' and honourDescription = '党中央国务院'";
        List<StatisticsVO> cfs = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        List<StatisticsListVO> statisticsListVOS = new ArrayList<>();
        StatisticsListVO statisticsListVO1 = new StatisticsListVO();
        StatisticsListVO statisticsListVO2 = new StatisticsListVO();
        statisticsListVO1.setName("表彰");
        statisticsListVO1.setStatistics(rys);
        statisticsListVO2.setName("处分");
        statisticsListVO2.setStatistics(cfs);
        statisticsListVOS.add(statisticsListVO1);
        statisticsListVOS.add(statisticsListVO2);
        return statisticsListVOS;
    }

    @Override
    public Object getAgeCountByDistrict(String cadresType,String districtId) {
        String sql = "SELECT a.districtName as name,a.parentDistrictName as parentDistrictName, \n" +
                "cast((case  WHEN  a.val1 = 0 THEN 0 ELSE 1 END) as varchar) v1 ,\n" +
                "cast((case  WHEN  a.val1 = 0 THEN a.val2 ELSE ROUND(a.val2/a.val1, 1) END) as varchar) v2 ,\n" +
                "cast((case  WHEN  a.val1 = 0 THEN a.val3 ELSE ROUND(a.val3/a.val1, 1) END) as varchar) v3  \n" +
                "FROM \n" +
                "(select districtName,parentDistrictName,\n" +
                "cast(sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) < 35 THEN 1 ELSE 0 END) as FLOAT) as val1 ,\n" +
                "cast(sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) BETWEEN 35 AND 50 THEN 1 ELSE 0 END)  as FLOAT) as val2 ,\n" +
                "cast(sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) >50 THEN 1 ELSE 0 END)  as FLOAT) as val3 \n" +
                "from village_cadres WHERE cadresType like '"+cadresType+"%' AND hasRetire = '0' and isDelete = '0'  AND districtName IS NOT NULL  GROUP BY districtName,parentDistrictName ) a";

        String districtSQL = "SELECT districtName as name FROM SYS_District WHERE districtLevel = 2 and  districtType = 'Party' AND districtName NOT LIKE '%广电%' and districtId like '"+districtId +"%'";
        List<StatisticsListVO> dis = findAllBySql(StatisticsListVO.class,districtSQL);
        List<StatisticsVO> list = findAllBySql(StatisticsVO.class,sql);

        Map<String,List<StatisticsVO>> map = list.stream().collect(Collectors.groupingBy(StatisticsVO::getParentDistrictName));
        for (StatisticsListVO s : dis){
            if (map.containsKey(s.getName())){
                s.setStatistics(map.get(s.getName()));
            }
        }
        return dis;
    }

    @Override
    public List<StatisticsVO> getAllStatistics() {
        String sql = "select parentDistrictName as 'name', \n" +
                "(convert(varchar(10),sum(case  WHEN  sex = '男' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  sex = '女' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) <= 35 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) = 36 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) = 37 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) = 38 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) = 39 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) = 40 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) = 41 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) = 42 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) = 43 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) = 44 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) = 45 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) = 46 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) = 47 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) = 48 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) = 49 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) = 50 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,birth,GETDATE()) > 50 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  education = '研究生' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  education = '本科' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  education = '大专' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  education = '高中及以下' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  personnelType = '公务员' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  personnelType = '事业编制' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  personnelType = '企业主' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  personnelType = '全职村干部' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  cast(onDutyTime as FLOAT) < 1 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  cast(onDutyTime as FLOAT) BETWEEN 1 and 5 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  cast(onDutyTime as FLOAT) BETWEEN 6 and 10 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  cast(onDutyTime as FLOAT) BETWEEN 11 and 15 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  cast(onDutyTime as FLOAT) BETWEEN 16 and 20 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  cast(onDutyTime as FLOAT) >= 20 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  rank = '一级专职村书记' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  rank = '二级专职村书记' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  rank = '三级专职村书记' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  rank = '四级专职村书记' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  rank = '五级专职村书记' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,partyTime,GETDATE()) < 1 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,partyTime,GETDATE()) BETWEEN 1 and 5 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,partyTime,GETDATE()) BETWEEN 6 and 10 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,partyTime,GETDATE()) BETWEEN 11 and 15 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,partyTime,GETDATE()) BETWEEN 16 and 20 THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  DATEDIFF(YEAR,partyTime,GETDATE()) > 20 THEN 1 ELSE 0 END))\n" +
                ") as value\n" +
                "from village_cadres  WHERE parentDistrictName is not null and village_cadres.cadresType = 'SECRETARY' and village_cadres.hasRetire = '0' and village_cadres.isDelete = '0'  GROUP BY parentDistrictName ";
        List<StatisticsVO> cfs = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);

        String honoursql = "select parentDistrictName as 'name', \n" +
                "(\n" +
                "convert(varchar(10),sum(case  WHEN  rewardsType+honourDescription ='荣誉句容市级' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  rewardsType+honourDescription ='荣誉镇江市级' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  rewardsType+honourDescription ='荣誉省级' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  rewardsType+honourDescription ='荣誉党中央国务院' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  rewardsType+honourDescription ='处分句容市级' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  rewardsType+honourDescription ='处分镇江市级' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  rewardsType+honourDescription ='处分省级' THEN 1 ELSE 0 END))+ '_' + \n" +
                "convert(varchar(10),sum(case  WHEN  rewardsType+honourDescription ='处分党中央国务院' THEN 1 ELSE 0 END))\n" +
                ") as value\n" +
                "from village_cadres LEFT join Honour_Info on Honour_Info.cadresId = village_cadres.id WHERE parentDistrictName is not null and village_cadres.cadresType = 'SECRETARY' and village_cadres.hasRetire = '0' and village_cadres.isDelete = '0' GROUP BY parentDistrictName ";
        List<StatisticsVO> honours = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,honoursql);
        for (StatisticsVO s : cfs){
            for (StatisticsVO h : honours){
                if (s.getName().equals(h.getName())){
                    s.setValue(s.getValue()+"_"+h.getValue());
                }
            }
        }
        return cfs;
    }

    @Override
    public Object getCustomStatistics(List<VillageCadresStatisticsSearchable> villageCadresStatisticsSearchables, String cadresType, String districtId) {
        if (StringUtils.isEmpty(cadresType)) {
            cadresType = "SECRETARY";
        }
        try {
            //2.查询按镇统计
            StringBuilder s = new StringBuilder();
            s.append(createSql(villageCadresStatisticsSearchables, districtId));
            StringBuilder statisticsSql = new StringBuilder();

            String sql;

            if (!StringUtils.isEmpty(districtId) && districtId.length() >= 4) {
                statisticsSql.append("select count(1) as val, districtName as name from village_cadres  WHERE village_cadres.cadresType = '" + cadresType +"' and village_cadres.hasRetire = '0' and village_cadres.isDelete = '0' ");
                statisticsSql.append(s);
                statisticsSql.append(" group by districtName");
                sql = "SELECT 0 as val,districtName as name FROM SYS_District WHERE districtLevel = 3 and districtType = 'Party' and orgParent = '" +districtId+ "'";
            } else {
                statisticsSql.append("select count(1) as val, parentDistrictName as name from village_cadres  WHERE village_cadres.cadresType = '" + cadresType +"' and village_cadres.hasRetire = '0' and village_cadres.isDelete = '0' ");
                statisticsSql.append(s);
                statisticsSql.append(" group by parentDistrictName");
                sql = "SELECT 0 as val,districtName as name FROM SYS_District WHERE districtLevel = 2 and districtType = 'Party'";
            }
            List<StatisticsVO> statistics = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,statisticsSql.toString());

            //没有统计数据的镇补0
            List<StatisticsVO> districts = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
            for (StatisticsVO district : districts){
                for (StatisticsVO statisticsVO : statistics){
                    if (district.getName().equals(statisticsVO.getName())){
                        district.setVal(statisticsVO.getVal());
                    }
                }
            }
            Map<String,Object> map = new HashMap<>();
            map.put("statistics",districts);
            return map;
        }catch (Exception e){
            return "error";
        }
    }

    @Override
    public Object page(List<VillageCadresStatisticsSearchable> villageCadresStatisticsSearchables, String cadresType, Integer page, Integer size, String districtId, Pageable pageable) {
        if (StringUtils.isEmpty(cadresType)) {
            cadresType = "SECRETARY";
        }
        //1.查询列表数据
        StringBuilder s = new StringBuilder();
        Map<String,List<VillageCadresStatisticsSearchable>>  map = villageCadresStatisticsSearchables.stream().collect(Collectors.groupingBy(VillageCadresStatisticsSearchable::getTableName));
        s.append(map.containsKey("VillageCadres") ? createSql(map.get("VillageCadres"), districtId) : "");
        StringBuilder resSql = new StringBuilder();
        String tempSql = "SELECT * from (select * ,row_number() over(order by id) as rownumber from village_cadres  WHERE village_cadres.cadresType = '" +cadresType+ "' and village_cadres.hasRetire = '0' and village_cadres.isDelete = '0' ";
        resSql.append(tempSql);
        resSql.append(s);
        //2.增加子查询条件，使用IN
        Set<String> strings = new HashSet<>();
        for (String key : map.keySet()){
            if (!key.equals("VillageCadres") && map.get(key) != null){
                Set set = createChildSql(map.get(key),key);
                strings.addAll(set);
            }
        }
        String inSql = "";
        if (!CollectionUtils.isEmpty(strings)){
            inSql += "and id in (";
            for (String s1 : strings){
                inSql +=s1+",";
            }
            inSql += ")";
        }
        resSql.append(inSql);
        resSql.append(") a WHERE  a.rownumber BETWEEN ");
        resSql.append(page*size+1);
        resSql.append(" and ");
        resSql.append(page*size+size);
        log.info("-------------------主查询"+resSql.toString());
        StringBuilder count = new StringBuilder("select count(1) as val,'全部' as name from village_cadres  WHERE village_cadres.cadresType = '" +cadresType+ "' and village_cadres.hasRetire = '0' and village_cadres.isDelete = '0' ");
        count.append(s);
        count.append(inSql);
        List<StatisticsVO> statistics = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,count.toString());

        List<VillageCadresStatisticsVO> villageCadresStatisticsVOS = (List<VillageCadresStatisticsVO>)findAllBySql(VillageCadresStatisticsVO.class,resSql.toString());
        return new PageImpl<>(villageCadresStatisticsVOS,pageable,Long.valueOf(statistics.get(0).getVal()));
    }

    @Override
    public String export(ExportDTO exportDTO, String cadresType, String districtId) {
        if (StringUtils.isEmpty(cadresType)) {
            cadresType = "SECRETARY";
        }
        //1.查询列表数据
        StringBuilder s = new StringBuilder();
        s.append(createSql(exportDTO.getSearchFields(), districtId));
        StringBuilder resSql = new StringBuilder();
        resSql.append("select * from village_cadres  WHERE village_cadres.cadresType = '" + cadresType + "' and " +
                "village_cadres.hasRetire = '0' and village_cadres.isDelete = '0' ");
        resSql.append(s);
        return generateFileUrl(exportDTO, resSql.toString());
    }

    public String generateFileUrl(ExportDTO exportDTO, String resSql) {
        List<VillageCadresStatisticsVO> villageCadresStatisticsVOS = findAllBySql(VillageCadresStatisticsVO.class, resSql);
        String url = null;
        //设置excel列头信息
        String[] rowsName = new String[exportDTO.getExportFields().size()+1];
        List<VillageCadresStatisticsSearchable> exports = exportDTO.getExportFields();
        rowsName[0] = "序号";
        //设置每列对应的值
        List<Object[]> dataList = new ArrayList<>();
        Object[] objs ;
        int index = 1;
        for (VillageCadresStatisticsVO v : villageCadresStatisticsVOS){
            objs = new Object[rowsName.length];
            objs[0] = index++;
            for (int i = 0;i < exports.size();i++){
                rowsName[i+1] = exports.get(i).getFiledDesc();
                try {
                    Field field = v.getClass().getDeclaredField(exports.get(i).getFiledName());
                    field.setAccessible(true);
                    Object obj = field.get(v);
                    if (StringUtils.isEmpty(obj)){
                        objs[i+1] = "-";
                    }else {
                        objs[i+1] = obj;
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            dataList.add(objs);
        }
        HSSFWorkbook workbook = export("村书记信息",rowsName,dataList);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os); //wb是HSSFWorkbook对象
            ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
            url = fdfsService.uploadFile(is,Long.valueOf(is.available()),"村书记信息导出.xls");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    public List<StatisticsNotIntegerVO> getRewardsStatisticsByType(String type,String cadresType, String districtId) {
        String sql = "";
        String fieldName = "parentDistrictName";
        if (StringUtils.isEmpty(districtId)) {
            districtId = "01";
        }
        if (!StringUtils.isEmpty(districtId) && districtId.length() >= 4) {
            fieldName = "districtName";
        }
        if ("1".equals(type)) {
            sql = "SELECT cast(avg(cast(Isnull(reward.totalReward, 0) as  decimal(10,2))) as decimal(10,2)) as val, " +
                    "cadres." +fieldName+ " as name from Reward_Info reward join village_cadres cadres on " +
                    "reward.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and cadres.isDelete = '0' " +
                    "and hasRetire = '0' AND reward.achieveTime IS NOT null";
        } else if ("2".equals(type)){
            sql = "SELECT cast(avg(cast(Isnull(reward.basicReward, 0) as decimal(10,2))) as decimal(10,2)) as val, " +
                    "cadres." +fieldName+ " as name from Reward_Info reward join village_cadres cadres on " +
                    "reward.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and cadres.isDelete = '0' " +
                    "and hasRetire = '0' AND reward.achieveTime IS NOT null";
        }else if ("3".equals(type)){
            sql = "SELECT cast(avg(cast(Isnull(reward.basicReward, 0) as decimal(10,2))) as decimal(10,2)) as val, " +
                    "cadres." +fieldName+ " as name from Reward_Info reward join village_cadres cadres on " +
                    "reward.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and cadres.isDelete = '0'" +
                    " and hasRetire = '0' AND reward.achieveTime IS NOT null and cadres.personnelType = '全职村干部' ";

        }else if ("4".equals(type)){
            sql = "SELECT cast(avg(cast(Isnull(reward.basicReward, 0) as decimal(10,2))) as decimal(10,2)) as val, " +
                    "cadres." +fieldName+ " as name from Reward_Info reward join village_cadres cadres on " +
                    "reward.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and cadres.isDelete = '0' " +
                    "and hasRetire = '0' AND reward.achieveTime IS NOT null  and cadres.personnelType = '公务员' ";

        }else if ("5".equals(type)){
            sql = "SELECT cast(avg(cast(Isnull(reward.basicReward, 0) as  decimal(10,2))) as decimal(10,2)) as val, " +
                    "cadres." +fieldName+ " as name from Reward_Info reward join village_cadres cadres on " +
                    "reward.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and cadres.isDelete = '0' " +
                    "and hasRetire = '0' AND reward.achieveTime IS NOT null  and cadres.personnelType = '事业编制' ";

        }else if ("6".equals(type)){
            sql = "SELECT cast(avg(cast(Isnull(reward.reviewReward, 0) as  decimal(10,2))) as decimal(10,2)) as val, " +
                    "cadres." +fieldName+ " as name from Reward_Info reward join village_cadres cadres on " +
                    "reward.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and cadres.isDelete = '0' " +
                    "and hasRetire = '0' AND reward.achieveTime IS NOT null";

        }else if ("7".equals(type)){
            sql = "SELECT cast(avg(cast(Isnull(reward.otherReward, 0) as  decimal(10,2))) as decimal(10,2)) as val, " +
                    "cadres." +fieldName+ " as name from Reward_Info reward join village_cadres cadres on " +
                    "reward.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and cadres.isDelete = '0' " +
                    "and hasRetire = '0' AND reward.achieveTime IS NOT null";
        }else if ("8".equals(type)){
            sql = "SELECT cast(avg(cast(Isnull(reward.des, 0) as  decimal(10,2))) as decimal(10,2)) as val, " +
                    "cadres." +fieldName+ " as name from Reward_Info reward join village_cadres cadres on " +
                    "reward.cadresId = cadres.id and cadres.cadresType like '"+cadresType+"%' and cadres.isDelete = '0' " +
                    "and hasRetire = '0' AND reward.achieveTime IS NOT null";
        }
        sql += " and cadres.districtId like '" + districtId + "%' group by cadres." + fieldName;
        List<StatisticsNotIntegerVO> totals = findAllBySql(StatisticsNotIntegerVO.class, sql);//平均总报酬
        return totals;
    }

    //拼接字符串
    private StringBuilder createSql(List<VillageCadresStatisticsSearchable> villageCadresStatisticsSearchables, String districtId){
        StringBuilder s = new StringBuilder();
        for (VillageCadresStatisticsSearchable v : villageCadresStatisticsSearchables){
            StringBuilder filedName = new StringBuilder();
            if (v.getFiledType().equals("date")){
                filedName.append("DATEDIFF(YEAR,").append(v.getFiledName()).append(",GETDATE())");
            }else {
                filedName.append(v.getFiledName());
            }
            StringBuilder strToNumberMin = new StringBuilder("(CASE WHEN IsNumeric('").append(v.getValueMin()).append("') = 1 THEN cast('").append(v.getValueMin()).append("'  as int) ELSE -1 END)");
            StringBuilder strToNumberMax = new StringBuilder("(CASE WHEN IsNumeric('").append(v.getValueMax()).append("') = 1 THEN cast('").append(v.getValueMax()).append("'  as int) ELSE -1 END)");
            switch(v.getComparison()){
                case "大于" :
                    s.append(" and ").append(filedName).append(" > ").append(strToNumberMin);
                    break;
                case "小于" :
                    s.append(" and ").append(filedName).append(" < ").append(strToNumberMin);
                    break;
                case "大于等于" :
                    s.append(" and ").append(filedName).append(" >= ").append(strToNumberMin);
                    break;
                case "小于等于" :
                    s.append(" and ").append(filedName).append(" <= ").append(strToNumberMin);
                    break;
                case "等于" :
                    s.append(" and ").append(filedName).append(" = '").append(v.getValueMin()).append("'");
                    break;
                case "模糊匹配" :
                    s.append(" and ").append(filedName).append(" like ").append(" '%").append(v.getValueMin()).append("%' ");
                    break;
                case "在范围内" :
                    if (!StringUtils.isEmpty(v.getValueMin())){
                        s.append(" and ").append(filedName).append(" >= ").append(strToNumberMin);
                    }
                    if (!StringUtils.isEmpty(v.getValueMax())){
                        s.append(" and ").append(filedName).append(" <= ").append(strToNumberMax);
                    }
                    break;
            }
        }
        if (StringUtils.isEmpty(districtId)) {
            districtId = "01";
        }
        s.append(" and districtId like '").append(districtId).append("%'");
        return s;
    }

    private Set<String> createChildSql(List<VillageCadresStatisticsSearchable> villageCadresStatisticsSearchables, String tableName){
        StringBuilder s = new StringBuilder();
        s.append("select cadresId from "+tableName + " where 1=1");
        for (VillageCadresStatisticsSearchable v : villageCadresStatisticsSearchables){
            StringBuilder filedName = new StringBuilder();
            StringBuilder strToNumberMin = new StringBuilder("(CASE WHEN IsNumeric('").append(v.getValueMin()).append("') = 1 THEN cast('").append(v.getValueMin()).append("'  as deciaml(10,2)) ELSE -1 END)");
            StringBuilder strToNumberMax = new StringBuilder("(CASE WHEN IsNumeric('").append(v.getValueMax()).append("') = 1 THEN cast('").append(v.getValueMax()).append("'  as deciaml(10,2)) ELSE -1 END)");
            switch(v.getComparison()){
                case "大于" :
                    s.append(" and (").append("cast(Isnull("+filedName+", 0) as  decimal(10,2))").append(" > ").append(strToNumberMin);
                    break;
                case "小于" :
                    s.append(" and (").append("cast(Isnull("+filedName+", 0) as  decimal(10,2))").append(" < ").append(strToNumberMin);
                    break;
                case "大于等于" :
                    s.append(" and (").append("cast(Isnull("+filedName+", 0) as  decimal(10,2))").append(" >= ").append(strToNumberMin);
                    break;
                case "小于等于" :
                    s.append(" and (").append("cast(Isnull("+filedName+", 0) as  decimal(10,2))").append(" <= ").append(strToNumberMin);
                    break;
                case "等于" :
                    s.append(" and (").append("cast(Isnull("+filedName+", 0) as  decimal(10,2))").append(" = '").append(v.getValueMin()).append("'");
                    break;
                case "模糊匹配" :
                    s.append(" and (").append(filedName).append(" like ").append(" '%").append(v.getValueMin()).append("%' ");
                    break;
                case "在范围内" :
                    if (!StringUtils.isEmpty(v.getValueMin()) && !StringUtils.isEmpty(v.getValueMin())){
                        s.append(" and (").append("cast(Isnull("+filedName+", 0) as  decimal(10,2))").append(" >= ").append(strToNumberMin);
                        s.append(" and ").append("cast(Isnull("+filedName+", 0) as  decimal(10,2))").append(" <= ").append(strToNumberMax);
                    }else {
                        if (!StringUtils.isEmpty(v.getValueMin()) ){
                            s.append(" and (").append("cast(Isnull("+filedName+", 0) as  decimal(10,2))").append(" >= ").append(strToNumberMin);
                        }
                        if (!StringUtils.isEmpty(v.getValueMax())){
                            s.append(" and (").append("cast(Isnull("+filedName+", 0) as  decimal(10,2))").append(" <= ").append(strToNumberMax);
                        }
                    }
                    break;
            }
            if (!StringUtils.isEmpty(v.getSearchYear())){
                s.append(" and YEAR(achieveTime) = "+v.getSearchYear());
            }
            s.append(")");
        }
        log.info("------------子查询SQL："+s.toString());
        List<ChildSqlVO> childSqlVOS = findAllBySql(ChildSqlVO.class,s.toString());
        Set<String> collect = childSqlVOS.stream().map(ChildSqlVO::getCadresId).collect(Collectors.toSet());
        return collect;
    }
}
