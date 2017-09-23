package com.lan.common.controller;

import com.lan.common.model.Message;
import com.lan.common.model.UserEntity;
import com.lan.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * package com.lan.common.controller
 *
 * @author lanzongxiao
 * @date 2017/9/23
 */
@CrossOrigin(
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS},
    allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Message getUserInfo(@AuthenticationPrincipal UserEntity userEntity) {
        if (userEntity == null) {
            return new Message(0, "User info is empty");
        }
        Message message = new Message(1, "Get user info success");
        message.setData(userEntity);
        return message;
    }
}
