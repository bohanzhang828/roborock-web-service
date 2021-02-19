package com.roborock.springboot.service.service.impl;

import com.roborock.springboot.service.domain.UserTest;
import com.roborock.springboot.service.domain.vo.UserTestVo;
import com.roborock.springboot.service.mapper.UserTkMapper;
import com.roborock.springboot.service.service.UserTkService;
import com.roborock.springboot.service.util.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author BoHanZhang
 * @Date Create in 2021/2/18 15:49
 * @Description
 */
@Service
public class UserTkServiceImpl extends AbstractService<UserTest> implements UserTkService {

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
