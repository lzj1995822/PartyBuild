package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.SysUser;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 系统用户 repository
 * @author asher
 */
@Repository
public interface SysUserRepository extends BaseRepository<SysUser> {

}