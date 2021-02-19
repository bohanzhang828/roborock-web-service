package com.roborock.springboot.service.mapper;

import com.roborock.springboot.service.domain.SysMenu;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysMenuMapper extends Mapper<SysMenu> {

    /**
     * 查询用户的权限列表
     *
     * @param userId
     * @return
     */
    List<SysMenu> selectListByUser(String userId);

    List<String> selectMenuPermsByUserId(String userId);
}