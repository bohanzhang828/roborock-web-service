package com.roborock.springboot.service.controller;

import com.roborock.springboot.service.common.controller.BaseController;
import com.roborock.springboot.service.common.domain.AjaxResult;
import com.roborock.springboot.service.common.domain.LoginUser;
import com.roborock.springboot.service.common.page.TableDataInfo;
import com.roborock.springboot.service.config.TestConfig;
import com.roborock.springboot.service.domain.SysUser;
import com.roborock.springboot.service.domain.UserTest;
import com.roborock.springboot.service.domain.vo.SysUserVo;
import com.roborock.springboot.service.domain.vo.UserTestVo;
import com.roborock.springboot.service.service.SysUserService;
import com.roborock.springboot.service.service.UserTkService;
import com.roborock.springboot.service.util.ServletUtils;
import com.roborock.springboot.service.util.jwt.JwtUtil;
import com.roborock.springboot.service.util.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private JwtUtil jwtUtil;

    @PreAuthorize("hasPermission('/roborock/user/getUser','sys:user:list')")
    @GetMapping(value = "/getUser")
    public AjaxResult getAll() {
        return AjaxResult.success(sysUserService.queryAll());
    }

    @PreAuthorize("hasPermission('/roborock/user','sys:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult get(@PathVariable(value = "id") String id) {
        return AjaxResult.success(userService.queryById(id));
    }

    @PreAuthorize("hasPermission('/roborock/user','sys:user:add')")
    @PostMapping
    public AjaxResult insert(@RequestBody UserTest userTest) {
        return toAjax(userService.saveSelective(userTest));
    }

    @PreAuthorize("hasPermission('/roborock/user','sys:user:edit')")
    @PutMapping(value = "/{id}")
    public AjaxResult update(@PathVariable(value = "id") String id, @RequestBody UserTest userTest) {
        userTest.setId(id);
        return toAjax(userService.updateSelective(userTest));
    }

    @PreAuthorize("hasPermission('/roborock/user','sys:user:remove')")
    @DeleteMapping(value = "/{id}")
    public AjaxResult delete(@PathVariable(value = "id") String id) {
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

    //excel导入
    @PreAuthorize("hasPermission('/roborock/user/importData','system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SysUserVo> util = new ExcelUtil<SysUserVo>(SysUserVo.class);
        List<SysUserVo> userList = util.importExcel(file.getInputStream());
        LoginUser loginUser = jwtUtil.getLoginUser(ServletUtils.getRequest());
        String userId = loginUser.getUser().getId();
        String message = sysUserService.importUser(userList, updateSupport, userId);
        return AjaxResult.success(message);
    }

}
