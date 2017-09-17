package com.lan.common.config;

import com.lan.common.model.TokenEntity;
import com.lan.common.service.TokenService;
import com.lan.common.util.Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    private UserDetailsService userDetailsService;
    private UserDetailsChecker preAuthenticationChecks = new TokenAuthenticationProvider.DefaultPreAuthenticationChecks();
    private UserDetailsChecker postAuthenticationChecks = new TokenAuthenticationProvider.DefaultPostAuthenticationChecks();
    private TokenService tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(TokenAuthentication.class, authentication, "Only TokenAuthenticationProvider is supported");
        String encryptedToken = (String) authentication.getCredentials();
        String[] decryptedToken = new String[3];
        try {
            decryptedToken = Encrypt.aesDecrypt(encryptedToken).split("#");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String token = decryptedToken[0];
        String email = decryptedToken[1];
        Date expireTime = new Date(Long.valueOf(decryptedToken[2]));
        logger.info("Token: " + token + " email: " + email + " expireTime: " + expireTime);
        UserDetails user;
        try {
            user = this.getUserDetailsService().loadUserByUsername(email);
            if (user == null) {
                throw new BadCredentialsException("Cannot find user by this token!");
            }
        } catch (UsernameNotFoundException e) {
            logger.error("Cannot find user by this token!");
            throw new BadCredentialsException("Cannot find user by this token!");
        }
        this.preAuthenticationChecks.check(user);
        TokenEntity tokenEntity = this.getTokenService().getByToken(token);
        if (tokenEntity == null) {
            throw new BadCredentialsException("Illegal token, access denied!");
        } else if (tokenEntity.getExpireTime().after(new Date())) {
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

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public TokenAuthenticationProvider setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        return this;
    }

    public TokenService getTokenService() {
        return tokenService;
    }

    public TokenAuthenticationProvider setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
        return this;
    }

    public UserDetailsChecker getPreAuthenticationChecks() {
        return preAuthenticationChecks;
    }

    public void setPreAuthenticationChecks(UserDetailsChecker preAuthenticationChecks) {
        this.preAuthenticationChecks = preAuthenticationChecks;
    }

    public UserDetailsChecker getPostAuthenticationChecks() {
        return postAuthenticationChecks;
    }

    public void setPostAuthenticationChecks(UserDetailsChecker postAuthenticationChecks) {
        this.postAuthenticationChecks = postAuthenticationChecks;
    }

    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {
        private DefaultPostAuthenticationChecks() {
        }

        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                TokenAuthenticationProvider.this.logger.debug("User account credentials have expired");
                throw new CredentialsExpiredException("AbstractUserDetailsAuthenticationProvider.credentialsExpired");
            }
        }
    }

    private class DefaultPreAuthenticationChecks implements UserDetailsChecker {
        private DefaultPreAuthenticationChecks() {
        }

        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                TokenAuthenticationProvider.this.logger.debug("User account is locked");
                throw new LockedException("AbstractUserDetailsAuthenticationProvider.locked");
            } else if (!user.isEnabled()) {
                TokenAuthenticationProvider.this.logger.debug("User account is disabled");
                throw new DisabledException("AbstractUserDetailsAuthenticationProvider.disabled");
            } else if (!user.isAccountNonExpired()) {
                TokenAuthenticationProvider.this.logger.debug("User account is expired");
                throw new AccountExpiredException("AbstractUserDetailsAuthenticationProvider.expired");
            }
        }
    }
}
