package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.CadrePosition;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 岗位管理 repository
 * @author cqh
 */
@Repository
public interface CadrePositionRepository extends BaseRepository<CadrePosition> {

    Integer countAllByDistrictIdStartingWithAndPostAndCadreIdNotNull(String districtId,String post);

}