package com.roborock.springboot.server.service;

import com.roborock.springboot.server.bean.UserTest;
import com.roborock.springboot.server.mapper.UserTkMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class UserTkService extends BaseService<UserTest>{

    @Resource
    private UserTkMapper mapper;

    public Integer deleteTest(String id) {
        return mapper.deleteTest(id);
    }

    @Override
    public Integer saveSelective(UserTest userTest) {
        userTest.setId(UUID.randomUUID().toString().replaceAll("-",""));
        userTest.setCreateTime(new Date());
        userTest.setUpdateTime(userTest.getCreateTime());
        return mapper.insertSelective(userTest);
    }
}
