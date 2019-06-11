package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.SysUser;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

/**
 * 系统用户 repository
 * @author asher
 */
@Repository
public interface SysUserRepository extends BaseRepository<SysUser> {

    /**
     * 通过登录名查用户
     * @param userName
     * @return
     */
    Optional<SysUser> findByUserName(String userName);

    /**
     * 查询列表 根据组织id
     * @param organizationId 组织id
     * @return 组织用户关系列表
     */
    @Nonnull
    List<SysUser> findAllByOrganizationId(@Nonnull String organizationId);
}
