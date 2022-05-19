package com.cmict.server.system.exception.enums;

import com.cmict.exception.ExceptionEnumInterface;
import io.swagger.models.auth.In;
import lombok.val;

import javax.security.auth.message.AuthException;

/**
 * @Author: lichenxin
 * @Date: 2021/5/19
 * 业务系统异常枚举 业务系统抛出的异常参超此类
 * 2000开始
 */
public enum BusinessEnum  implements ExceptionEnumInterface {
    DEVICE_NOT_EXIST(2000,"设备不存在"),
    MENU_NOT_EXIST(2010,"菜单不存在"),
    ROLE_ID_CANNOT_NULL(2020,"角色不能为空"),
    USER_NAME_EXIST(2030,"名称已存在"),
    USER_NOT_EXIST(2040,"用户不存在"),
    NO_PERMISSION_UPDATE(2050,"您无权修改别人的账号信息！"),
    PASSWORD_SAMPLE(2060,"密码过于简单,请输入由中英文组成的6-20位密码"),
    COMPONENT_NULL(2070,"component不能为空"),
    PATH_NULL(2080,"url不能为空"),
    ROLE_NOT_EXIST(2090,"角色不存在"),
    MENU_PARENT_NOT_EXIST(2100,"父级菜单不存在"),
    PASSWORD_ERROR(2110,"密码错误"),
    PASSWORD_IS_NULL(2120,"原密码不能为空"),
    MENU_EXIST(2130,"菜单已存在"),
    ROLE_ERROR(2140,"该角色不能被设置"),
    ;
    private Integer code;
    private String message;

     BusinessEnum(Integer code,String message){
    this.code = code;
    this.message = message;
    }

    public static BusinessEnum getErrorEnum(Integer code) {
        for (BusinessEnum error : BusinessEnum.values()) {
            if (error.getCode().intValue() == code) {
                return error;
            }
        }
        return null;
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
