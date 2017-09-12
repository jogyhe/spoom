package com.lan.common.model.utilModel;

import com.lan.common.model.User;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * package com.lan
 * extends User or implements UserDetails are both OK!
 * @author spoomlzx
 * @date 2016/8/24
 */
public class UserInfo extends org.springframework.security.core.userdetails.User {
    
    private static final long serialVersionUID = 5482288529004276910L;
    private User user;

    public UserInfo(User user){
        super(user.getEmail(), user.getPassword(),true,true,true,true,
                AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles()));
        this.user=user;
    }

    public User getUser(){
        return this.user;
    }

    public Integer getUserId(){
        return this.user.getUserId();
    }

    public String getUserName(){
        return this.user.getUserName();
    }

    public String getAvatar(){
        return this.user.getAvatar();
    }
}
