package com.roborock.springboot.service.domain.vo;

import com.roborock.springboot.service.common.annotation.Excel;
import lombok.Data;

/**
 * @Author BoHanZhang
 * @Date Create in 2021/2/22 20:22
 * @Description 用户输入实体
 */
@Data
public class SysUserVo {

    @Excel(name = "用户名")
    private String userName;

    @Excel(name = "账号")
    private String account;

}
