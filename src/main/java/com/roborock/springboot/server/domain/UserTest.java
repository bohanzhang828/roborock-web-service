package com.roborock.springboot.server.domain;

import com.roborock.springboot.server.common.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Id;

@Data
public class UserTest extends BaseEntity {

    @Id
    private String id;

    private String name;

    private Integer age;

}
