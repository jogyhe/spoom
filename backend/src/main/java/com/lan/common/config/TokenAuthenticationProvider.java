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

import java.util.Date;

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
        String email = decryptedToken[1];
        Date expireTime = new Date(Long.valueOf(decryptedToken[2]));
        if (token == null || email == null || expireTime == null) {
            throw new BadCredentialsException("Token is invalid!");
        }
        logger.info("Token: " + token + " email: " + email + " expireTime: " + expireTime);

        UserEntity user;
        try {
            user = this.getUserService().getUserByEmail(email);
            if (user == null) {
                throw new BadCredentialsException("Cannot find user by this token!");
            }
        } catch (UsernameNotFoundException e) {
            logger.error("Cannot find user by this token!");
            throw new BadCredentialsException("Cannot find user by this token!");
        }
        //加上缓存功能以后，应当可以从缓存中直接查看是否有该token并进行鉴权，缓存由AuthController 更新
        TokenEntity tokenEntity = this.getTokenService().getByToken(token);
        if (tokenEntity == null) {
            throw new BadCredentialsException("Illegal token, access denied!");
        } else if (tokenEntity.getExpireTime().after(new Date())) {
            // user中的password信息需要抹掉
            user.setPassword("");
            TokenAuthentication result = new TokenAuthentication(user, authentication, user.getAuthorities());
            return result;
        } else {
            throw new BadCredentialsException("Token has expired!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.isAssignableFrom(authentication);
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
}
