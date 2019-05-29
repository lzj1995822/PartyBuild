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

}
