package com.lan.common.service;

import com.lan.common.dao.UserMapper;
import com.lan.common.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * package com.lan.service
 *
 * @author spoomlzx
 * @date 2017/6/10
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public UserEntity getUserByEmail(String email) {
        UserEntity user = userMapper.getUserByEmail(email);
        return user;
    }

    @Transactional
    public void regUser(UserEntity user) {
        try {
            userMapper.insertUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void updateUser(UserEntity user) {
        try {
            userMapper.updateUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
