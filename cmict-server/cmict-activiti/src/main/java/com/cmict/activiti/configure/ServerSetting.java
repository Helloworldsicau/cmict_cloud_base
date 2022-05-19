package com.cmict.activiti.configure;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

/**
 * @author luffy
 * @date 2021/8/18
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:cmict-server-activiti.properties"})
@ConfigurationProperties(prefix = "cmict.server.activiti")
public class ServerSetting {

    private String anonUrl;
    private Swagger swagger;

    @Data
    @Validated
    public static class Swagger {
        private String basePackage;
        private String title;
        private String description;
        private String version;
        private String accessKeyId;
        private String author;
        private String url;
        private String email;
        private String license;
        private String licenseUrl;
        private String grantUrl;
        private String name;
        private String scope;
    }
}
