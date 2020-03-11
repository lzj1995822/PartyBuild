package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.FamilyInfo;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.identity.domain.HonourInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 专职村书记家庭情况 repository
 * @author yujian
 */
@Repository
public interface FamilyInfoRepository extends BaseRepository<FamilyInfo> {
    void deleteAllByCadresId(String cadresId);

    List<FamilyInfo> findAllByCadresId(String cadresId);
}