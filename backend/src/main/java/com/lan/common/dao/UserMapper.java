package com.lan.common.dao;

import com.lan.common.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * package com.lan.dao
 *
 * @author spoomlzx
 * @date 2017/5/29
 */
@Mapper
public interface UserMapper {

    UserEntity getUserByEmail(String email);

    int updateUser(@Param("user") UserEntity user);

    int insertUser(@Param("user") UserEntity user);
}
