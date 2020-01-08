package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ParMember;
import com.cloudkeeper.leasing.identity.repository.ParMemberRepository;
import com.cloudkeeper.leasing.identity.service.ParMemberService;
import com.cloudkeeper.leasing.identity.vo.ParMemberChartsVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 党员管理 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParMemberServiceImpl extends BaseServiceImpl<ParMember> implements ParMemberService {

    /** 党员管理 repository */
    private final ParMemberRepository parMemberRepository;

    @Override
    protected BaseRepository<ParMember> getBaseRepository() {
        return parMemberRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("sex", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("nativePlace", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("nation", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("identityCard", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("company", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("education", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("academicDegree", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("contact", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("isDifficulty", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("districtId", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("isVolunteer", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public Integer countAll(String districtId) {
        Integer integer = parMemberRepository.countAllByDistrictIdStartingWith(districtId);
        return integer;
    }

    @Override
    public List<ParMemberChartsVo> statisticsSex(String districtId) {
        String sql = "select name,sum(sum) as sum,sum(ratio) as ratio from (\n" +
                "select a.sex as name,a.sum,cast((a.sum+0.0) / b.total as decimal(20,2)) as ratio from\n" +
                "(SELECT sex,count(sex) as sum from PAR_Member\n" +
                "where districtId like '"+districtId+"%'\n" +
                "group by sex) as a,\n" +
                "(SELECT count(sex) as total from PAR_Member where districtId like '"+districtId+"%') as b\n" +
                "UNION all\n" +
                "select '男' as name ,0 as sum,0 as ratio\n" +
                "UNION all\n" +
                "select '女' as name ,0 as sum,0 as ratio) as temp\n" +
                "group by name";
        List<ParMemberChartsVo> list = super.findAllBySql(ParMemberChartsVo.class, sql);
        return list;
    }

    @Override
    public List<ParMemberChartsVo> statisticsAge(String districtId) {
        String sql ="select name ,SUM(sum) as sum from\n" +
                "((select\n" +
                "case\n" +
                "when age>=18 and age<30 then '18-30'\n" +
                "when age>=30 and age<40 then '30-40'\n" +
                "when age>=40 and age<50 then '40-50'\n" +
                "when age>=50 and age<70 then '50-70' \n" +
                "when age>=70 then '70以上'\n" +
                "end as name,\n" +
                "COUNT(1) as sum\n" +
                "from (\n" +
                "select name,(YEAR(GETDATE())-YEAR(birth))as age from PAR_Member \n" +
                "where districtId like '"+districtId+"%') temp GROUP BY \n" +
                "case\n" +
                "when age>=18 and age<30 then '18-30'\n" +
                "when age>=30 and age<40 then '30-40'\n" +
                "when age>=40 and age<50 then '40-50'\n" +
                "when age>=50 and age<70 then '50-70' \n" +
                "when age>=70 then '70以上'\n" +
                "end\n" +
                ")\n" +
                "UNION all\n" +
                "SELECT '18-20' as name,0 as sum\n" +
                "UNION all\n" +
                "SELECT '30-40' as name,0 as sum\n" +
                "UNION all\n" +
                "SELECT '40-50' as name,0 as sum\n" +
                "UNION all\n" +
                "SELECT '50-70' as name,0 as sum\n" +
                "UNION all\n" +
                "SELECT '70以上' as name,0 as sum)as temp\n" +
                "group by name\n" +
                "ORDER BY name asc";
        List<ParMemberChartsVo> list = super.findAllBySql(ParMemberChartsVo.class, sql);
        return list;
    }

    @Override
    public List<ParMemberChartsVo> statisticsBranch(String districtId) {
        String sql = "select districtName as name , sum(total) as sum from\n" +
                "(Select s.districtName,count(s.districtName) as total from PAR_Member as p\n" +
                "LEFT JOIN sys_district as s on s.districtId = p.districtId\n" +
                "where p.districtId like '"+districtId+"%'\n" +
                "GROUP BY  s.districtName\n" +
                "UNION all\n" +
                "select s1.districtName,0 as total  from sys_district as s1\n" +
                "where s1.districtId like '"+districtId+"%') as temp\n" +
                "GROUP BY districtName";
        List<ParMemberChartsVo> list = super.findAllBySql(ParMemberChartsVo.class, sql);
        return list;
    }
}