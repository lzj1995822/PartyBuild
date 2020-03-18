package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.VillageCadresTerm;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 村主任任期信息 repository
 * @author yujian
 */
@Repository
public interface VillageCadresTermRepository extends BaseRepository<VillageCadresTerm> {
    void deleteAllByCadresId(String cadresId);

    VillageCadresTerm findByCadresId(String cadresId);
}
