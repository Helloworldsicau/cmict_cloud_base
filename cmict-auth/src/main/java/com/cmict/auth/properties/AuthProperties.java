package com.cmict.auth.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: lichenxin
 * @Date: 2021/2/16
 *认证配置
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:auth.properties"})
@ConfigurationProperties(prefix = "cmict.auth")
public class AuthProperties {
    private ClientsProperties[] clients = {};
    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 7;

    //验证码配置类
    private ValidateCodeProperties code = new ValidateCodeProperties();

    // 免认证路径
    private String anonUrl;
}
