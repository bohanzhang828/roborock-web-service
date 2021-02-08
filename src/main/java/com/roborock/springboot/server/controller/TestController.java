package com.roborock.springboot.server.controller;

import com.roborock.springboot.server.bean.User;
import com.roborock.springboot.server.config.TestConfig;
import com.roborock.springboot.server.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/roborock/user")
public class TestController {

    @Resource
    private TestConfig config;

    @Resource
    private UserService userService;

    @GetMapping(value = "/list")
    public List<User> getList() {
        return userService.getUserList();
    }

    @GetMapping(value = "/{id}")
    public User get(@PathVariable(value = "id")String id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public int insert(@RequestBody User user){
        return userService.insertUser(user);
    }

    @PutMapping(value = "/{id}")
    public int update(@PathVariable(value = "id")String id, @RequestBody User user){
        user.setId(id);
        return userService.updateUserById(user);
    }

    @DeleteMapping(value = "/{id}")
    public int update(@PathVariable(value = "id")String id){
        return userService.deleteUserById(id);
    }

}
