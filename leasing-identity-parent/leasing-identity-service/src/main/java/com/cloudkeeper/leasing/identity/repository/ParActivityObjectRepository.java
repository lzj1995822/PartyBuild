package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ParActivityObject;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

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
}