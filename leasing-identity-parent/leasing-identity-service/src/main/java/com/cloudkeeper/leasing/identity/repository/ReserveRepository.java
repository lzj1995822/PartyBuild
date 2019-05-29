package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.Reserve;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 后备人才 repository
 * @author asher
 */
@Repository
public interface ReserveRepository extends BaseRepository<Reserve> {

}