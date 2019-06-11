package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParActivityObject;
import com.cloudkeeper.leasing.base.service.BaseService;

/**
 * 任务对象 service
 * @author lxw
 */
public interface ParActivityObjectService extends BaseService<ParActivityObject> {

    void deleteAllByActivityId(String activityId);
}
