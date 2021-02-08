package com.roborock.springboot.server;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@NacosPropertySource(dataId = "roborock-web-service", autoRefreshed = true)
public class RoborockWebServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoborockWebServerApplication.class, args);
    }
}
