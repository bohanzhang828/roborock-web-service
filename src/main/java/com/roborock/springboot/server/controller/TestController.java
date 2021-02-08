package com.roborock.springboot.server.controller;

import com.github.pagehelper.PageInfo;
import com.roborock.springboot.server.bean.PageRequest;
import com.roborock.springboot.server.bean.UserTest;
import com.roborock.springboot.server.config.TestConfig;
import com.roborock.springboot.server.service.UserTkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/roborock/user")
public class TestController {

    @Resource
    private TestConfig config;

    @Resource
    private UserTkService userService;

    @GetMapping(value = "/all")
    public List<UserTest> getAll() {
        return userService.queryAll();
    }

    @GetMapping(value = "/{id}")
    public UserTest get(@PathVariable(value = "id")String id) {
        return userService.queryById(id);
    }

    @PostMapping
    public int insert(@RequestBody UserTest userTest){
        return userService.saveSelective(userTest);
    }

    @PutMapping(value = "/{id}")
    public int update(@PathVariable(value = "id")String id, @RequestBody UserTest userTest){
        userTest.setId(id);
        return userService.updateSelective(userTest);
    }

    @DeleteMapping(value = "/{id}")
    public int update(@PathVariable(value = "id")String id){
        return userService.deleteTest(id);
    }

    @GetMapping(value = "/list")
    public PageInfo getPageList(@RequestBody PageRequest<UserTest> pageRequest) {
        return userService.queryPageListByWhere(pageRequest.getPageNum(), pageRequest.getPageSize(), pageRequest.getContent());
    }

}
