package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.identity.domain.Information;
import org.springframework.stereotype.Repository;

/**
 * 消息通知 repository
 * @author cqh
 */
@Repository
public interface InformationRepository extends BaseRepository<Information> {
}
