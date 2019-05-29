package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ParActivityPerform;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.identity.vo.ParActivityPerformVO;
import com.cloudkeeper.leasing.identity.vo.PassPercentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务执行记录 repository
 * @author lxw
 */
@Repository
public interface ParActivityPerformRepository extends BaseRepository<ParActivityPerform> {

//    @Transactional
//    @Modifying
//    @Query(value = "select * from PAR_ActivityPerform",nativeQuery = true)
//    Page<ParActivityPerform> listAll(String activityId, String orgId, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "SELECT s6.*, ROUND(cast(s6.passed as FLOAT)/(s6.waitCheck + s6.passed + s6.fail),3)  as finishRatio from (\n" +
            "\n" +
            "\n" +
            "SELECT\n" +
            "\tS5.tn,\n" +
            "\tCOUNT( CASE WHEN sa = 1 THEN 1 ELSE NULL END ) waitCheck,\n" +
            "\tCOUNT( CASE WHEN sa = 2 THEN 1 ELSE NULL END ) passed,\n" +
            "\tCOUNT( CASE WHEN sa = 3 OR sa IS NULL THEN 1 ELSE NULL END ) fail \n" +
            "FROM\n" +
            "\t(\n" +
            "SELECT\n" +
            "\tS3.town tn,\n" +
            "\tS3.cun cn,\n" +
            "\tS3.cunId cd,\n" +
            "\tS4.STATUS sa,\n" +
            "\tS4.ActivityID aid \n" +
            "FROM\n" +
            "\t(\n" +
            "SELECT\n" +
            "\tS1.districtName town,\n" +
            "\tS0.districtName cun,\n" +
            "\tS0.id cunId \n" +
            "FROM\n" +
            "\tSYS_District S0\n" +
            "\tLEFT JOIN ( SELECT * FROM SYS_District sd WHERE sd.districtLevel = 2 ) S1 ON S0.attachTo = S1.districtId \n" +
            "WHERE\n" +
            "\tS0.districtLevel = 3 \n" +
            "\t) S3\n" +
            "\tLEFT JOIN PAR_ActivityPerform S4 ON S3.cunId = S4.organizationId \n" +
            "\tAND S4.ActivityID = 'e7f8f422-d0e3-49cf-bc23-fb98610f18af' \n" +
            "\t) S5 \n" +
            "GROUP BY\n" +
            "\ttn\n" +
            "\t) s6",nativeQuery = true)
    List<PassPercentVO> perecent(String activityId);
}
