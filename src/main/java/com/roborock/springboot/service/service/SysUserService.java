package com.roborock.springboot.service.service;

import com.roborock.springboot.service.domain.SysUser;
import com.roborock.springboot.service.domain.vo.SysUserVo;

import java.util.List;

public interface SysUserService extends BaseService<SysUser> {

    SysUser selectByUserName(String userName);

    String importUser(List<SysUserVo> userList, boolean updateSupport, String userId);
}