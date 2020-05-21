package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.KPIVillageStatistics;
import com.cloudkeeper.leasing.base.service.BaseService;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 村一级指标统计 service
 * @author yujian
 */
public interface KPIVillageStatisticsService extends BaseService<KPIVillageStatistics> {

    void addStatistics(List<KPIVillageStatistics> kpiVillageStatistics);

    Boolean generateVillageStatistic(@Nonnull String taskId);

}
