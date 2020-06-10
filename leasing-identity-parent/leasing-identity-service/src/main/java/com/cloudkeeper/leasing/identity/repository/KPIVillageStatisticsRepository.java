package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.KPIVillageStatistics;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 村一级指标统计 repository
 * @author yujian
 */
@Repository
public interface KPIVillageStatisticsRepository extends BaseRepository<KPIVillageStatistics> {

    void deleteAllByTaskId(@Nonnull String taskId);

    List<KPIVillageStatistics> findAllByCadresIdAndQuotaLevelOrderByCreatedAt(String cadresId, String quotaLevel);
}