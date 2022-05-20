package com.cmict.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableLettuceRedis {
}
