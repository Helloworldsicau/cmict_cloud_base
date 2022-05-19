package com.cmict.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: lichenxin
 * @Date: 2021/4/2
 *
 * cmict.protect.allow.url ： 允许绕过微服务防护直接访问微服务的URL
 */

public class AnonProperties {

    public static final  String url = "/user";
}
