package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParPictureInfro;
import com.cloudkeeper.leasing.base.service.BaseService;

/**
 * 电视截图 service
 * @author lxw
 */
public interface ParPictureInfroService extends BaseService<ParPictureInfro> {


    ParPictureInfro findByRedisUuid(String redisUuid);
}