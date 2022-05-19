package com.cmict.gateway.exception;

import com.cmict.exception.ExceptionEnumInterface;

/**
 * @Author: lichenxin
 * @Date: 2021/5/20
 * 网关异常 3000起始
 */
public enum ExceptionEnum implements ExceptionEnumInterface {
        ACCESS_DENIED(3000,"该URI不允许外部访问"),
        NO_PERMISSION(3010,"该资源没有对应的权限"),
        TOKEN_ERROR(3020,"TOKEN 错误"),

    ;

    private Integer code;

    private String  message;

    ExceptionEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }
    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
