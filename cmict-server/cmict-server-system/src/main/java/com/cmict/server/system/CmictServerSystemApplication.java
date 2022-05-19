package com.cmict.server.system;

import com.cmict.annotation.CmictCloudApplication;
import com.cmict.annotation.EnableMAS;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CmictCloudApplication
@EnableTransactionManagement
@SpringCloudApplication
@EnableMAS
@MapperScan("com.cmict.server.system.mapper")
public class CmictServerSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmictServerSystemApplication.class, args);
    }

}
