package com.roborock.springboot.service.service.impl;

import com.roborock.springboot.service.domain.SysUser;
import com.roborock.springboot.service.mapper.SysUserMapper;
import com.roborock.springboot.service.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl extends AbstractService<SysUser> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    public SysUser selectByUserName(String userName) {
        SysUser sysUser = sysUserMapper.selectByUserName(userName);
        return sysUser;
    }
}
