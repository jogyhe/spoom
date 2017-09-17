package com.lan.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * package com.lan.common.model
 *
 * @author lanzongxiao
 * @date 2017/9/16
 */
public class TokenEntity implements Serializable{
    private static final long serialVersionUID = 8522422227347696735L;
    private Integer userId;
    private String token;
    private Date expireTime;
    private Date updateTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
