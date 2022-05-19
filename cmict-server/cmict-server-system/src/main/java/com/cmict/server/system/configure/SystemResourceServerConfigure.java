package com.cmict.server.system.configure;

import com.cmict.handler.CmictAccessDeniedHandler;
import com.cmict.handler.CmictAuthExceptionEntryPoint;
import com.cmict.server.system.properties.ServerSystemProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
public class SystemResourceServerConfigure extends ResourceServerConfigurerAdapter {
    @Autowired
    private CmictAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private CmictAuthExceptionEntryPoint exceptionEntryPoint;

    @Autowired
    private ServerSystemProperties properties;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(properties.getAnonUrl(), ",");
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
