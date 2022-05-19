package com.cmict.exception;

/**
 * @Author: lichenxin
 * @Date: 2021/2/19
 * 自定义返回的 code 和 Message
 */
public interface CmictExceptionConstant {

    //认证相关10000 起始
    Integer AUTH_FAILED_CODE = 10000;
    String AUTH_FAILED_MESSAGE = "认证失败";


    Integer NOT_SUPPORTED_AUTH_TYPE_CODE = 10001;
    String NOT_SUPPORTED_AUTH_TYPE_MESSAGE = "不支持该认证类型";

    Integer REFRESH_TOKEN_INVALID_CODE = 10002;
    String REFRESH_TOKEN_INVALID_MESSAGE = "refresh token无效";

    Integer USER_LOCK_CODE = 10003;
    String USER_LOCK_MESSAGE = "用户已被锁定，请联系管理员";

    Integer USERNAME_PASSWORD_ERROR_CODE = 10004;
    String USERNAME_PASSWORD_ERROR_MESSAGE = "用户名或密码错误";

    Integer REFRESH_TOKEN_EXPIRE_CODE = 10005;
    String REFRESH_TOKEN_EXPIRE_MESSAGE = "刷新令牌已过期，请重新登录";

    Integer SCOPE_INVALID_CODE = 10006;
    String SCOPE_INVALID_MESSAGE = "不是有效的scope值";

    Integer REDIRECT_URI_ERROR = 10007;
    String REDIRECT_URI_ERROR_MESSAGE = "redirect_uri值不正确";

    Integer CLIENT_INVALID_CODE = 10008;
    String CLIENT_INVALID_MESSAGE = "client值不合法";

    Integer RESPONSE_TYPE_INVALID_CODE = 10009;
    String RESPONSE_TYPE_INVALID_MESSAGE = "不是合法的response_type值";

    Integer USERNAME_EXIST_CODE = 10010;
    String USERNAME_EXIST_CODE_MESSAGE = "用户名已存在";

    Integer USERNAME_TOO_LONG_CODE = 10011;
    String  USERNAME_TOO_LONG_MESSAGE = "用户名过长";

    Integer INFO_ILLEGAL_CODE = 10012;
    String  INFO_ILLEGAL_MESSAGE = "信息不合法";

    Integer OPERATION_NOT_SUPPORT_CODE = 10013;
    String  OPERATION_NOT_SUPPORT_MESSAGE = "不支持的操作";

    Integer ROLE_NAME_EXIST_CODE = 10014;
    String  ROLE_NAME_EXIST_MESSAGE = "角色名已存在";

    Integer MENU_NOT_EXIST_CODE = 10014;
    String  MENU_NOT_EXIST_MESSAGE = "菜单不存在";

    Integer USERNAME_CANNOT_EMPTY_CODE = 10015;
    String  USERNAME_CANNOT_EMPTY_MESSAGE = "用户名不能为空";


    //第三方对接相关  20000  开始
    String AUTHENTICATION_FAILED_MESSAGE = "小移云平台申请授权失败 code : ";
    String LOGIN_FAILED_MESSAGE = "小移云平台申请授权失败 code : ";

    String INVOKE_FAILED_MESSAGE = "大喇叭接口调用失败";
    String URI_PARSED_MESSAGE = "媒资文本地址URI解析异常";
    String UNSUPPORTED_CHARSET_MESSAGE = "不支持的编码类型";

    Integer DEVICE_NOT_EXIST_CODE = 20000;
    String  DEVICE_NOT_EXIST_MESSAGE = "设备不存在";


    //BaseExceptionHandler 中的错误 30000 开始
    Integer SYSTEM_UNKNOWN_CODE = 30000;
    String SYSTEM_UNKNOWN_MESSAGE = "系统未知异常";

    Integer NO_PERMISSION_CODE = 30001;
    String  NO_PERMISSION_MESSAGE = "没有权限进行此操作";
    //系统内部错误
    Integer INTERNAL_ERROR_CODE = 30002;

    //参数校验错误
    Integer PARAM_ILLEGAL_CODE = 30003;



}
