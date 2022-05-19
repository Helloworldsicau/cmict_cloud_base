package com.cmict.auth.exception;

import com.cmict.exception.ExceptionEnumInterface;
import lombok.Data;

/**
 * @Author: lichenxin
 * @Date: 2021/5/20
 * 认证服务器异常
 * 错误码1000开始
 */
public enum ExceptionEnum  implements ExceptionEnumInterface {

    AUTH_FAILED(1010,"认证失败"),
    NOT_SUPPORTED_AUTH_TYPE(1020,"不支持该认证类型"),
    REFRESH_TOKEN_EXPIRE(1030,"刷新令牌已过期，请重新登录"),
    SCOPE_INVALID(1040,"不是有效的scope值"),
    REDIRECT_URI_ERROR(1050,"redirect_uri值不正确"),
    CLIENT_INVALID(1060,"client值不合法"),
    REFRESH_TOKEN_INVALID(1070,"refresh token无效"),
    USER_LOCK(1080,"用户已被锁定，请联系管理员"),
    USERNAME_PASSWORD_ERROR(1090,"用户名或密码错误"),
    PHONE_ERROR(1100,"手机号不存在"),
    MOBILE_ILLEGAL(1110,"手机号不合法"),
    PASSWORD_UN_UPDATE(1120,"初始密码未修改"),
    PASSWORD_UPDATE(1130,"初始已修改");
    ;

    private Integer code;

    private String message;

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
