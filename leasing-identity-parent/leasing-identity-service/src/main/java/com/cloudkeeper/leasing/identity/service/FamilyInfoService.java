package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.FamilyInfo;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.domain.HonourInfo;

import java.util.List;

/**
 * 专职村书记家庭情况 service
 * @author yujian
 */
public interface FamilyInfoService extends BaseService<FamilyInfo> {
    void deleteAllByCadresId(String cadresId);

    List<FamilyInfo> findAllByCadresId(String cadresId);
}