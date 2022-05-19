package com.cmict.auth.component;

import com.cmict.entity.CmictAuthUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lichenxin
 * @Date: 2021/2/20
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
         //适配客户端模式获取token  进行令牌获取
        if(authentication.getPrincipal() instanceof  CmictAuthUser  ){
            CmictAuthUser securityUser = (CmictAuthUser) authentication.getPrincipal();
            Map<String, Object> info = new HashMap<>();
            //设置用户角色到JWT
            info.put("role", securityUser.getRoleId());
            info.put("pwdFlag",securityUser.getStatus());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
            return accessToken;
        }
        return accessToken;
    }
}
