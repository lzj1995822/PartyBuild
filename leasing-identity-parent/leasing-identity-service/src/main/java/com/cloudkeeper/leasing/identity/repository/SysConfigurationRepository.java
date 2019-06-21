package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.SysConfiguration;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 系统属性配置 repository
 * @author cqh
 */
@Repository
public interface SysConfigurationRepository extends BaseRepository<SysConfiguration> {

}