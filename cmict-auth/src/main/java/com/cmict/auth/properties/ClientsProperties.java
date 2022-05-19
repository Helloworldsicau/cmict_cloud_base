package com.cmict.auth.properties;

import lombok.Data;

/**
 * @Author: lichenxin
 * @Date: 2021/2/16
 * 客户端信息配置
 */
@Data
public class ClientsProperties {
    private String client;
    private String secret;
    private String grantType = "password,authorization_code,refresh_token";
    private String scope = "all";
    private Integer  accessTokenValiditySeconds = 86400 ;
}
