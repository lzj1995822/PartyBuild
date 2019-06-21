package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.MessageCenter;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 消息中心 repository
 * @author cqh
 */
@Repository
public interface MessageCenterRepository extends BaseRepository<MessageCenter> {

}