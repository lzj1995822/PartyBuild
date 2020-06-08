package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.KPIStatistics;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

/**
 * 双向印证 repository
 * @author yujian
 */
@Repository
public interface KPIStatisticsRepository extends BaseRepository<KPIStatistics> {

    void deleteAllByTaskId(@Nonnull String taskId);
}