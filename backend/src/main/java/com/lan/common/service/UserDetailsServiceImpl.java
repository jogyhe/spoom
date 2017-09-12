package com.lan.common.service;

import com.lan.common.dao.UserMapper;
import com.lan.common.model.User;
import com.lan.common.model.utilModel.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * package com.lan.service
 *
 * @author spoomlzx
 * @date 2017/5/29
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOGGER= LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private UserInfo userInfo;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userMapper.getUserByEmail(username);
        if(user!=null){
            LOGGER.info("user: "+user.getUserName()+" had being found");
            userInfo = new UserInfo(user);
        }
        return userInfo;
    }
}
