package com.cmict.auth.configure;

import com.cmict.auth.properties.AuthProperties;
import com.cmict.handler.CmictAccessDeniedHandler;
import com.cmict.handler.CmictAuthExceptionEntryPoint;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @Author: lichenxin
 * @Date: 2021/2/15
 * oauth2 资源服务器配置
 * 优先级3
 */
@EnableResourceServer
@Configuration
public class CmictResourceServer extends ResourceServerConfigurerAdapter {
    @Autowired
    private CmictAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private CmictAuthExceptionEntryPoint exceptionEntryPoint;

    @Autowired
    private AuthProperties properties;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(properties.getAnonUrl(), ",");
        http.csrf().disable()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers(anonUrls).permitAll()
                .antMatchers("/**").authenticated()
                .and().httpBasic();
    }
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(exceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }
}
