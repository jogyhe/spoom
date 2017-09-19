package com.lan;

import com.lan.common.model.TokenEntity;
import com.lan.common.model.UserEntity;
import com.lan.common.service.TokenService;
import com.lan.common.util.Encrypt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.cache.Cache;
import java.util.Date;

/**
 * package com.lan
 *
 * @author lanzongxiao
 * @date 2017/9/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenTest {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private Cache<Integer, TokenEntity> tokenCache;

    @Test
    public void testCreateToken() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("spoomlzx@qq.com");
        userEntity.setUserId(1);
        String encryptedToken = tokenService.createToken(userEntity);
        System.out.println("加密过后的token:" + encryptedToken);
        try {
            String tokenString = Encrypt.aesDecrypt(encryptedToken);
            System.out.println(tokenString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetUser() {
        TokenEntity tokenEntity = tokenService.getByUserId(3);
        tokenCache.put(3, tokenEntity);
        tokenEntity = tokenService.getByUserId(13);
        TokenEntity entity = tokenCache.get(3);
        System.out.println("Get cached user: " + entity.getUserId() + " with token: " + entity.getToken());
        System.out.println("Get DB user: " + tokenEntity.getUserId() + " with token: " + tokenEntity.getToken());
    }

    @Test
    public void testTokenTime() {
        TokenEntity tokenEntity = new TokenEntity();

        TokenEntity t = tokenService.getByUserId(3);
        tokenEntity.setUserId(3);
        tokenEntity.setToken("asdf");
        Date now = new Date();
        System.out.println("before time: " + now.getTime());
        tokenEntity.setExpireTime(now);
        if(t==null){
            tokenService.insertToken(tokenEntity);
        }
        t=tokenService.getByUserId(3);
        System.out.println("mysql time: " + t.getExpireTime().getTime());
        System.out.println("now time: " + now.getTime());
    }
}
