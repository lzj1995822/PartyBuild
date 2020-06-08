package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.KPIStatistics;
import com.cloudkeeper.leasing.base.service.BaseService;

import javax.annotation.Nonnull;

/**
 * 双向印证 service
 * @author yujian
 */
public interface KPIStatisticsService extends BaseService<KPIStatistics> {

    void deleteAllByTaskId(@Nonnull String taskId);

}