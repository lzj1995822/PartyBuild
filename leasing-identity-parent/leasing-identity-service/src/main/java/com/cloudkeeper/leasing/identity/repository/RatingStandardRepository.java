package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.RatingStandard;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 村书记评级标准 repository
 * @author asher
 */
@Repository
public interface RatingStandardRepository extends BaseRepository<RatingStandard> {

    //  查询所有标准
    List<RatingStandard> findAllByIsStandard(String isStandard);

    void deleteAllByCadresId(String cadresId);
}