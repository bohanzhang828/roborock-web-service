package com.roborock.springboot.service.service.impl;

import com.roborock.springboot.service.common.exception.CustomException;
import com.roborock.springboot.service.controller.CommonController;
import com.roborock.springboot.service.domain.SysUser;
import com.roborock.springboot.service.domain.vo.SysUserVo;
import com.roborock.springboot.service.mapper.SysUserMapper;
import com.roborock.springboot.service.service.SysUserService;
import com.roborock.springboot.service.util.SecurityUtils;
import com.roborock.springboot.service.util.StringUtils;
import com.roborock.springboot.service.util.uuid.IdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl extends AbstractService<SysUser> implements SysUserService {

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Resource
    private SysUserMapper sysUserMapper;

    public SysUser selectByUserName(String userName) {
        SysUser sysUser = sysUserMapper.selectByUserName(userName);
        return sysUser;
    }

    @Override
    public String importUser(List<SysUserVo> userList, boolean updateSupport, String userId) {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new CustomException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = "123456";
        SysUser user = new SysUser();
        for (SysUserVo userVo : userList)
        {
            try
            {
                // 验证是否存在这个用户
                SysUser u = sysUserMapper.selectByUserName(userVo.getAccount());
                if (StringUtils.isNull(u))
                {
                    user.setId(IdUtils.fastSimpleUUID());
                    user.setAccount(userVo.getAccount());
                    user.setUserName(userVo.getUserName());
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateUser(userId);
                    user.setCreateTime(new Date());
                    user.setAccountNonLocked(true);
                    user.setEnabled(true);
                    this.save(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + userVo.getAccount() + " 导入成功");
                }
                else if (updateSupport)
                {
                    user.setId(u.getId());
                    user.setUpdateUser(userId);
                    user.setUpdateTime(new Date());
                    user.setAccount(userVo.getAccount());
                    user.setUserName(userVo.getUserName());
                    this.updateSelective(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + userVo.getAccount() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + userVo.getAccount() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getAccount() + " 导入失败";
                failureMsg.append(msg);
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
