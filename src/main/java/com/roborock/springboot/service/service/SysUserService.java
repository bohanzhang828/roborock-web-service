package com.roborock.springboot.service.service;

import com.roborock.springboot.service.domain.SysUser;

public interface SysUserService extends BaseService<SysUser> {

    SysUser selectByUserName(String userName);
}