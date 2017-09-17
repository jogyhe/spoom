package com.lan;

import com.lan.common.service.TokenService;
import com.lan.common.util.Encrypt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void testCreateToken() {
        String encryptedToken = tokenService.createToken(1,"spoomlzx@qq.com");
        System.out.println("加密过后的token:" + encryptedToken);
        try {
            String tokenString = Encrypt.aesDecrypt(encryptedToken);
            System.out.println(tokenString);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}