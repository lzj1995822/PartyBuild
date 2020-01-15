package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.AuthorizationToken;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 访问权限 repository
 * @author cqh
 */
@Repository
public interface AuthorizationTokenRepository extends BaseRepository<AuthorizationToken> {

}