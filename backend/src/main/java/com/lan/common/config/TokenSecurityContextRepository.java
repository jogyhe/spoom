package com.lan.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * package com.lan.common.config
 *
 * @author lanzongxiao
 * @date 2017/9/15
 */
public class TokenSecurityContextRepository implements SecurityContextRepository {
    private final static Logger logger = LoggerFactory.getLogger(TokenSecurityContextRepository.class);

    private AuthenticationManager authenticationManager;

    public TokenSecurityContextRepository setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        return this;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder httpRequestResponseHolder) {
        HttpServletRequest request = httpRequestResponseHolder.getRequest();
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        String token = request.getHeader("Token");
        //如果是不带token的请求，视为anonymity call，直接返回一个emptyContext();等待其他filter
        if (token != null) {
            logger.error("Token: " + token);
            TokenAuthentication result = new TokenAuthentication("authenticate token", token);
            try {
                Authentication authResult = this.authenticationManager.authenticate(result);
                securityContext.setAuthentication(authResult);
            } catch (AuthenticationException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.error("There is no token, anonymity call");
        }
        return securityContext;
    }

    @Override
    public void saveContext(SecurityContext securityContext, HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public boolean containsContext(HttpServletRequest httpServletRequest) {
        return false;
    }
}
