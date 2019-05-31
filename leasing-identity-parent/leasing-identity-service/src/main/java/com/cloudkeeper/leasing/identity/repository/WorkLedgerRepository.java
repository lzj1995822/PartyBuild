package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.WorkLedger;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 工作台账 repository
 * @author cqh
 */
@Repository
public interface WorkLedgerRepository extends BaseRepository<WorkLedger> {

}