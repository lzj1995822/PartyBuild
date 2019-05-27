package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.AcceptInformation;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 接收公告 repository
 * @author cqh
 */
@Repository
public interface AcceptInformationRepository extends BaseRepository<AcceptInformation> {

    /**
     * 根据informationId删除所有
     * @param informationId
     * @return
     */
    Integer deleteAllByInformationId(String informationId);
}