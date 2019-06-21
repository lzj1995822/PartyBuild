package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ExaScore;
import com.cloudkeeper.leasing.identity.repository.ExaScoreRepository;
import com.cloudkeeper.leasing.identity.service.ExaScoreService;
import com.cloudkeeper.leasing.identity.vo.ExamScorePercentVO;
import com.cloudkeeper.leasing.identity.vo.ExamScoreVO;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.source.spi.Sortable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 考核积分 service
 * @author lxw
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExaScoreServiceImpl extends BaseServiceImpl<ExaScore> implements ExaScoreService {

    /** 考核积分 repository */
    private final ExaScoreRepository exaScoreRepository;

    @Override
    protected BaseRepository<ExaScore> getBaseRepository() {
        return exaScoreRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public List<ExamScoreVO> scoreCun(Pageable pageable, String sort,String year) {
//        String sql = "SELECT TOP " +
//                " "+pageable.getPageSize()+" *  " +
//                "FROM " +
//                " ( " +
//                "SELECT " +
//                " exam, " +
//                " row_number () OVER ( ORDER BY exam "+sort+" ) AS row_number, " +
//                "COUNT(*)OVER() AS total, "+
//                " cun " +
//                "FROM " +
//                " ( " +
//                "SELECT SUM " +
//                " ( score ) exam, " +
//                " districtName cun, " +
//                " attachTo  " +
//                "FROM " +
//                " ( " +
//                "SELECT " +
//                " es.organizationId, " +
//                " es.activityId, " +
//                " es.score, " +
//                " es.createTime, " +
//                " sd.districtName, " +
//                " sd.attachTo " +
//                "FROM " +
//                " EXA_Score es " +
//                " LEFT JOIN SYS_District sd ON es.organizationId = sd.id  " +
//                "WHERE " +
//                " sd.districtLevel = 3  " +
//                " AND sd.isDelete = 0  " +
//                "AND es.createTime BETWEEN '"+year+"-01-01 00:00:00' and '"+year+"-12-31 23:59:59' "+
//                " ) S1  " +
//                "GROUP BY " +
//                " districtName, " +
//                " attachTo  " +
//                " ) a  " +
//                " ) t  " +
//                "WHERE " +
//                " row_number > ( "+pageable.getPageNumber()+" ) * "+pageable.getPageSize()+"";

        String sql = "SELECT TOP "+pageable.getPageSize()+" *  " +
                "FROM " +
                " ( " +
                "SELECT " +
                " exam, " +
                " row_number () OVER ( ORDER BY exam "+sort+" ) AS row_number, " +
                " COUNT(*)OVER() AS total, " +
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
                " AND es.createTime BETWEEN '"+year+"-01-01 00:00:00' and '"+year+"-12-31 23:59:59' "+
                " ) S1  " +
                "GROUP BY " +
                " districtName, " +
                " districtId, " +
                " attachTo " +
                " )S0 left join  " +
                " ( select sum(score) score,organizationId from  EXA_Examine where createTime between '"+year+"-01-01 00:00:00' and '"+year+"-12-31 23:59:59' group by organizationId) examine " +
                "  on S0.districtId = examine.organizationId " +
                " ) a  " +
                " ) t  " +
                "WHERE " +
                " row_number > ( "+pageable.getPageNumber()+" ) * "+pageable.getPageSize()+"";
        List<ExamScoreVO> list = super.findAllBySql(ExamScoreVO.class, sql);
        return  list;
    }
    @Override
    public List<ExamScorePercentVO> percentTown(String year){
        String sql = "SELECT S006.*,sysDis.districtName town from ( " +
                "SELECT S001.attachTo,ROUND(CAST(S001.number AS FLOAT)/S002.numbers, 3) score from( " +
                "SELECT sum(number) number,attachTo from ( " +
                "SELECT S0.districtName,count(districtName) number,S0.attachTo from ( " +
                "SELECT S1.*,pac.month from (  " +
                "SELECT pa.id,pa.status,sd.districtName,sd.attachTo,pa.activityId from PAR_ActivityObject pa,SYS_District sd where pa.organizationId = sd.districtId and sd.isDelete=0 and sd.districtLevel = 3  and pa.status = 2 " +
                ") S1,PAR_Activity pac WHERE pac.id = S1.activityId and pac.month BETWEEN '"+year+"-01-01 00:00:00' and '"+year+"-12-31 23:59:59' " +
                ") S0 GROUP BY districtName,attachTo " +
                ") S004 group by attachTo " +
                ") S001, " +
                "( " +
                "SELECT sum(numbers) numbers,attachTo from ( " +
                "SELECT S0.districtName,count(districtName) numbers,S0.attachTo from ( " +
                "SELECT S1.*,pac.month from ( " +
                "SELECT pa.id,pa.status,sd.districtName,sd.attachTo,pa.activityId from PAR_ActivityObject pa,SYS_District sd where pa.organizationId = sd.districtId and sd.isDelete=0 and sd.districtLevel = 3   " +
                ") S1,PAR_Activity pac WHERE pac.id = S1.activityId and pac.month BETWEEN '"+year+"-01-01 00:00:00' and '"+year+"-12-31 23:59:59' " +
                ") S0 GROUP BY districtName,attachTo " +
                ") S005 group by attachTo " +
                ") S002 where S001.attachTo = S002.attachTo " +
                ") S006,SYS_District sysDis WHERE S006.attachTo = sysDis.districtId " +
                "ORDER BY score desc " +
                " " +
                " " ;
        List<ExamScorePercentVO> list = super.findAllBySql(ExamScorePercentVO.class, sql);
        return  list;
    }
    @Override
    public List<ExamScorePercentVO> percentCun(String year,String townName){
        String sql = "SELECT * from ( " +
                "SELECT S003.districtName cun,S003.score,sysDis.districtName town from ( " +
                "SELECT " +
                " S001.districtName, " +
                " ROUND( CAST( S001.number AS FLOAT ) / S002.numbers, 3 ) score, " +
                " S001.attachTo  " +
                "FROM " +
                " ( " +
                "SELECT " +
                " S0.districtName, " +
                " count( districtName ) number, " +
                " S0.attachTo  " +
                "FROM " +
                " ( " +
                "SELECT " +
                " S1.*, " +
                " pac.MONTH  " +
                "FROM " +
                " ( " +
                "SELECT " +
                " pa.id, " +
                " pa.STATUS, " +
                " sd.districtName, " +
                " sd.attachTo, " +
                " pa.activityId  " +
                "FROM " +
                " PAR_ActivityObject pa, " +
                " SYS_District sd  " +
                "WHERE " +
                " pa.organizationId = sd.districtId  " +
                " AND sd.isDelete = 0  " +
                " AND sd.districtLevel = 3  " +
                " AND pa.STATUS = 2  " +
                " ) S1, " +
                " PAR_Activity pac  " +
                "WHERE " +
                " pac.id = S1.activityId  " +
                " AND pac.MONTH BETWEEN '"+year+"-01-01 00:00:00'  " +
                " AND '"+year+"-12-31 23:59:59'  " +
                " ) S0  " +
                "GROUP BY " +
                " districtName, " +
                " attachTo  " +
                " ) S001, " +
                " ( " +
                "SELECT " +
                " S0.districtName, " +
                " count( districtName ) numbers, " +
                " S0.attachTo  " +
                "FROM " +
                " ( " +
                "SELECT " +
                " S1.*, " +
                " pac.MONTH  " +
                "FROM " +
                " ( " +
                "SELECT " +
                " pa.id, " +
                " pa.STATUS, " +
                " sd.districtName, " +
                " sd.attachTo, " +
                " pa.activityId  " +
                "FROM " +
                " PAR_ActivityObject pa, " +
                " SYS_District sd  " +
                "WHERE " +
                " pa.organizationId = sd.districtId  " +
                " AND sd.isDelete = 0  " +
                " AND sd.districtLevel = 3  " +
                " ) S1, " +
                " PAR_Activity pac  " +
                "WHERE " +
                " pac.id = S1.activityId  " +
                " AND pac.MONTH BETWEEN '"+year+"-01-01 00:00:00'  " +
                " AND '"+year+"-12-31 23:59:59'  " +
                " ) S0  " +
                "GROUP BY " +
                " districtName, " +
                " attachTo  " +
                " ) S002  " +
                "WHERE " +
                " S001.districtName = S002.districtName) S003,SYS_District sysDis where sysDis.districtId = S003.attachTo " +
                " ) S005 where S005.town = '"+townName+"' order by score desc" ;
        List<ExamScorePercentVO> list = super.findAllBySql(ExamScorePercentVO.class, sql);
        return  list;
    }

}
