package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.AcceptInformation;
import com.cloudkeeper.leasing.base.service.BaseService;

/**
 * 接收公告 service
 * @author cqh
 */
public interface AcceptInformationService extends BaseService<AcceptInformation> {

    /**
     * 根据informationId删除所有
     * @param informationId
     * @return
     */
    Integer deleteAllByInformationId(String informationId);
}