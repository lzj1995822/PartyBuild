package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.CadreTask;
import com.cloudkeeper.leasing.identity.domain.CadreTaskObject;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.domain.TaskObject;

import java.util.List;

/**
 * 村书记模块发布任务对象记录 service
 * @author asher
 */
public interface CadreTaskObjectService extends BaseService<CadreTaskObject> {

    CadreTaskObject updateStatusByTaskIdAndObjectId(String taskId, String objectId);

    void deleteByTaskId(String taskId);

    List<CadreTaskObject> findAllByTaskId(String taskId);

    CadreTaskObject submit(String taskObjectId, String isSuccess, String auditor, String auditorAdvice);
}