package com.roborock.springboot.server.mapper;

import com.roborock.springboot.server.bean.UserTest;
import tk.mybatis.mapper.common.Mapper;

public interface UserTkMapper extends Mapper<UserTest> {

    int deleteTest(String id);

}
