package com.cmict.activiti;

import com.cmict.annotation.CmictCloudApplication;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@CmictCloudApplication
@EnableFeignClients
@MapperScan("com.cmict.activiti.mapper")
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }

}
