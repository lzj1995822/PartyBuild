package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.identity.domain.InformationAudit;
import com.cloudkeeper.leasing.identity.repository.InformationRepository;
import com.cloudkeeper.leasing.identity.service.InformationAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InformationAuditServiceImpl  extends BaseServiceImpl<InformationAudit> implements InformationAuditService {

    @Autowired
    InformationRepository informationRepository;

    @Override
    protected BaseRepository<InformationAudit> getBaseRepository() {

        return (BaseRepository)informationRepository;
    }

    @Override
    public Integer isPass(String informationAuditId) {
        return null;
    }

    @Override
    public String passAdvice(String passAdvice) {
        return null;
    }

    @Override
    public Integer villageId(Integer villageId) {
        return null;
    }
}