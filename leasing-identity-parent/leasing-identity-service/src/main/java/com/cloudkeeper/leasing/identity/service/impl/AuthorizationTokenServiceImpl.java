package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.repository.BaseRepository;
import com.cloudkeeper.leasing.base.service.impl.BaseServiceImpl;
import com.cloudkeeper.leasing.base.utils.TokenUtil;
import com.cloudkeeper.leasing.identity.domain.AuthorizationToken;
import com.cloudkeeper.leasing.identity.dto.authorizationtoken.AuthorizationTokenDTO;
import com.cloudkeeper.leasing.identity.repository.AuthorizationTokenRepository;
import com.cloudkeeper.leasing.identity.service.AuthorizationTokenService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * 访问权限 service
 * @author cqh
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizationTokenServiceImpl extends BaseServiceImpl<AuthorizationToken> implements AuthorizationTokenService {

    /** 访问权限 repository */
    private final AuthorizationTokenRepository authorizationTokenRepository;

    private final RedisTemplate redisTemplate;


    @Override
    protected BaseRepository<AuthorizationToken> getBaseRepository() {
        return authorizationTokenRepository;
    }

    @Override
    public ExampleMatcher defaultExampleMatcher() {
        return super.defaultExampleMatcher()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("code", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("des", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("tokenVal", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    @Override
    public AuthorizationToken save(AuthorizationTokenDTO authorizationTokenDTO) {
        if(StringUtils.isEmpty(authorizationTokenDTO.getCode())){
           return null;
        }
        String of = TokenUtil.of(authorizationTokenDTO.getCode());
        if(!StringUtils.isEmpty(authorizationTokenDTO.getId())){
            AuthorizationToken byId = super.findById(authorizationTokenDTO.getId());
            if(byId.getIsUse().equals("1")){
                redisTemplate.delete(byId.getCode());
            }
        }
        authorizationTokenDTO.setTokenVal(of);
        authorizationTokenDTO.setIsUse("0");
        return super.save(authorizationTokenDTO.convert(AuthorizationToken.class));
    }

    @Override
    public Boolean delete(String id) {
        Optional<AuthorizationToken> optionalById = super.findOptionalById(id);
        if (!optionalById.isPresent()){
            return false;
        }
        AuthorizationToken authorizationToken = optionalById.get();
        if(authorizationToken.getIsUse().equals("1")){
            redisTemplate.delete(authorizationToken.getCode());
        }
        super.deleteById(id);
        return true;
    }

    @Override
    public Boolean updateRedis(@NonNull String id, @NonNull String isUse) {
        Optional<AuthorizationToken> optionalById = super.findOptionalById(id);
        if (!optionalById.isPresent()){
            return false;
        }
        AuthorizationToken authorizationToken = optionalById.get();
        /*isUse :1 启用，0不启用*/
        if(isUse.equals("1")){
            redisTemplate.opsForValue().set(authorizationToken.getCode(),authorizationToken.getTokenVal());
        }else if(isUse.equals("0")){
            redisTemplate.delete(authorizationToken.getCode());
        }else{
            return false;
        }
        authorizationToken.setIsUse(isUse);
        super.save(authorizationToken);
        return true;
    }
}