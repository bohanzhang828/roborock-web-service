package com.roborock.springboot.service.mapper;

import com.roborock.springboot.service.domain.UserTest;
import tk.mybatis.mapper.common.Mapper;

public interface UserTkMapper extends Mapper<UserTest> {

    int deleteTest(String id);

}
