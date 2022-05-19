package com.cmict.server.system.log;

import java.lang.annotation.*;

/**
 *
 * @author chentianshu
 * @since 2021/3/24
 * 记录事件处理日志，应用在controller层
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventLogger {

    String operation();

    /**
     *获取事件编号，如param1表示获取第一个参数，param1.number表示获取第一个参数中的number字段
     */
    String targetParam() default "";
}
