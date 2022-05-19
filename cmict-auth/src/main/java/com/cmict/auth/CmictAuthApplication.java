package com.cmict.auth;

import com.cmict.annotation.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@CmictCloudApplication
@EnableMAS
@MapperScan("com.cmict.auth.mapper")
public class CmictAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmictAuthApplication.class, args);
    }

}
