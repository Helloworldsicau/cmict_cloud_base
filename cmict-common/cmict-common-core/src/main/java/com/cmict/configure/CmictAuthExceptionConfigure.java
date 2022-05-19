package com.cmict.configure;

import com.cmict.handler.CmictAccessDeniedHandler;
import com.cmict.handler.CmictAuthExceptionEntryPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @Author: lichenxin
 * @Date: 2021/2/16
 */
public class CmictAuthExceptionConfigure {

    @Bean
    @ConditionalOnMissingBean(name = "accessDeniedHandler")
    public CmictAccessDeniedHandler accessDeniedHandler() {
        return new CmictAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "authenticationEntryPoint")
    public CmictAuthExceptionEntryPoint authenticationEntryPoint() {
        return new CmictAuthExceptionEntryPoint();
    }
}
