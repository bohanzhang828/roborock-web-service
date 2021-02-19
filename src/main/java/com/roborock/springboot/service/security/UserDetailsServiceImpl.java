package com.roborock.springboot.service.security;

import com.roborock.springboot.service.common.domain.LoginUser;
import com.roborock.springboot.service.domain.SysMenu;
import com.roborock.springboot.service.domain.SysUser;
import com.roborock.springboot.service.service.SysMenuService;
import com.roborock.springboot.service.service.SysUserService;
import com.roborock.springboot.service.util.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(userName)) {
            throw new UsernameNotFoundException("用户名不能为空！");
        }
        SysUser sysUser = sysUserService.selectByUserName(userName);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }
        Set<String> permissions = sysMenuService.selectMenuPermsByUserId(sysUser.getId());
        return new LoginUser(sysUser, permissions);
    }
}
