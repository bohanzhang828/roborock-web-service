package com.roborock.springboot.service.service.impl;

import com.roborock.springboot.service.domain.SysMenu;
import com.roborock.springboot.service.mapper.SysMenuMapper;
import com.roborock.springboot.service.service.SysMenuService;
import com.roborock.springboot.service.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysMenuServiceImpl extends AbstractService<SysMenu> implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> selectListByUser(String userId) {
        List<SysMenu> list = sysMenuMapper.selectListByUser(userId);
        return list;
    }

    @Override
    public Set<String> selectMenuPermsByUserId(String userId)
    {
        List<String> perms = sysMenuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

}
