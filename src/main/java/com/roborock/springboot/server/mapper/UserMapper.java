package com.roborock.springboot.server.mapper;

import com.roborock.springboot.server.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> getList();

    User getById(String id);

    int insert(User user);

    int update(User user);

    int delete(String id);

}
