package com.lan.common.config;

import com.lan.common.model.TokenEntity;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Cache;
import javax.cache.CacheManager;

/**
 * package com.lan.common.config
 *
 * @author lanzongxiao
 * @date 2017/9/19
 */
@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public Cache<Integer,TokenEntity> tokenCache(CacheManager cacheManager) {
        return cacheManager.getCache("tokenCache");
    }
}
