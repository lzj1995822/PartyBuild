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
import java.util.stream.Collectors;

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
        String querySql = " sd.districtId like '01%' ";
        if ("01".equals(search)) {
            // 句容市委进入
            if ("Office".equals(districtType)) {
                // 机关
                querySql = " sd.districtId >= '0118' ";
            } else if ("Party".equals(districtType)) {
                // 农村
                querySql = " LEFT(sd.districtId, 4) BETWEEN '0101' and '0117' ";
            }
        } else if ("0118".equals(search)) {
            querySql = " sd.districtId >= '0118' ";
        } else {
            querySql = " sd.districtId like '" + search + "%' ";
        }
        String sql = "Select temp2.*, sd.districtName from (\n" +
                "SELECT  LEFT(temp.districtId, 4) as districtId, sum(temp.score) as total, CONVERT(DECIMAL(18,2),AVG(cast(temp.score as FLOAT))) as score, CONVERT(DECIMAL(18,2),sum(temp.finish)/cast(sum(temp.total) as FLOAT)) as finishRatio FROM (\n" +
                "SELECT sd.districtId, sum(ISNULL(es.score, 0)) as score, COUNT(CASE WHEN pao.status = 2 THEN 1 ELSE NULL END) as finish, COUNT(1) as total FROM PAR_ActivityObject pao LEFT JOIN PAR_Activity pa on pao.activityId = pa.id \n" +
                "left JOIN SYS_District sd on pao.organizationId = sd.districtId\n" +
                "LEFT JOIN EXA_Score es on pao.activityId = es.activityId and es.organizationId = sd.id\n" +
                "WHERE LEFT(pa.[month], 4) = '" + year + "' and sd.districtId is not null and " + querySql +
                "and sd.isDelete = 0 GROUP BY sd.districtId ) temp GROUP BY LEFT(temp.districtId, 4) \n" +
                ") temp2 LEFT JOIN SYS_District sd on sd.districtId = temp2.districtId ORDER BY sd.districtId asc";
        List<ExamScoreAllVO> parent = super.findAllBySql(ExamScoreAllVO.class, sql);

        String csql = "SELECT sd.districtId, sd.districtName, CONVERT(DECIMAL(18,2),sum(ISNULL(es.score, 0))) as score,  CONVERT(DECIMAL(18,2),COUNT(CASE WHEN pao.status = 2 THEN 1 ELSE NULL END)/cast(count(1) as FLOAT)) as finishRatio FROM PAR_ActivityObject pao LEFT JOIN PAR_Activity pa on pao.activityId = pa.id \n" +
                "left JOIN SYS_District sd on pao.organizationId = sd.districtId\n" +
                "LEFT JOIN EXA_Score es on pao.activityId = es.activityId and es.organizationId = sd.id\n" +
                "WHERE LEFT(pa.[month], 4) = '" + year + "' and sd.districtId is not null  and " + querySql +
                "and sd.isDelete = 0 GROUP BY sd.districtId,sd.districtName ORDER BY sd.districtId asc\n";
        List<ExamScoreAllVO> children = super.findAllBySql(ExamScoreAllVO.class, csql);
        Map<String, ActivityExamVO> secondRes = new HashMap<>();
        for (ExamScoreAllVO item : parent) {
            ActivityExamVO activityExamVO = new ActivityExamVO();
            activityExamVO.setTown(item.getDistrictName());
            activityExamVO.setTownScore(item.getFinishRatio().doubleValue());
            activityExamVO.setTownExam(item.getScore().doubleValue());
            activityExamVO.setTownTotal(item.getTotal());
            activityExamVO.setChildren(new ArrayList<>());
            secondRes.put(item.getDistrictId(), activityExamVO);
        }
        children.forEach(item -> {
           ActivityExamVO activityExamVO = secondRes.get(item.getDistrictId().substring(0, 4));
           ActivityExamVO activityExam = new ActivityExamVO();
           activityExam.setTown(item.getDistrictName());
           activityExam.setTownScore(item.getFinishRatio().doubleValue());
           activityExam.setTownExam(item.getScore().doubleValue());
           activityExamVO.getChildren().add(activityExam);
        });
        return new ArrayList<>(secondRes.values());
    }

}
