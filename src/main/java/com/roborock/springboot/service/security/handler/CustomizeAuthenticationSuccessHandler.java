package com.roborock.springboot.service.security.handler;

import com.alibaba.fastjson.JSON;
import com.roborock.springboot.service.common.domain.AjaxResult;
import com.roborock.springboot.service.common.domain.LoginUser;
import com.roborock.springboot.service.domain.SysUser;
import com.roborock.springboot.service.service.SysUserService;
import com.roborock.springboot.service.util.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @Author: BoHanZhang
 * @Description: 登录成功处理逻辑
 * @Date Create in 2021/2/18 15:52
 */
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //更新用户表上次登录时间、更新人、更新时间等字段
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
/*        SysUser sysUser = sysUserService.selectByUserName(loginUser.getUsername());
        sysUser.setLastLoginTime(new Date());
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateUser(sysUser.getId());
        sysUserService.update(sysUser);*/
        //此处还可以进行一些处理，比如登录成功之后可能需要返回给前台当前用户有哪些菜单权限，
        //进而前台动态的控制菜单的显示等，具体根据自己的业务需求进行扩展
        //创建用户token
        String token = jwtUtil.createToken(loginUser);
        //返回json数据
        AjaxResult result = AjaxResult.success();
        result.put("token",token);
        //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("text/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
