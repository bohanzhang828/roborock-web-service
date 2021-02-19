package com.roborock.springboot.service.service;

import com.roborock.springboot.service.domain.UserTest;
import com.roborock.springboot.service.domain.vo.UserTestVo;

import java.util.List;

public interface UserTkService extends BaseService<UserTest> {

    Integer deleteTest(String id);

    Integer saveSelective(UserTest userTest);

    List<UserTest> queryListByExample(UserTestVo vo);
}
