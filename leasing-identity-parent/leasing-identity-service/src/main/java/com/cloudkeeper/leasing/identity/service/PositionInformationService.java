package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.PositionInformation;
import com.cloudkeeper.leasing.base.service.BaseService;

/**
 * 阵地信息 service
 * @author cqh
 */
public interface PositionInformationService extends BaseService<PositionInformation> {
    //统计阵地数量
    Integer countAllByDistrictId(String districtId);
}