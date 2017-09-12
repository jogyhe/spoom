package com.lan.common.service;

import com.lan.common.dao.UserMapper;
import com.lan.common.model.User;
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

    @Transactional
    public void regUser(User user){
        try {
            userMapper.insertUser(user);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public User testEmail(String email){
        User user=userMapper.getUserByEmail(email);
        return user;
    }

    @Transactional
    public void updateUser(User user){
        try {
            userMapper.updateUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
