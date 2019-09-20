package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ParActivityPerform;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.identity.dto.paractivityperform.ParActivityPerformDTO;
import com.cloudkeeper.leasing.identity.dto.paractivityperform.ParActivityPerformSearchable;
import com.cloudkeeper.leasing.identity.vo.ParActivityPerformVO;
import com.cloudkeeper.leasing.identity.vo.PassPercentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    Optional<ParActivityPerform> findByActivityIDAndOrganizationId(String activityId, String organizationId);

    @Query(value = "SELECT COUNT(1) from PAR_ActivityPerform as par LEFT JOIN SYS_District as d on par.organizationId = d.id LEFT JOIN PAR_Activity pa on par.ActivityID = pa.id where d.districtId LIKE :districtId% AND YEAR(pa.[month]) = :year", nativeQuery = true)
    Integer countAll(@Param("districtId") String districtId, @Param("year") String year);

    Integer countAllByStatus(String status);

}
