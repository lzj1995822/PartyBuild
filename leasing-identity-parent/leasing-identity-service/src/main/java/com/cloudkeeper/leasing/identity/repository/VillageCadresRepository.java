package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.VillageCadres;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 村干部管理 repository
 * @author cqh
 */
@Repository
public interface VillageCadresRepository extends BaseRepository<VillageCadres> {

    Long countAllByDistrictIdStartingWith(String districtId);

    Integer countAllByQuasiAssessmentRank(String QuasiAssessmentRank);

    List<VillageCadres> findAllByParentDistrictId(String objectId);
}