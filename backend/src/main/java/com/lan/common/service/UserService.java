package com.lan.common.service;

import com.lan.common.model.UserEntity;

/**
 * package com.lan.common.service
 *
 * @author lanzongxiao
 * @date 2017/9/18
 */
public interface UserService {

    UserEntity getUserById(Integer userId);

    UserEntity getUserByEmail(String email);

    void regUser(UserEntity user);

    void updateUser(UserEntity user);
}
