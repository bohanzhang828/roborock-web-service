package com.roborock.springboot.service.security;

import com.alibaba.fastjson.JSON;
import com.roborock.springboot.service.common.domain.AjaxResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @Author: BoHanZhang
 * @Description: 匿名用户访问无权限资源时异常
 * @Date Create in 2021/2/18 15:52
 */
@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(AjaxResult.error(2001,"用户未登录")));
    }
}
