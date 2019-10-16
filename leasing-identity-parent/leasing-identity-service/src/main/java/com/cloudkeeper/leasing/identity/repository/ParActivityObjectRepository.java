package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ParActivityObject;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import java.util.Optional;

/**
 * 任务对象 repository
 * @author lxw
 */
@Repository
public interface ParActivityObjectRepository extends BaseRepository<ParActivityObject> {

    /**
     * 根据活动id删除所有需要执行的对象
     * @param activityId
     */
    void deleteAllByActivityId(String activityId);

    @Query(value = "SELECT DISTINCT activityId FROM PAR_ActivityObject WHERE attachTo = ? ",nativeQuery=true)
    List<String> findActivityIdsByDistrictCode(String districtCode);

    @Query(value = "SELECT DISTINCT activityId FROM PAR_ActivityObject WHERE organizationId = ? ",nativeQuery=true)
    List<String> findActivityIdsByOrganizationId(String organizationId);

    Integer countAllByOrganizationIdStartingWithAndStatusAndCreatedAtBetween(String organizationId, String status, LocalDateTime start, LocalDateTime end);
    Integer countAllByOrganizationIdStartingWithAndStatus(String organizationId, String status);

    Integer countAllByOrganizationIdStartingWithAndCreatedAtBetween(String organizationId, LocalDateTime start, LocalDateTime end);

    Optional<ParActivityObject> findByActivityIdAndOrganizationId(String activityId,String organizationId);

    ParActivityObject findByOrganizationIdAndActivityId(String organizationId, String activityId);

    Integer countAllByIsWorking(String isWorking);
}
