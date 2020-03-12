package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.MessageCenter;
import com.cloudkeeper.leasing.identity.service.StatisticsService;
import com.cloudkeeper.leasing.identity.vo.StatisticsClassifyVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsListVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsNotIntegerVO;
import com.cloudkeeper.leasing.identity.vo.StatisticsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticsServiceImpl extends BaseServiceImpl implements StatisticsService  {
    @Override
    protected BaseRepository<MessageCenter> getBaseRepository() {
        return null;
    }

    @Override
    public List<StatisticsVO> getSxStatistics(String districtId) {
        String sql = "select count(1) as val,'男' as name from village_cadres where cadresType = 'SECRETARY' and districtId like '"+districtId+"%' and sex = '男'";
        sql += " UNION all ";
        sql += "select count(1) as val,'女' as name from village_cadres where cadresType = 'SECRETARY' and districtId like '"+districtId+"%' and sex = '女'";
        List<StatisticsVO> list = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        return list;
    }

    @Override
    public List<StatisticsVO> getAgeStatistics(String districtId) {

        String sql = "select  cast(isNULL(MAX(FLOOR(DATEDIFF(DY, substring(IDcardNumber,7,4), GETDATE()) / 365.25)),0) as int) as val,'最高年龄' as name from village_cadres where cadresType = 'SECRETARY' and districtId like '"+districtId+"%'\n" +
                "UNION all\n" +
                "select  cast(isNULL(MIN(FLOOR(DATEDIFF(DY, substring(IDcardNumber,7,4), GETDATE()) / 365.25)),0) as int) as val,'最低年龄' as name from village_cadres where cadresType = 'SECRETARY' and districtId like '"+districtId+"%'\n" +
                "UNION all\n" +
                "select  isNULL(cast(round(avg(DATEDIFF(DY, substring(IDcardNumber,7,4), GETDATE()) / 365.25),0) as int),0) as val,'平均年龄' as name from village_cadres where cadresType = 'SECRETARY' and districtId like '"+districtId+"%'\n" +
                "UNION all\n" +
                "select  count(1) as val,'35周岁以下' as name from village_cadres where cadresType = 'SECRETARY' and FLOOR(DATEDIFF(DY, substring(IDcardNumber,7,4), GETDATE()) / 365.25) <= 35 and districtId like '"+districtId+"%'\n" +
                "UNION all\n" +
                "select  count(1) as val,'35-50周岁' as name from village_cadres where cadresType = 'SECRETARY' and FLOOR(DATEDIFF(DY, substring(IDcardNumber,7,4), GETDATE()) / 365.25) > 35 and FLOOR(DATEDIFF(DY, substring(IDcardNumber,7,4), GETDATE()) / 365.25) <= 50 and districtId like '"+districtId+"%'\n" +
                "UNION all\n" +
                "select  count(1) as val,'50周岁以上' as name from village_cadres where cadresType = 'SECRETARY' and FLOOR(DATEDIFF(DY, substring(IDcardNumber,7,4), GETDATE()) / 365.25) > 50 and districtId like '"+districtId+"%'\n" +
                "\n";
        List<StatisticsVO> list = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        return list;
    }

    @Override
    public List<StatisticsVO> getEduStatistics(String districtId) {
        String sql = "SELECT count(1) as val,'研究生' as name from village_cadres WHERE  cadresType = 'SECRETARY' and education = '研究生' and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'本科' as name from village_cadres WHERE  cadresType = 'SECRETARY' and education = '本科' and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'大专' as name from village_cadres WHERE  cadresType = 'SECRETARY' and education = '大专' and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'高中及以下' as name from village_cadres WHERE  cadresType = 'SECRETARY' and education = '高中及以下' and districtId like '"+districtId+"%'";
        List<StatisticsVO> list = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        return list;
    }

    @Override
    public List<StatisticsVO> getcadresTypeStatistics(String districtId) {
        String sql = "SELECT count(1) as val,'公务员' as name from village_cadres WHERE cadresType = 'SECRETARY' and personnelType = '公务员' and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'事业编制' as name from village_cadres WHERE  cadresType = 'SECRETARY' and personnelType = '事业编制' and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'企业主' as name from village_cadres WHERE  cadresType = 'SECRETARY' and personnelType = '企业主' and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'全职村干部' as name from village_cadres WHERE  cadresType = 'SECRETARY' and personnelType = '全职村干部' and districtId like '"+districtId+"%'";
        List<StatisticsVO> list = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        return list;
    }

    @Override
    public List<StatisticsVO> getServingYearStatistics(String districtId) {
        String sql = "SELECT count(1) as val,'不满1年' as name from village_cadres WHERE cadresType = 'SECRETARY' and  onDutyTime < 1 and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'1-5年' as name from village_cadres WHERE cadresType = 'SECRETARY' and  onDutyTime >= 1 and onDutyTime < 5 and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'5-10年' as name from village_cadres WHERE cadresType = 'SECRETARY' and  onDutyTime >= 5 and onDutyTime < 10 and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'10-15年' as name from village_cadres WHERE cadresType = 'SECRETARY' and  onDutyTime >= 10 and onDutyTime < 15 and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'15-20年' as name from village_cadres WHERE cadresType = 'SECRETARY' and  onDutyTime >= 15 and onDutyTime < 20 and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'20年以上' as name from village_cadres WHERE cadresType = 'SECRETARY' and  onDutyTime >= 20 and districtId like '"+districtId+"%'";
        List<StatisticsVO> list = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        return list;
    }

    @Override
    public List<StatisticsVO> getRankStatistics(String districtId) {

        String sql = "SELECT count(1) as val,'一级村书记' as name from village_cadres WHERE cadresType = 'SECRETARY' and  rank = '一级村书记' and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'二级村书记' as name from village_cadres WHERE cadresType = 'SECRETARY' and  rank = '二级村书记' and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'三级村书记' as name from village_cadres WHERE cadresType = 'SECRETARY' and  rank = '三级村书记' and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'四级村书记' as name from village_cadres WHERE cadresType = 'SECRETARY' and  rank = '四级村书记' and districtId like '"+districtId+"%'" +
                "UNION all\n" +
                "SELECT count(1) as val,'五级村书记' as name from village_cadres WHERE cadresType = 'SECRETARY' and  rank = '五级村书记' and districtId like '"+districtId+"%'";
        List<StatisticsVO> list = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        return list;
    }

    @Override
    public List<StatisticsClassifyVO> getSalaryStatistics(String districtId) {
        String sql = "SELECT cast(avg(cast(Isnull(reward.basicReward, 0) as  decimal(10,2))) as decimal(5,2)) as val,DateName(year,reward.achieveTime) as name from Reward_Info reward join village_cadres cadres on reward.cadresId = cadres.id and cadres.cadresType = 'SECRETARY'and cadres.districtId like '"+districtId+"%' GROUP BY DateName(year,reward.achieveTime)";
        List<StatisticsNotIntegerVO> basics = (List<StatisticsNotIntegerVO>)findAllBySql(StatisticsNotIntegerVO.class,sql);//年平均基本报酬
        sql = "SELECT cast(avg(cast(Isnull(reward.reviewReward, 0) as  decimal(10,2))) as decimal(5,2)) as val,DateName(year,reward.achieveTime) as name from Reward_Info reward join village_cadres cadres on reward.cadresId = cadres.id and cadres.cadresType = 'SECRETARY' and cadres.districtId like '"+districtId+"%' GROUP BY DateName(year,reward.achieveTime)";
        List<StatisticsNotIntegerVO> reviews = (List<StatisticsNotIntegerVO>)findAllBySql(StatisticsNotIntegerVO.class,sql);//年平均考核报酬
        sql = "SELECT cast(avg(cast(Isnull(reward.otherReward, 0) as  decimal(10,2))) as decimal(5,2)) as val,DateName(year,reward.achieveTime) as name from Reward_Info reward join village_cadres cadres on reward.cadresId = cadres.id and cadres.cadresType = 'SECRETARY' and cadres.districtId like '"+districtId+"%' GROUP BY DateName(year,reward.achieveTime)";
        List<StatisticsNotIntegerVO> others =  (List<StatisticsNotIntegerVO>)findAllBySql(StatisticsNotIntegerVO.class,sql);//年平均增收报酬
        sql = "SELECT cast(avg(cast(Isnull(reward.totalReward, 0) as  decimal(10,2))) as decimal(5,2)) as val,DateName(year,reward.achieveTime) as name from Reward_Info reward join village_cadres cadres on reward.cadresId = cadres.id and cadres.cadresType = 'SECRETARY' and cadres.districtId like '"+districtId+"%' GROUP BY DateName(year,reward.achieveTime)";
        List<StatisticsNotIntegerVO> totals =  (List<StatisticsNotIntegerVO>)findAllBySql(StatisticsNotIntegerVO.class,sql);//年平均报酬
        StatisticsClassifyVO statisticsClassifyVO1 = new StatisticsClassifyVO();
        statisticsClassifyVO1.setName("年平均基本报酬");
        statisticsClassifyVO1.setStatistics(basics);
        StatisticsClassifyVO statisticsClassifyVO2 = new StatisticsClassifyVO();
        statisticsClassifyVO2.setName("年平均考核报酬");
        statisticsClassifyVO2.setStatistics(reviews);
        StatisticsClassifyVO statisticsClassifyVO3 = new StatisticsClassifyVO();
        statisticsClassifyVO3.setName("年平均增收报酬");
        statisticsClassifyVO3.setStatistics(others);
        StatisticsClassifyVO statisticsClassifyVO4 = new StatisticsClassifyVO();
        statisticsClassifyVO4.setName("年平均报酬");
        statisticsClassifyVO4.setStatistics(others);
        List<StatisticsClassifyVO> list = new ArrayList<>();
        list.add(statisticsClassifyVO1);
        list.add(statisticsClassifyVO2);
        list.add(statisticsClassifyVO3);
        list.add(statisticsClassifyVO4);
        return list;
    }

    @Override
    public List<StatisticsVO> getPartyStandingStatistics(String districtId) {
        String sql = "SELECT count(1) as val, '不满1年' as name FROM village_cadres WHERE DATEDIFF(YEAR,partyTime,GETDATE()) < 1 and cadresType = 'SECRETARY' and districtId like '"+districtId+"%'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val, '1-5年' as name FROM village_cadres WHERE DATEDIFF(YEAR,partyTime,GETDATE()) < 5 and DATEDIFF(YEAR,partyTime,GETDATE()) >= 1 and cadresType = 'SECRETARY' and districtId like '"+districtId+"%'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val, '5-10年' as name FROM village_cadres WHERE DATEDIFF(YEAR,partyTime,GETDATE()) < 10 and DATEDIFF(YEAR,partyTime,GETDATE()) >= 5 and cadresType = 'SECRETARY' and districtId like '"+districtId+"%'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val, '10-15年' as name FROM village_cadres WHERE DATEDIFF(YEAR,partyTime,GETDATE()) < 15 and DATEDIFF(YEAR,partyTime,GETDATE()) >= 10 and cadresType = 'SECRETARY' and districtId like '"+districtId+"%'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val, '15-20年' as name FROM village_cadres WHERE DATEDIFF(YEAR,partyTime,GETDATE()) < 20 and DATEDIFF(YEAR,partyTime,GETDATE()) >= 15 and cadresType = 'SECRETARY' and districtId like '"+districtId+"%'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val, '20年以上' as name FROM village_cadres WHERE DATEDIFF(YEAR,partyTime,GETDATE()) >= 20 and cadresType = 'SECRETARY' and districtId like '"+districtId+"%'";
        List<StatisticsVO> list = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        return list;
    }

    @Override
    public List<StatisticsListVO> getRewardsStatistics(String districtId) {
        String sql = "SELECT count(1) as val,'句容市级' as name from Honour_Info honour join village_cadres cadres on honour.cadresId = cadres.id and cadres.cadresType = 'SECRETARY' and cadres.districtId like '"+districtId+"%' and rewardsType = '荣誉' and honourDescription = '句容市级'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val,'镇江市级' as name from Honour_Info honour join village_cadres cadres on honour.cadresId = cadres.id and cadres.cadresType = 'SECRETARY' and cadres.districtId like '"+districtId+"%' and rewardsType = '荣誉' and honourDescription = '镇江市级'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val,'省级' as name from Honour_Info honour join village_cadres cadres on honour.cadresId = cadres.id and cadres.cadresType = 'SECRETARY' and cadres.districtId like '"+districtId+"%' and rewardsType = '荣誉' and honourDescription = '省级'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val,'党中央国务院' as name from Honour_Info honour join village_cadres cadres on honour.cadresId = cadres.id and cadres.cadresType = 'SECRETARY' and cadres.districtId like '"+districtId+"%' and rewardsType = '荣誉' and honourDescription = '党中央国务院'";
        List<StatisticsVO> rys = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        sql = "SELECT count(1) as val,'句容市级' as name from Honour_Info honour join village_cadres cadres on honour.cadresId = cadres.id and cadres.cadresType = 'SECRETARY' and cadres.districtId like '"+districtId+"%' and rewardsType = '处分' and honourDescription = '句容市级'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val,'镇江市级' as name from Honour_Info honour join village_cadres cadres on honour.cadresId = cadres.id and cadres.cadresType = 'SECRETARY' and cadres.districtId like '"+districtId+"%' and rewardsType = '处分' and honourDescription = '镇江市级'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val,'省级' as name from Honour_Info honour join village_cadres cadres on honour.cadresId = cadres.id and cadres.cadresType = 'SECRETARY' and cadres.districtId like '"+districtId+"%' and rewardsType = '处分' and honourDescription = '省级'\n" +
                "UNION ALL\n" +
                "SELECT count(1) as val,'党中央国务院' as name from Honour_Info honour join village_cadres cadres on honour.cadresId = cadres.id and cadres.cadresType = 'SECRETARY' and cadres.districtId like '"+districtId+"%' and rewardsType = '处分' and honourDescription = '党中央国务院'";
        List<StatisticsVO> cfs = (List<StatisticsVO>)findAllBySql(StatisticsVO.class,sql);
        List<StatisticsListVO> statisticsListVOS = new ArrayList<>();
        StatisticsListVO statisticsListVO1 = new StatisticsListVO();
        StatisticsListVO statisticsListVO2 = new StatisticsListVO();
        statisticsListVO1.setName("荣誉");
        statisticsListVO1.setStatistics(rys);
        statisticsListVO2.setName("处分");
        statisticsListVO2.setStatistics(cfs);
        statisticsListVOS.add(statisticsListVO1);
        statisticsListVOS.add(statisticsListVO2);
        return statisticsListVOS;
    }
}
