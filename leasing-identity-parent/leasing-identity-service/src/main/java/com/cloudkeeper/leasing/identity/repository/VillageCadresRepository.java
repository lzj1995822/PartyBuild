package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.identity.domain.VillageCadres;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    List<VillageCadres> findAllByParentDistrictIdAndHasRetire(String objectId, String hasRetire);

    List<VillageCadres> findAllByQuasiAssessmentRankAndParentDistrictIdAndHasRetire(String rank, String parentDistrictId, String hasRetire);

    List<VillageCadres> findAllByQuasiAssessmentRankAndHasRetire(String rank, String hasRetire);


    @Modifying
    @Transactional
    @Query(value = "update village_cadres set state = '0'",nativeQuery = true)
    int updateState();
}
