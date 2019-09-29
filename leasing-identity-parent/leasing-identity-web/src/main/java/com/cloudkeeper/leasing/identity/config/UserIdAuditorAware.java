package com.cloudkeeper.leasing.identity.config;

import com.cloudkeeper.leasing.base.constant.AuthorizationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * 自动注入创建人，更新人id
 * @author jerry
 */
@Component
public class UserIdAuditorAware implements AuditorAware<String> {

    /** http 会话*/
    @Autowired
    private HttpSession session;

    @Override
    public Optional<String> getCurrentAuditor() {
        String principalId = null;
        try {
            principalId  = (String) session.getAttribute(AuthorizationConstants.CURRENT_USER_ID);
        } catch (IllegalStateException e) {
        }
        return Optional.ofNullable(principalId);
    }
}
