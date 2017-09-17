package com.lan.common.service;

import com.lan.common.model.TokenEntity;

public interface TokenService {

    TokenEntity getByToken(String token);

    TokenEntity getByUserId(Integer userId);

    int insertToken(TokenEntity token);

    int updateToken(TokenEntity token);

    String createToken(Integer userId, String email);
}
