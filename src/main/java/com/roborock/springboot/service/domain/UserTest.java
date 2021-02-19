package com.roborock.springboot.service.domain;

import com.roborock.springboot.service.common.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Id;

@Data
public class UserTest extends BaseEntity {

    @Id
    private String id;

    private String name;

    private Integer age;

}
