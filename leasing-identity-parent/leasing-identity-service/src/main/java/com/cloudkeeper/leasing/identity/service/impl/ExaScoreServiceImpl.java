package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ExaScore;
import com.cloudkeeper.leasing.identity.repository.ExaScoreRepository;
import com.cloudkeeper.leasing.identity.service.ExaScoreService;
import com.cloudkeeper.leasing.identity.vo.ActivityExamVO;
import com.cloudkeeper.leasing.identity.vo.ExamScoreAllVO;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<ExamScoreVO> scoreCun(Pageable pageable, String sort, String year, String districtType) {
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
                " SELECT " +
                " sd.districtName, sd.attachTo, sd.id, districtId, es.activityId, es.score, es.createTime " +
                " FROM SYS_District sd " +
                " LEFT JOIN EXA_Score es ON es.organizationId = sd.id " +
                "WHERE " +
                " sd.isDelete = 0  " +
                " AND es.createTime BETWEEN '"+year+"-01-01 00:00:00' and '"+year+"-12-31 23:59:59' "+
                " AND sd.districtType = '" + districtType +
                "' AND sd.districtLevel = 3  " +
                " OR sd.districtLevel = 4 " +
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

    @Override
    public List<ActivityExamVO> examScoreAll(Pageable pageable, String year,String search,String districtType){
        String sql = "SELECT " +
                " TOP "+pageable.getPageSize()+" *  " +
                "FROM " +
                " ( " +
                "SELECT " +
                " S0012.*, " +
                " row_number ( ) OVER ( ORDER BY townExam DESC,townName DESC,exam DESC ) AS row_number, " +
                " COUNT( * ) OVER ( ) AS total " +
                "  " +
                "FROM " +
                " ( " +
                "SELECT " +
                "S0010.*, " +
                "S0011.townScore  " +
                "FROM " +
                "( " +
                "SELECT " +
                "*  " +
                "FROM " +
                "( " +
                "SELECT " +
                "S007.*, " +
                "S005.score  " +
                "FROM " +
                "( " +
                "SELECT " +
                "S3.exam, " +
                "S3.cun, " +
                "S3.districtName town  " +
                "FROM " +
                "( " +
                "SELECT " +
                "a.exam, " +
                "a.cun, " +
                "a.attachTo, " +
                "a.districtId, " +
                "a.exscore, " +
                "district.districtName  " +
                "FROM " +
                "( " +
                "SELECT " +
                "S0.cun, " +
                "S0.attachTo, " +
                "S0.districtId, " +
                "examine.score exscore, " +
                "S0.exam + isnull( examine.score, 0 ) exam  " +
                "FROM " +
                " ( " +
                " SELECT " +
                "  SUM( score ) exam, " +
                "  districtName cun, " +
                "  attachTo, " +
                "  districtId  " +
                " FROM " +
                "  ( " +
                "  SELECT " +
                "   es.activityId, " +
                "   es.score, " +
                "   es.createTime, " +
                "   sd.districtName, " +
                "   sd.attachTo, " +
                "   sd.id districtId  " +
                "  FROM " +
                "   EXA_Score es " +
                "   LEFT JOIN SYS_District sd ON es.organizationId = sd.id  " +
                "  WHERE " +
                "   sd.districtLevel = 3  " +
                "   AND sd.isDelete = 0  " +
                "   AND es.createTime BETWEEN '"+year+"-01-01 00:00:00'  " +
                "   AND '"+year+"-12-31 23:59:59'  " +
                "  ) S1  " +
                " GROUP BY " +
                "  districtName, " +
                "  districtId, " +
                "  attachTo  " +
                " ) S0 " +
                " LEFT JOIN ( SELECT sum( score ) score, organizationId FROM EXA_Examine WHERE createTime BETWEEN '"+year+"-01-01 00:00:00' AND '"+year+"-12-31 23:59:59' GROUP BY organizationId ) examine ON S0.districtId = examine.organizationId  " +
                " ) a, " +
                " SYS_District district  " +
                "WHERE " +
                " a.attachTo = district.districtId  " +
                " ) S3  " +
                " ) S007, " +
                " ( " +
                " SELECT " +
                "  S003.districtName cun, " +
                "  S003.score, " +
                "  sysDis.districtName town  " +
                " FROM " +
                "  ( " +
                "  SELECT " +
                "   S001.districtName, " +
                "   ROUND( CAST( S001.number AS FLOAT ) / S002.numbers, 3 ) score, " +
                "   S001.attachTo  " +
                "  FROM " +
                "   ( " +
                "   SELECT " +
                "    S0.districtName, " +
                "    count( districtName ) number, " +
                "    S0.attachTo  " +
                "   FROM " +
                "    ( " +
                "    SELECT " +
                "     S1.*, " +
                "     pac.MONTH  " +
                "    FROM " +
                "     ( " +
                "     SELECT " +
                "      pa.id, " +
                "      pa.STATUS, " +
                "      sd.districtName, " +
                "      sd.attachTo, " +
                "      pa.activityId  " +
                "     FROM " +
                "      PAR_ActivityObject pa, " +
                "      SYS_District sd  " +
                "     WHERE " +
                "      pa.organizationId = sd.districtId  " +
                "      AND sd.isDelete = 0  " +
                "      AND sd.districtLevel = 3  " +
                "      AND pa.STATUS = 2  " +
                "     ) S1, " +
                "     PAR_Activity pac  " +
                "    WHERE " +
                "     pac.id = S1.activityId  " +
                "     AND pac.MONTH BETWEEN '"+year+"-01-01 00:00:00'  " +
                "     AND '"+year+"-12-31 23:59:59'  " +
                "    ) S0  " +
                "   GROUP BY " +
                "    districtName, " +
                "    attachTo  " +
                "   ) S001, " +
                "   ( " +
                "   SELECT " +
                "    S0.districtName, " +
                "    count( districtName ) numbers, " +
                "    S0.attachTo  " +
                "   FROM " +
                "    ( " +
                "    SELECT " +
                "     S1.*, " +
                "     pac.MONTH  " +
                "    FROM " +
                "     ( " +
                "     SELECT " +
                "      pa.id, " +
                "      pa.STATUS, " +
                "      sd.districtName, " +
                "      sd.attachTo, " +
                "      pa.activityId  " +
                "     FROM " +
                "      PAR_ActivityObject pa, " +
                "      SYS_District sd  " +
                "     WHERE " +
                "      pa.organizationId = sd.districtId  " +
                "      AND sd.isDelete = 0  " +
                "      AND sd.districtLevel = 3  " +
                "     ) S1, " +
                "     PAR_Activity pac  " +
                "    WHERE " +
                "     pac.id = S1.activityId  " +
                "     AND pac.MONTH BETWEEN '"+year+"-01-01 00:00:00'  " +
                "     AND '"+year+"-12-31 23:59:59'  " +
                "    ) S0  " +
                "   GROUP BY " +
                "    districtName, " +
                "    attachTo  " +
                "   ) S002  " +
                "  WHERE " +
                "   S001.districtName = S002.districtName  " +
                "  ) S003, " +
                "  SYS_District sysDis  " +
                " WHERE " +
                "  sysDis.districtId = S003.attachTo  " +
                " ) S005  " +
                "WHERE " +
                " S007.cun = S005.cun  " +
                " ) S008, " +
                " ( " +
                " SELECT " +
                "  SUM( exam ) / count( districtName ) townExam, " +
                "  districtName townName  " +
                " FROM " +
                "  ( " +
                "  SELECT " +
                "   S000.cun, " +
                "   S000.exam, " +
                "   sysd.districtName  " +
                "  FROM " +
                "   ( " +
                "   SELECT " +
                "    exam, " +
                "    cun, " +
                "    attachTo, " +
                "    districtId, " +
                "    exscore  " +
                "   FROM " +
                "    ( " +
                "    SELECT " +
                "     S0.cun, " +
                "     S0.attachTo, " +
                "     S0.districtId, " +
                "     examine.score exscore, " +
                "     S0.exam + isnull( examine.score, 0 ) exam  " +
                "    FROM " +
                "     ( " +
                "     SELECT " +
                "      SUM( score ) exam, " +
                "      districtName cun, " +
                "      attachTo, " +
                "      districtId  " +
                "     FROM " +
                "      ( " +
                "      SELECT " +
                "       es.activityId, " +
                "       es.score, " +
                "       es.createTime, " +
                "       sd.districtName, " +
                "       sd.attachTo, " +
                "       sd.id districtId  " +
                "      FROM " +
                "       EXA_Score es " +
                "       LEFT JOIN SYS_District sd ON es.organizationId = sd.id  " +
                "      WHERE " +
                "       sd.isDelete = 0  " +
                "       AND es.createTime BETWEEN '"+year+"-01-01 00:00:00'  " +
                "       AND '"+year+"-12-31 23:59:59'  " +
                "       AND sd.districtType = '" + districtType +
                "'       AND sd.districtLevel = 3  or sd.districtLevel = 4" +
                "      ) S1  " +
                "     GROUP BY " +
                "      districtName, " +
                "      districtId, " +
                "      attachTo  " +
                "     ) S0 " +
                "     LEFT JOIN ( SELECT sum( score ) score, organizationId FROM EXA_Examine WHERE createTime BETWEEN '"+year+"-01-01 00:00:00' AND '"+year+"-12-31 23:59:59' GROUP BY organizationId ) examine ON S0.districtId = examine.organizationId  " +
                "    ) a  " +
                "   ) S000, " +
                "   SYS_District sysd  " +
                "  WHERE " +
                "   S000.attachTo = sysd.districtId  " +
                "  ) S001  " +
                " GROUP BY " +
                "  districtName  " +
                " ) S009  " +
                "WHERE " +
                " S008.town = S009.townName  " +
                " ) S0010, " +
                " ( " +
                " SELECT " +
                "  S006.score townScore, " +
                "  sysDis.districtName town  " +
                " FROM " +
                "  ( " +
                "  SELECT " +
                "   S001.attachTo, " +
                "   ROUND( CAST ( S001.number AS FLOAT ) / S002.numbers, 3 ) score  " +
                "  FROM " +
                "   ( " +
                "   SELECT " +
                "    SUM( number ) number, " +
                "    attachTo  " +
                "   FROM " +
                "    ( " +
                "    SELECT " +
                "     S0.districtName, " +
                "     COUNT( districtName ) number, " +
                "     S0.attachTo  " +
                "    FROM " +
                "     ( " +
                "     SELECT " +
                "      S1.*, " +
                "      pac.MONTH  " +
                "     FROM " +
                "      ( " +
                "      SELECT " +
                "       pa.id, " +
                "       pa.STATUS, " +
                "       sd.districtName, " +
                "       sd.attachTo, " +
                "       pa.activityId  " +
                "      FROM " +
                "       PAR_ActivityObject pa, " +
                "       SYS_District sd  " +
                "      WHERE " +
                "       pa.organizationId = sd.districtId  " +
                "       AND sd.isDelete = 0  " +
                "       AND sd.districtLevel = 3  " +
                "       AND pa.STATUS = 2  " +
                "      ) S1, " +
                "      PAR_Activity pac  " +
                "     WHERE " +
                "      pac.id = S1.activityId  " +
                "      AND pac.MONTH BETWEEN '"+year+"-01-01 00:00:00'  " +
                "      AND '"+year+"-12-31 23:59:59'  " +
                "     ) S0  " +
                "    GROUP BY " +
                "     districtName, " +
                "     attachTo  " +
                "    ) S004  " +
                "   GROUP BY " +
                "    attachTo  " +
                "   ) S001, " +
                "   ( " +
                "   SELECT " +
                "    SUM( numbers ) numbers, " +
                "    attachTo  " +
                "   FROM " +
                "    ( " +
                "    SELECT " +
                "     S0.districtName, " +
                "     COUNT( districtName ) numbers, " +
                "     S0.attachTo  " +
                "    FROM " +
                "     ( " +
                "     SELECT " +
                "      S1.*, " +
                "      pac.MONTH  " +
                "     FROM " +
                "      ( " +
                "      SELECT " +
                "       pa.id, " +
                "       pa.STATUS, " +
                "       sd.districtName, " +
                "       sd.attachTo, " +
                "       pa.activityId  " +
                "      FROM " +
                "       PAR_ActivityObject pa, " +
                "       SYS_District sd  " +
                "      WHERE " +
                "       pa.organizationId = sd.districtId  " +
                "       AND sd.isDelete = 0  " +
                "       AND sd.districtLevel = 3  " +
                "      ) S1, " +
                "      PAR_Activity pac  " +
                "     WHERE " +
                "      pac.id = S1.activityId  " +
                "      AND pac.MONTH BETWEEN '"+year+"-01-01 00:00:00'  " +
                "      AND '"+year+"-12-31 23:59:59'  " +
                "     ) S0  " +
                "    GROUP BY " +
                "     districtName, " +
                "     attachTo  " +
                "    ) S005  " +
                "   GROUP BY " +
                "    attachTo  " +
                "   ) S002  " +
                "  WHERE " +
                "   S001.attachTo = S002.attachTo  " +
                "  ) S006, " +
                "  SYS_District sysDis  " +
                " WHERE " +
                "  S006.attachTo = sysDis.districtId  " +
                " ) S0011  " +
                "WHERE " +
                " S0010.town = S0011.town  " +
                " ) S0012  " +
                "WHERE " +
                " S0012.town LIKE '%"+search+"%'  " +
                " OR S0012.cun LIKE '%"+search+"%'  " +
                " ) S0013 " +
                "WHERE " +
                " row_number > ( "+pageable.getPageNumber()+" ) * "+pageable.getPageSize()+"";
        List<ExamScoreAllVO> list = super.findAllBySql(ExamScoreAllVO.class, sql);
        List<ActivityExamVO> examList = new ArrayList<>();
        list.forEach(item->{
            boolean isExist = false;
            for (ActivityExamVO activityExamVO: examList) {
                if (activityExamVO.getTown().equals(item.getTown())) {
                    isExist = true;
                    ActivityExamVO cunVO = new ActivityExamVO();
                    cunVO.setTown(item.getCun());
                    cunVO.setTownExam(item.getExam());
                    cunVO.setTownScore(item.getScore());
                    activityExamVO.getChildren().add(cunVO);
                }
            }
            if (!isExist) {
                ActivityExamVO townVO = new ActivityExamVO();
                townVO.setTown(item.getTown());
                townVO.setTownExam(item.getTownExam());
                townVO.setTownScore(item.getTownScore());
                ActivityExamVO cunVO = new ActivityExamVO();
                cunVO.setTown(item.getCun());
                cunVO.setTownExam(item.getExam());
                cunVO.setTownScore(item.getScore());
                List<ActivityExamVO> temp = new ArrayList<>();
                temp.add(cunVO);
                townVO.setChildren(temp);
                examList.add(townVO);
            }
        });
        return  examList;
    }

}
