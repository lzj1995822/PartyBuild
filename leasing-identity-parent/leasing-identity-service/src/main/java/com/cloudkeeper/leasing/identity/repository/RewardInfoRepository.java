package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.RewardInfo;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 报酬情况 repository
 * @author asher
 */
@Repository
public interface RewardInfoRepository extends BaseRepository<RewardInfo> {

    void deleteAllByCadresId(String cadresId);

    List<RewardInfo> findAllByCadresId(String cadresId);
}