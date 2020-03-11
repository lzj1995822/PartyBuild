package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.FamilyInfo;
import com.cloudkeeper.leasing.identity.domain.FamilyWorkInfo;
import com.cloudkeeper.leasing.base.service.BaseService;

import java.util.List;

/**
 * 专职村书记家庭工作情况 service
 * @author yujian
 */
public interface FamilyWorkInfoService extends BaseService<FamilyWorkInfo> {
    void deleteAllByCadresId(String cadresId);

    List<FamilyWorkInfo> findAllByCadresId(String cadresId);
}