package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.MessageCenter;
import com.cloudkeeper.leasing.base.service.BaseService;

import javax.annotation.Nonnull;

/**
 * 消息中心 service
 * @author cqh
 */
public interface MessageCenterService extends BaseService<MessageCenter> {

    @Nonnull
    MessageCenter save(@Nonnull String activityId, String districtId, String taskType);
}