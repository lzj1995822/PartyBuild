package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.ExaScore;
import com.cloudkeeper.leasing.identity.repository.ExaScoreRepository;
import com.cloudkeeper.leasing.identity.service.ExaScoreService;
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

}
