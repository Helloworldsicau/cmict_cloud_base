package com.cmict.server.system.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: lichenxin
 * @Date: 2021/2/19
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:cmict-server-system.properties"})
@ConfigurationProperties(prefix = "cmict.server.system")
public class ServerSystemProperties {

    /**
     * 免认证 URI，多个值的话以逗号分隔
     */
    private String anonUrl;

    private SwaggerProperties swagger = new SwaggerProperties();

}
