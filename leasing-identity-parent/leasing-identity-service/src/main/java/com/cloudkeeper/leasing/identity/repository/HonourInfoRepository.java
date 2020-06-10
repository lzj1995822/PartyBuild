package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.HonourInfo;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 表彰情况 repository
 * @author asher
 */
@Repository
public interface HonourInfoRepository extends BaseRepository<HonourInfo> {

    void deleteAllByCadresId(String cadresId);

    List<HonourInfo> findAllByCadresId(String cadresId);

    List<HonourInfo> findAllByCadresIdAndRewardsType(String cadresId, String rewardsType);
}