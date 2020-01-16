package com.cloudkeeper.leasing.identity.auto;

import com.cloudkeeper.leasing.identity.domain.AuthorizationToken;
import com.cloudkeeper.leasing.identity.service.AuthorizationTokenService;
import com.cloudkeeper.leasing.identity.service.impl.FdfsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(value = 1)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenAutoRunner implements ApplicationRunner {

    // redis
    private final RedisTemplate<String,String> redisTemplate;

    private final AuthorizationTokenService authorizationTokenService;

    private final Logger logger = LoggerFactory.getLogger(TokenAutoRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<AuthorizationToken> all = authorizationTokenService.findAll();
        for (AuthorizationToken item : all) {
            redisTemplate.opsForValue().set(item.getCode(), item.getTokenVal());
            logger.info(item.getName() + "授权成功");
        }
    }
}
