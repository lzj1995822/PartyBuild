package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ExaExamine;
import com.cloudkeeper.leasing.identity.repository.ExaExamineRepository;
import com.cloudkeeper.leasing.identity.service.ExaExamineService;
import com.cloudkeeper.leasing.identity.vo.ExamScoreVO;
import com.cloudkeeper.leasing.identity.vo.PassPercentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 考核审核 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExaExamineServiceImpl extends BaseServiceImpl<ExaExamine> implements ExaExamineService {

    /** 考核审核 repository */
    private final ExaExamineRepository exaExamineRepository;

    @Override
    protected BaseRepository<ExaExamine> getBaseRepository() {
        return exaExamineRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("remark", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public  List<ExamScoreVO> scoreTown(String year) {
        String sql = "SELECT SUM(exam)/count(districtName) exam,districtName town from  " +
                " (SELECT S000.cun,S000.exam,sysd.districtName from  " +
                " ( " +
                "SELECT " +
                " exam, " +
                " cun, " +
                " attachTo, " +
                " districtId, " +
                " exscore " +
                "FROM " +
                " ( " +
                " SELECT S0.cun,S0.attachTo,S0.districtId,examine.score exscore,S0.exam+isnull(examine.score,0) exam from  " +
                " ( " +
                "SELECT SUM " +
                " ( score ) exam, " +
                " districtName cun, " +
                " attachTo, " +
                " districtId  " +
                "FROM " +
                " ( " +
                "SELECT " +
                " es.activityId, " +
                " es.score, " +
                " es.createTime, " +
                " sd.districtName, " +
                " sd.attachTo, " +
                " sd.id districtId " +
                "FROM " +
                " EXA_Score es " +
                " LEFT JOIN SYS_District sd ON es.organizationId = sd.id  " +
                "WHERE " +
                " sd.districtLevel = 3  " +
                " AND sd.isDelete = 0  " +
                " AND es.createTime BETWEEN '"+year+"-01-01 00:00:00' and '"+year+"-12-31 23:59:59' " +
                " ) S1  " +
                "GROUP BY " +
                " districtName, " +
                " districtId, " +
                " attachTo " +
                " )S0 left join  " +
                " ( select sum(score) score,organizationId from  EXA_Examine  " +
                " where createTime between '"+year+"-01-01 00:00:00' and '"+year+"-12-31 23:59:59' group by organizationId) examine " +
                "  on S0.districtId = examine.organizationId " +
                "   " +
                " ) a " +
                " ) S000,SYS_District sysd where S000.attachTo = sysd.districtId " +
                ") S001  " +
                " GROUP BY districtName ORDER BY exam desc";
        List<ExamScoreVO> list = super.findAllBySql(ExamScoreVO.class, sql);
        return  list;
    }

    @Override
    public List<ExamScoreVO> scoreCun(String year,String town) {
        if(StringUtils.isEmpty(town) || StringUtils.isEmpty(year) ){
            return null;
        }
        String sql = "SELECT S3.exam,S3.cun,S3.districtName town from( " +
                "SELECT " +
                " a.exam, " +
                " a.cun, " +
                " a.attachTo, " +
                " a.districtId, " +
                " a.exscore, " +
                " district.districtName " +
                "FROM " +
                " ( " +
                " SELECT S0.cun,S0.attachTo,S0.districtId,examine.score exscore,S0.exam+isnull(examine.score,0) exam from  " +
                " ( " +
                "SELECT SUM " +
                " ( score ) exam, " +
                " districtName cun, " +
                " attachTo, " +
                " districtId  " +
                "FROM " +
                " ( " +
                "SELECT " +
                " es.activityId, " +
                " es.score, " +
                " es.createTime, " +
                " sd.districtName, " +
                " sd.attachTo, " +
                " sd.id districtId " +
                "FROM " +
                " EXA_Score es " +
                " LEFT JOIN SYS_District sd ON es.organizationId = sd.id  " +
                "WHERE " +
                " sd.districtLevel = 3  " +
                " AND sd.isDelete = 0  " +
                " AND es.createTime BETWEEN '"+year+"-01-01 00:00:00' and '"+year+"-12-31 23:59:59' " +
                " ) S1  " +
                "GROUP BY " +
                " districtName, " +
                " districtId, " +
                " attachTo " +
                " )S0 left join  " +
                " ( select sum(score) score,organizationId from  EXA_Examine  " +
                " where createTime between '"+year+"-01-01 00:00:00' and '"+year+"-12-31 23:59:59' group by organizationId) examine " +
                "  on S0.districtId = examine.organizationId " +
                " ) a,SYS_District district where a.attachTo = district.districtId " +
                " ) S3 where districtName = '"+town+"' " +
                " order by S3.exam desc";
        List<ExamScoreVO> list = super.findAllBySql(ExamScoreVO.class, sql);
        return  list;
    }

}
