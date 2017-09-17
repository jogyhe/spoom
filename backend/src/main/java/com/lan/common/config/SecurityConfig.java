package com.lan.common.config;

import com.lan.common.service.TokenService;
import com.lan.common.service.UserDetailsServiceImpl;
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
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private TokenService tokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .exceptionHandling().accessDeniedHandler(new ApiAccessDeniedHandler()).and()
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
                    .setUserDetailsService(userDetailsService)
                    .setTokenService(tokenService)
            );
    }

}
