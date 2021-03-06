package com.roborock.springboot.service.domain;

import lombok.Data;

import javax.persistence.Id;

@Data
public class SysMenu {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.id
     *
     * @mbg.generated Thu Feb 18 10:33:43 CST 2021
     */
    @Id
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.permission_code
     *
     * @mbg.generated Thu Feb 18 10:33:43 CST 2021
     */
    private String permissionCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.permission_name
     *
     * @mbg.generated Thu Feb 18 10:33:43 CST 2021
     */
    private String permissionName;

}