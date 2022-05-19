package com.cmict.auth.configure;

import com.cmict.auth.configure.mobile.MobileAuthenticationProvider;
import com.cmict.auth.filter.ValidateCodeFilter;
import com.cmict.auth.service.CmictUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: lichenxin
 * @Date: 2021/2/15
 * spring security 安全配置
 * CmictResourceServer  默认优先级为100  ResourceServerConfigurerAdapter为3
 * 所以All  req 都会被资源服务器拦截 所以调整安全认证的优先级为2
 * 以/oauth/开头的请求由SecurityConfigure过滤器链处理，剩下的其他请求由ResourceServerConfigure过滤器链处理。
 */
@Order(2)
@EnableWebSecurity
public class CmictSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Autowired
    private CmictUserDetailService userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ValidateCodeFilter validateCodeFilter;
    @Autowired
    private MobileAuthenticationProvider mobileAuthenticationProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .requestMatchers()
                //对/oauth/开头的请求生效
                .antMatchers("/oauth/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .antMatchers("/actuator/**").permitAll()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
        auth.authenticationProvider(mobileAuthenticationProvider);
    }
}
