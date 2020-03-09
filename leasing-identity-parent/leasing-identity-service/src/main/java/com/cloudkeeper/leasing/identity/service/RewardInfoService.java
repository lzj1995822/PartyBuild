package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.RewardInfo;
import com.cloudkeeper.leasing.base.service.BaseService;

import java.util.List;

/**
 * 报酬情况 service
 * @author asher
 */
public interface RewardInfoService extends BaseService<RewardInfo> {

    void deleteAllByCadresId(String cadresId);

    List<RewardInfo> findAllByCadresId(String cadresId);

}