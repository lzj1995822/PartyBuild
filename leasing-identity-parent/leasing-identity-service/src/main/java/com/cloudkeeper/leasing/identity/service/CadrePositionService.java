package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.CadrePosition;
import com.cloudkeeper.leasing.base.service.BaseService;

/**
 * 岗位管理 service
 * @author cqh
 */
public interface CadrePositionService extends BaseService<CadrePosition> {

    Integer countVillageSecretaryNumber(String districtId,String post);

    CadrePosition findByDistrictIdAndPost(String districtId, String post);
}