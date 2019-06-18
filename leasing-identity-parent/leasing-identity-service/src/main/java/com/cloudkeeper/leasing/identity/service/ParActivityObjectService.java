package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParActivityObject;
import com.cloudkeeper.leasing.base.service.BaseService;

import java.util.List;

/**
 * 任务对象 service
 * @author lxw
 */
public interface ParActivityObjectService extends BaseService<ParActivityObject> {

    void deleteAllByActivityId(String activityId);

    //根据父组织查对应活动
    List<String> findActivityIdsByDistrictCode(String districtCode);

    //根据村组织查对应活动
    List<String> findActivityIdsByOrganizationId(String organizationId);

    ParActivityObject findByOrganizationIdAndActivityId(String organizationId, String activityId);
}
