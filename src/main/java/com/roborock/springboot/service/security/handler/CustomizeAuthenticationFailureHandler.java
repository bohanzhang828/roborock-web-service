package com.roborock.springboot.service.security.handler;

import com.alibaba.fastjson.JSON;
import com.roborock.springboot.service.common.domain.AjaxResult;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author BoHanZhang
 * @Date Create in 2021/2/18 14:21
 * @Description 登录失败处理逻辑
 */
@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        AjaxResult result;
        if (e instanceof AccountExpiredException) {
            //账号过期
            result = AjaxResult.error(2002,"账号过期");
        } else if (e instanceof BadCredentialsException) {
            //密码错误
            result = AjaxResult.error(2003,"用户名或密码错误");
        } else if (e instanceof CredentialsExpiredException) {
            //密码过期
            result = AjaxResult.error(2004,"密码过期");
        } else if (e instanceof DisabledException) {
            //账号不可用
            result = AjaxResult.error(2005,"账号不可用");
        } else if (e instanceof LockedException) {
            //账号锁定
            result = AjaxResult.error(2006,"账号锁定");
        } else if (e instanceof InternalAuthenticationServiceException) {
            //用户不存在
            result = AjaxResult.error(2007,"用户不存在");
        }else{
            //其他错误
            result = AjaxResult.error(9999,"其他错误");
        }
        //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("text/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
