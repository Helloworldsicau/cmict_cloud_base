package com.cmict.annotation;

import com.cmict.configure.MasConfigure;
import com.cmict.configure.OAuth2FeignConfigure;
import com.cmict.service.MasService;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: lichenxin
 * @Date: 2021/5/27
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MasConfigure.class)
public @interface EnableMAS {
}
