package com.roborock.springboot.server.mapper;

import com.roborock.springboot.server.domain.UserTest;
import tk.mybatis.mapper.common.Mapper;

public interface UserTkMapper extends Mapper<UserTest> {

    int deleteTest(String id);

}
