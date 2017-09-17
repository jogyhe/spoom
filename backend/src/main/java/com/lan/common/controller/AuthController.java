package com.lan.common.controller;

import com.lan.common.model.Message;
import com.lan.common.model.UserEntity;
import com.lan.common.model.UserInfo;
import com.lan.common.service.TokenService;
import com.lan.common.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * package com.lan.common.controller
 *
 * @author lanzongxiao
 * @date 2017/9/15
 */
@CrossOrigin(
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS},
    allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 登录换取Token
     * token: base64(AESEncrypt(uuid # userId # expireTime))
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Message login(@RequestBody UserEntity user) {
        Message message = new Message(0, "user is not exits");
        UserInfo userInfo = null;
        try {
            userInfo = (UserInfo) userDetailsService.loadUserByUsername(user.getEmail());
        } catch (UsernameNotFoundException e) {
            logger.error("UserName cannot found");
        } catch (Exception e) {
            logger.error(e.getClass().getName() + ":" + e.getMessage());
        }
        if (userInfo == null) {
            message.setMsg("login failure");
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(user.getPassword(), userInfo.getPassword())) {
                String token = tokenService.createToken(userInfo.getUserId(), user.getEmail());
                message.setCode(1);
                message.setMsg("login success");
                message.setData(token);
            } else {
                message.setMsg("password invalid");
            }
        }
        return message;
    }

    /**
     * 注册user
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public Message register(@RequestBody UserEntity user) {
        Message message = new Message();
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = encoder.encode(user.getPassword());
            user.setPassword(password);
            userService.regUser(user);
            message.setMsg("register user success");
        } catch (RuntimeException e) {
            logger.error(e.getClass().getName() + ":" + e.getMessage());
            message.setCode(0);
            message.setMsg("register user failure");
        }
        return message;
    }
}
