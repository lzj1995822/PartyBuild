package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParActivityObject;
import com.cloudkeeper.leasing.base.service.BaseService;

import java.math.BigDecimal;
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

    //统计数量,计算完成率
    BigDecimal countAllByOrganizationIdStartingWithAndStatus(String organizationId, String status);

    // 初始化之前的活动对应活动对象
    void initPerActivity();
}
