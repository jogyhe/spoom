package com.lan.common.model;

import java.io.Serializable;

/**
 * package com.zzc.model
 *
 * @author spoomlzx
 * @date 2017/3/12
 */
public class Message implements Serializable {

    private static final long serialVersionUID = -8384851165488597002L;
    private int code;
    private String msg;
    private Object data;

    public Message() {
        this.code = 1;
    }

    public Message(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
