package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.TVSignIn;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.dto.tvsignin.TVSignInDTO;

/**
 * 远教视频签到记录 service
 * @author cqh
 */
public interface TVSignInService extends BaseService<TVSignIn> {

    TVSignIn save( String id,Integer type,String username);

    TVSignIn updateFlag(String id,Integer flag);
}