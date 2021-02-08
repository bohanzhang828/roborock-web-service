package com.roborock.springboot.server.service;

import com.roborock.springboot.server.bean.User;

import java.util.List;

public interface UserService {

    List<User> getUserList();

    User getUserById(String id);

    int insertUser(User user);

    int updateUserById(User user);

    int deleteUserById(String id);
}
