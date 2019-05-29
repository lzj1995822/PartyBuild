package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ParActivity;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.identity.vo.ParActivityVO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 活动 repository
 * @author lxw
 */
@Repository
public interface ParActivityRepository extends BaseRepository<ParActivity> {

    @Transactional
    @Modifying
    @Query(value = "delete from PAR_Activity where id =?",nativeQuery = true)
    void deletePar(String id);

    @Transactional
    @Modifying
    @Query(value = "delete from PAR_ActivityReleaseFile where activityID =?",nativeQuery = true)
    void deleteParFile(String id);



}
