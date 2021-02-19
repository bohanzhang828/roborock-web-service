package com.roborock.springboot.service.controller;

import com.roborock.springboot.service.common.controller.BaseController;
import com.roborock.springboot.service.common.domain.AjaxResult;
import com.roborock.springboot.service.common.page.TableDataInfo;
import com.roborock.springboot.service.config.TestConfig;
import com.roborock.springboot.service.domain.UserTest;
import com.roborock.springboot.service.domain.vo.UserTestVo;
import com.roborock.springboot.service.service.SysUserService;
import com.roborock.springboot.service.service.UserTkService;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Resource
    private SysUserService sysUserService;

    @PreAuthorize("hasPermission('/roborock/user/getUser','sys:user:list')")
    @GetMapping(value = "/getUser")
    public AjaxResult getAll() {
        return AjaxResult.success(sysUserService.queryAll());
    }

    @PreAuthorize("hasPermission('/roborock/user','sys:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult get(@PathVariable(value = "id")String id) {
        return AjaxResult.success(userService.queryById(id));
    }

    @PreAuthorize("hasPermission('/roborock/user','sys:user:add')")
    @PostMapping
    public AjaxResult insert(@RequestBody UserTest userTest){
        return toAjax(userService.saveSelective(userTest));
    }

    @PreAuthorize("hasPermission('/roborock/user','sys:user:edit')")
    @PutMapping(value = "/{id}")
    public AjaxResult update(@PathVariable(value = "id")String id, @RequestBody UserTest userTest){
        userTest.setId(id);
        return toAjax(userService.updateSelective(userTest));
    }

    @PreAuthorize("hasPermission('/roborock/user','sys:user:remove')")
    @DeleteMapping(value = "/{id}")
    public AjaxResult delete(@PathVariable(value = "id")String id){
        return toAjax(userService.deleteTest(id));
    }

    @PreAuthorize("hasPermission('/roborock/user/list','sys:user:list')")
    @GetMapping(value = "/list")
    public TableDataInfo getPageList(UserTest userTest) {
        startPage();
        List<UserTest> list = userService.queryListByWhere(userTest);
        return getDataTable(list);
    }

    @PreAuthorize("hasPermission('/roborock/user/list/date','sys:user:list')")
    @GetMapping(value = "/list/date")
    public TableDataInfo getPageListByExample(@RequestBody UserTestVo vo) {
        startPage();
        List<UserTest> list = userService.queryListByExample(vo);
        return getDataTable(list);
    }

}
