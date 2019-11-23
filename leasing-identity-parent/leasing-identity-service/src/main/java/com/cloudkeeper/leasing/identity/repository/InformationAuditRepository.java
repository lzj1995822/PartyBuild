package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.identity.domain.InformationAudit;
import com.cloudkeeper.leasing.identity.domain.ParMember;
import org.springframework.stereotype.Repository;


/**
 * 村书记审核状态 service
 * @author zdw
 */



@Repository
public interface InformationAuditRepository extends BaseRepository<InformationAudit> {

}
