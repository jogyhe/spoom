package com.lan.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lan.common.model.Message;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * package com.lan.common.config
 * 默认所有的无权限访问都返回一个json对象，包含code & msg
 *
 * @author lanzongxiao
 * @date 2017/9/16
 */
public class ApiAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Message message = new Message(0, "Access denied!");
        response.getWriter().write(JSON.toJSONString(message, SerializerFeature.WriteMapNullValue));
        response.getWriter().close();
    }
}
