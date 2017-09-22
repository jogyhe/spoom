package com.lan.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lan.common.model.Message;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * package com.lan.common.config
 *
 * @author lanzongxiao
 * @date 2017/9/18
 */
public class AccessDeniedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Message message = new Message(1001, "Anonymous denied!");
        response.getWriter().write(JSON.toJSONString(message, SerializerFeature.WriteMapNullValue));
        response.getWriter().close();
    }
}
