package com.roborock.springboot.server.controller;

import com.roborock.springboot.server.common.controller.BaseController;
import com.roborock.springboot.server.common.domain.AjaxResult;
import com.roborock.springboot.server.common.page.TableDataInfo;
import com.roborock.springboot.server.domain.UserTest;
import com.roborock.springboot.server.config.TestConfig;
import com.roborock.springboot.server.domain.vo.UserTestVo;
import com.roborock.springboot.server.service.UserTkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/roborock/user")
public class TestController extends BaseController {

    @Resource
    private TestConfig config;

    @Resource
    private UserTkService userService;

    @GetMapping(value = "/all")
    public List<UserTest> getAll() {
        return userService.queryAll();
    }

    @GetMapping(value = "/{id}")
    public AjaxResult get(@PathVariable(value = "id")String id) {
        return AjaxResult.success(userService.queryById(id));
    }

    @PostMapping
    public AjaxResult insert(@RequestBody UserTest userTest){
        return toAjax(userService.saveSelective(userTest));
    }

    @PutMapping(value = "/{id}")
    public AjaxResult update(@PathVariable(value = "id")String id, @RequestBody UserTest userTest){
        userTest.setId(id);
        return toAjax(userService.updateSelective(userTest));
    }

    @DeleteMapping(value = "/{id}")
    public AjaxResult update(@PathVariable(value = "id")String id){
        return toAjax(userService.deleteTest(id));
    }

    @GetMapping(value = "/list")
    public TableDataInfo getPageList(UserTest userTest) {
        startPage();
        List<UserTest> list = userService.queryListByWhere(userTest);
        return getDataTable(list);
    }

    @GetMapping(value = "/list/date")
    public TableDataInfo getPageListByExample(@RequestBody UserTestVo vo) {
        startPage();
        List<UserTest> list = userService.queryListByExample(vo);
        return getDataTable(list);
    }

}
