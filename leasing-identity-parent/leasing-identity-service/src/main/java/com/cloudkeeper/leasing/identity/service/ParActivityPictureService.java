package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParActivityPicture;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.domain.ParPictureInfro;

/**
 * 手机截图 service
 * @author lxw
 */
public interface ParActivityPictureService extends BaseService<ParActivityPicture> {

    ParActivityPicture findByRedisUuid(String redisUuid);
}