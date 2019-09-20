package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.ParMember;
import com.cloudkeeper.leasing.base.service.BaseService;

/**
 * 党员管理 service
 * @author cqh
 */
public interface ParMemberService extends BaseService<ParMember> {

    Integer countAll(String districtId);

}