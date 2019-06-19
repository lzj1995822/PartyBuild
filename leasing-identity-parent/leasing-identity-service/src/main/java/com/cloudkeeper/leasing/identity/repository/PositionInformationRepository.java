package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.PositionInformation;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 阵地信息 repository
 * @author cqh
 */
@Repository
public interface PositionInformationRepository extends BaseRepository<PositionInformation> {

    Integer countAllByDistrictIdStartingWith(String districtId);

}