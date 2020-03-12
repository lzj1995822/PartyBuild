package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.CadreTask;
import com.cloudkeeper.leasing.identity.domain.CadreTaskObject;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 村书记模块发布任务对象记录 repository
 * @author asher
 */
@Repository
public interface CadreTaskObjectRepository extends BaseRepository<CadreTaskObject> {

    CadreTaskObject findByTaskIdAndAndObjectId(String taskId, String objectId);

    void deleteAllByTaskId(String taskId);
}