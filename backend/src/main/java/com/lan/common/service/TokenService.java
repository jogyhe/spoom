package com.lan.common.service;

import com.lan.common.model.TokenEntity;
import com.lan.common.model.UserEntity;

public interface TokenService {

    TokenEntity getByToken(String token);

    TokenEntity getByUserId(Integer userId);

    int insertToken(TokenEntity token);

    int updateToken(TokenEntity token);

    String createToken(UserEntity userEntity);
}
