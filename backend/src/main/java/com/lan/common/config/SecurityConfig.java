package com.lan.common.config;

import com.lan.common.service.TokenService;
import com.lan.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .exceptionHandling().authenticationEntryPoint(new AccessDeniedEntryPoint()) // 当header中没有token时，返回json
            .accessDeniedHandler(new ApiAccessDeniedHandler()) // 当token认证出错时返回json
            .and()
            .securityContext().securityContextRepository(new TokenSecurityContextRepository().setAuthenticationManager(this.authenticationManager()))
            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/**").permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .authenticationProvider(
                new TokenAuthenticationProvider()
                    .setUserService(userService)
                    .setTokenService(tokenService)
            );
    }

}
