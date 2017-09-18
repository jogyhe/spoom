package com.lan.common.service;

import com.lan.common.dao.TokenMapper;
import com.lan.common.model.TokenEntity;
import com.lan.common.util.Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
    private final static Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private TokenMapper tokenMapper;

    public TokenEntity getByToken(String token) {
        return tokenMapper.getByToken(token);
    }

    @Override
    public TokenEntity getByUserId(Integer userId) {
        return tokenMapper.getByUserId(userId);
    }

    @Transactional
    public int insertToken(TokenEntity token) {
        try {
            tokenMapper.insertToken(token);
            return token.getUserId();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public int updateToken(TokenEntity token) {
        try {
            tokenMapper.updateToken(token);

            return token.getUserId();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 返回给客户端的token为  uuid#userId#expiretime
     * 并使用AES进行加密
     *
     * @param username
     * @return
     */
    public String createToken(Integer userId, String username) {
        //生成一个uuid存入数据库中的token表
        String token = UUID.randomUUID().toString().replace("-", "");
        //当前时间
        Date now = new Date();
        logger.info("Date: " + now.getTime());
        //过期时间
        Date expireTime = new Date(now.getTime() + 3600 * 12 * 1000);
        logger.info("ExpireTime: " + expireTime.getTime());

        String encryptedToken = token + "#" + username + "#" + expireTime.getTime();
        try {
            encryptedToken = Encrypt.aesEncrypt(encryptedToken);
            logger.info("Return token is: " + encryptedToken);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //判断是否生成过token
        TokenEntity tokenEntity = getByUserId(userId);
        if (tokenEntity == null) {
            tokenEntity = new TokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //保存token
            insertToken(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //更新token
            updateToken(tokenEntity);
        }
        return encryptedToken;
    }

}
