package com.roborock.springboot.server.service.impl;

import com.roborock.springboot.server.bean.User;
import com.roborock.springboot.server.mapper.UserMapper;
import com.roborock.springboot.server.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> getUserList() {
        return userMapper.getList();
    }

    @Override
    public User getUserById(String id) {
        return userMapper.getById(id);
    }

    @Override
    public int insertUser(User user) {
        user.setId(UUID.randomUUID().toString().replaceAll("-",""));
        return userMapper.insert(user);
    }

    @Override
    public int updateUserById(User user) {
        return userMapper.update(user);
    }

    @Override
    public int deleteUserById(String id) {
        return userMapper.delete(id);
    }
}
