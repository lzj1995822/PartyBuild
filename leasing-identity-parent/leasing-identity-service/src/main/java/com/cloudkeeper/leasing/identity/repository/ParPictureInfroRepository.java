package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.ParPictureInfro;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 电视截图 repository
 * @author lxw
 */
@Repository
public interface ParPictureInfroRepository extends BaseRepository<ParPictureInfro> {

    void deleteAllByStudyContentAndOrganizationId(String activityId,String organizationId);

    ParPictureInfro findByRedisUuid(String redisUuid);
}
