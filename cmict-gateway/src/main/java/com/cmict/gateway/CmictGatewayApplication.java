package com.cmict.gateway;

import com.cmict.annotation.EnableLettuceRedis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
@SpringBootApplication
@EnableLettuceRedis
public class CmictGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmictGatewayApplication.class, args);
    }
}
