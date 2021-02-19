package com.roborock.springboot.service.service;

import com.roborock.springboot.service.domain.SysMenu;

import java.util.List;
import java.util.Set;

public interface SysMenuService extends BaseService<SysMenu> {

    List<SysMenu> selectListByUser(String userId);

    Set<String> selectMenuPermsByUserId(String userId);

}
