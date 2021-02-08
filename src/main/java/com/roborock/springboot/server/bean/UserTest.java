package com.roborock.springboot.server.bean;

import lombok.Data;

import javax.persistence.Id;

@Data
public class UserTest extends BaseEntity {

    @Id
    private String id;

    private String name;

    private Integer age;

}
