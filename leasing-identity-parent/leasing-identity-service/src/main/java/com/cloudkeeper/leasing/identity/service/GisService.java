package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.base.domain.BaseEntity;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.vo.GisVO;

import java.util.List;

public interface GisService extends BaseService<BaseEntity> {

    List<GisVO> queryPosition();
}
