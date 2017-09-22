package com.lan.common.config;

import com.lan.common.model.TokenEntity;
import com.lan.common.model.UserEntity;
import com.lan.common.service.TokenService;
import com.lan.common.service.UserService;
import com.lan.common.util.Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import javax.cache.Cache;
import java.util.Date;
import java.util.Objects;

/**
 * package com.lan.common.config
 *
 * @author lanzongxiao
 * @date 2017/9/15
 */
public class TokenAuthenticationProvider implements AuthenticationProvider {
    private final static Logger logger = LoggerFactory.getLogger(TokenAuthenticationProvider.class);

    private UserService userService;
    private TokenService tokenService;
    private Cache<Integer, TokenEntity> tokenCache;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(TokenAuthentication.class, authentication, "Only TokenAuthenticationProvider is supported");
        String encryptedToken = (String) authentication.getCredentials();
        String[] decryptedToken;
        try {
            decryptedToken = Encrypt.aesDecrypt(encryptedToken).split("#");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BadCredentialsException("Token is invalid!");
        }
        String token = decryptedToken[0];
        Integer userId = Integer.valueOf(decryptedToken[1]);
        Date expireTime = new Date(Long.valueOf(decryptedToken[2]));
        if (token == null || userId == null || expireTime == null) {
            throw new BadCredentialsException("Token is invalid!");
        }
        logger.info("Token: " + token + " userId: " + userId + " expireTime: " + expireTime.getTime());

        //加上缓存功能以后，应当可以从缓存中直接查看是否有该token并进行鉴权，缓存由AuthController 更新
        boolean isTokenValid = false;
        TokenEntity tokenEntity = this.getTokenCache().get(userId);
        try {
            isTokenValid = checkToken(tokenEntity, token, expireTime);
            logger.info("Cache token is used, token: " + token);
        } catch (AuthenticationException e) {
            tokenEntity = this.getTokenService().getByUserId(userId);
            isTokenValid = checkToken(tokenEntity, token, expireTime);
            if (tokenCache.get(userId) == null) {
                tokenCache.put(userId, tokenEntity);
            } else {
                tokenCache.replace(userId, tokenEntity);
            }
            logger.info("DB token is used, token: " + token);
        }
        UserEntity user = new UserEntity();
        if (isTokenValid) {
            try {
                user = this.getUserService().getUserById(userId);
                if (user == null) {
                    throw new BadCredentialsException("Cannot find user by this token!");
                }
            } catch (UsernameNotFoundException e) {
                logger.error("Cannot find user by this token!");
                throw new BadCredentialsException("Cannot find user by this token!");
            }
        }
        // user中的password信息需要抹掉
        user.setPassword("");
        TokenAuthentication result = new TokenAuthentication(user, authentication, user.getAuthorities());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.isAssignableFrom(authentication);
    }

    public boolean checkToken(TokenEntity tokenEntity, String token, Date expireTime) throws AuthenticationException {
        if (tokenEntity == null) {
            throw new BadCredentialsException("Cannot find this token!");
        }
        if (!Objects.equals(token, tokenEntity.getToken()) || !expireTime.equals(tokenEntity.getExpireTime())) {
            throw new BadCredentialsException("Forged token!");
        }
        if (tokenEntity.getExpireTime().before(new Date())) {
            throw new BadCredentialsException("Expired token!");
        }
        return true;
    }

    public UserService getUserService() {
        return userService;
    }

    public TokenAuthenticationProvider setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

    public TokenService getTokenService() {
        return tokenService;
    }

    public TokenAuthenticationProvider setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
        return this;
    }

    public Cache<Integer, TokenEntity> getTokenCache() {
        return tokenCache;
    }

    public TokenAuthenticationProvider setTokenCache(Cache<Integer, TokenEntity> tokenCache) {
        this.tokenCache = tokenCache;
        return this;
    }
}
