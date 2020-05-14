package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.DetectionIndex;
import com.cloudkeeper.leasing.base.service.BaseService;

import javax.annotation.Nonnull;

/**
 * 监测指标 service
 * @author asher
 */
public interface DetectionIndexService extends BaseService<DetectionIndex> {

    DetectionIndex findByDistrictIdAndTaskId(@Nonnull String districtId, @Nonnull String taskId);

}