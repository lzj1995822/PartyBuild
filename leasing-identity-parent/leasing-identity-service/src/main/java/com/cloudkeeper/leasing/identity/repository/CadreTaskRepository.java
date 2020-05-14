package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.CadreTask;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * 村书记模块任务 repository
 * @author asher
 */
@Repository
public interface CadreTaskRepository extends BaseRepository<CadreTask> {

    CadreTask findByTypeAndEndTimeGreaterThanEqualOrderByEndTimeDesc(String type, LocalDate endTime);

    CadreTask findByTypeAndTaskYear(String type, String taskYear);

    CadreTask findByTypeAndTaskYearAndTaskQuarter(String type, String taskYear, String taskQuarter);
}