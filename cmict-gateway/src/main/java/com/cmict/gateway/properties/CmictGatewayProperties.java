package com.cmict.gateway.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:cmict-gateway.properties"})
@ConfigurationProperties(prefix = "cmict.gateway")
public class CmictGatewayProperties {
    /**
     * 禁止外部访问的 URI，多个值的话以逗号分隔
     */
    private String forbidRequestUri;

    /**
     * 用户获取令牌和验证码不进行资源限制
     */
    private String allowRequestUri;
}
