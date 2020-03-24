package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.DetectionIndex;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 监测指标 repository
 * @author asher
 */
@Repository
public interface DetectionIndexRepository extends BaseRepository<DetectionIndex> {

}