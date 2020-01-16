package com.cloudkeeper.leasing.identity.service;

import com.cloudkeeper.leasing.identity.domain.AuthorizationToken;
import com.cloudkeeper.leasing.base.service.BaseService;
import com.cloudkeeper.leasing.identity.dto.authorizationtoken.AuthorizationTokenDTO;

/**
 * 访问权限 service
 * @author cqh
 */
public interface AuthorizationTokenService extends BaseService<AuthorizationToken> {

   /*重写添加权限管理*/
    AuthorizationToken save(AuthorizationTokenDTO authorizationTokenDTO);

    /*重写删除权限管理*/
    Boolean delete(String id);

    /*token存入redis*/
    Boolean updateRedis(String id,String isUse);

}