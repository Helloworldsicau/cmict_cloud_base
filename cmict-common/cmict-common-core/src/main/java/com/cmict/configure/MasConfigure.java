package com.cmict.configure;

import com.cmict.interceptor.ServerProtectInterceptor;
import com.cmict.service.MasService;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Author: lichenxin
 * @Date: 2021/5/27
 */
public class MasConfigure {


    @Bean
    public MasService masService() {
        return new MasService();
    }
}
