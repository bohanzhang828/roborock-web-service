package com.roborock.springboot.service.security;

import com.roborock.springboot.service.common.domain.LoginUser;
import com.roborock.springboot.service.util.ServletUtils;
import com.roborock.springboot.service.util.StringUtils;
import com.roborock.springboot.service.util.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author BoHanZhang
 * @Date Create in 2021/2/19 15:04
 * @Description
 */
@Component
public class CustomizePermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        String permissionStr = (String)permission;
        if (StringUtils.isEmpty(permissionStr)) {
            return false;
        }
        LoginUser loginUser = jwtUtil.getLoginUser(ServletUtils.getRequest());
        //LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())) {
            return false;
        }
        return hasPermissions(loginUser.getPermissions(), permissionStr);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }

    /**
     * 判断是否包含权限
     *
     * @param permissions 权限列表
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    private boolean hasPermissions(Set<String> permissions, String permission)
    {
        return permissions.contains(StringUtils.trim(permission));
    }
}
