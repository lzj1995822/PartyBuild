package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.FamilyInfo;
import com.cloudkeeper.leasing.identity.domain.FamilyWorkInfo;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 专职村书记家庭工作情况 repository
 * @author yujian
 */
@Repository
public interface FamilyWorkInfoRepository extends BaseRepository<FamilyWorkInfo> {
    void deleteAllByCadresId(String cadresId);

    List<FamilyWorkInfo> findAllByCadresId(String cadresId);
}