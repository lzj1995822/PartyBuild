package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.TrainingInfo;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 专职村书记培训情况 repository
 * @author yujian
 */
@Repository
public interface TrainingInfoRepository extends BaseRepository<TrainingInfo> {

    void deleteAllByCadresId(String cadresId);
}