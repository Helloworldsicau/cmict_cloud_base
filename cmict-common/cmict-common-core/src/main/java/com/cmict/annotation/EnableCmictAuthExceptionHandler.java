package com.cmict.annotation;

import com.cmict.configure.CmictAuthExceptionConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: lichenxin
 * @Date: 2021/2/16
 * 引入自定义认证相关异常类
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CmictAuthExceptionConfigure.class)
public @interface EnableCmictAuthExceptionHandler {
}
