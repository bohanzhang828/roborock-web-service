package com.roborock.springboot.service.security.handler;

import com.alibaba.fastjson.JSON;
import com.roborock.springboot.service.common.domain.AjaxResult;
import com.roborock.springboot.service.common.domain.LoginUser;
import com.roborock.springboot.service.util.ServletUtils;
import com.roborock.springboot.service.util.StringUtils;
import com.roborock.springboot.service.util.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author BoHanZhang
 * @Date Create in 2021/2/18 14:28
 * @Description 登出成功处理
 */
@Component
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        LoginUser loginUser = jwtUtil.getLoginUser(httpServletRequest);
        if (StringUtils.isNotNull(loginUser))
        {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            jwtUtil.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
        }
        ServletUtils.renderString(httpServletResponse, JSON.toJSONString(AjaxResult.success("退出成功")));
    }
}
