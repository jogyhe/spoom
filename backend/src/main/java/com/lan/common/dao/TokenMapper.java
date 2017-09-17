package com.lan.common.dao;

import com.lan.common.model.TokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TokenMapper {

    TokenEntity getByToken(@Param("token") String token);

    TokenEntity getByUserId(@Param("userId") Integer userId);

    int insertToken(@Param("token") TokenEntity token);

    int updateToken(@Param("token") TokenEntity token);
}
