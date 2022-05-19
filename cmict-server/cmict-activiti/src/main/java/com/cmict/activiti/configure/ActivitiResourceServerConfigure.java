package com.cmict.activiti.configure;

import com.cmict.handler.CmictAccessDeniedHandler;
import com.cmict.handler.CmictAuthExceptionEntryPoint;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @Author: lichenxin
 * @Date: 2021/2/16
 */
@Configuration
@EnableResourceServer
@DependsOn(value = "serverSetting")
public class ActivitiResourceServerConfigure extends ResourceServerConfigurerAdapter {
    @Autowired
    private CmictAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private CmictAuthExceptionEntryPoint exceptionEntryPoint;

    @Autowired
    private ServerSetting serverSetting;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(serverSetting.getAnonUrl(), ",");
        http.csrf().disable()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers(anonUrls).permitAll()
                .antMatchers("/**").authenticated();
    }
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(exceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }

}
