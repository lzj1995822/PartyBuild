package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.SysLog;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 系统日志 repository
 * @author asher
 */
@Repository
public interface SysLogRepository extends BaseRepository<SysLog> {

}