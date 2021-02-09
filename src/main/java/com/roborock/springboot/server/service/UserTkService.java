package com.roborock.springboot.server.service;

import com.roborock.springboot.server.domain.UserTest;
import com.roborock.springboot.server.domain.vo.UserTestVo;
import com.roborock.springboot.server.mapper.UserTkMapper;
import com.roborock.springboot.server.util.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
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

    public List<UserTest> queryListByExample(UserTestVo vo) {
        Example example = new Example(UserTest.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotNull(vo.getBeginTime()) && StringUtils.isNotNull(vo.getEndTime())) {
            criteria.andBetween("createTime", vo.getBeginTime(), vo.getEndTime());
        }
        if (StringUtils.isNotEmpty(vo.getName())) {
            criteria.andLike("name","%" + vo.getName() + "%");
        }
        return mapper.selectByExample(example);
    }
}
