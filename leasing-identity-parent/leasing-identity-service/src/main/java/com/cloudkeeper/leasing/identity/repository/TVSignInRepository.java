package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.TVSignIn;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 远教视频签到记录 repository
 * @author cqh
 */
@Repository
public interface TVSignInRepository extends BaseRepository<TVSignIn> {

}