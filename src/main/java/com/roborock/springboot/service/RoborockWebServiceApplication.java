package com.roborock.springboot.service;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@NacosPropertySource(dataId = "roborock-web-service", autoRefreshed = true)
@MapperScan(basePackages = "com.roborock.springboot.service.mapper")
public class RoborockWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoborockWebServiceApplication.class, args);
    }
}
