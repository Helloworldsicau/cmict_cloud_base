package com.cmict.entity;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
public class RegexpConstant {

    public static final String MOBILE_REG = "[1]\\d{10}";
    //密码校验
    public static final String PASSWORD_REG= "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
    //用户名
    public static final String USERNAME_REG= "[\\u4e00-\\u9fa5_a-zA-Z0-9_]{1,10}";

    //大于1的正整数
    public static final String INTEGER_CHECK = "^[1-9]\\d*$";

    public static final String ROLE_NAME_REG = "[\\u4e00-\\u9fa5_a-zA-Z0-9_]{1,10}";

    public static final String ROLE_REMARK_REG = "[\\u4e00-\\u9fa5_a-zA-Z0-9_]{1,50}";
}
