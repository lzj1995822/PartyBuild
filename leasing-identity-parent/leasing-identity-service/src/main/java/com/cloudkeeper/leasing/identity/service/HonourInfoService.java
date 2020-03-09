package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.HonourInfo;
import com.cloudkeeper.leasing.base.service.BaseService;

import java.util.List;

/**
 * 表彰情况 service
 * @author asher
 */
public interface HonourInfoService extends BaseService<HonourInfo> {

    void deleteAllByCadresId(String cadresId);

    List<HonourInfo> findAllByCadresId(String cadresId);
}