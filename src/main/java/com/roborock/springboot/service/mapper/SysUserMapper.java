package com.roborock.springboot.service.mapper;

import com.roborock.springboot.service.domain.SysUser;
import tk.mybatis.mapper.common.Mapper;

public interface SysUserMapper extends Mapper<SysUser> {

    SysUser selectByUserName(String userName);
}