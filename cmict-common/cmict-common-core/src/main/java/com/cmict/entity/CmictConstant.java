package com.cmict.entity;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
public class CmictConstant {
    /**
     * 排序规则：降序
     */
    public static final   String ORDER_DESC = "descending";

    public static final String ORDER_ASC = "ascending";
    /**
     * Zuul请求头TOKEN名称（不要有空格）
     */
    public static final String ZUUL_TOKEN_HEADER = "ZuulToken";
    /**
     * Zuul请求头TOKEN值
     */
    public static final String ZUUL_TOKEN_VALUE = "cmict:zuul:123456";

    /**
     * gif类型
     */
    public static final String GIF = "gif";
    /**
     * png类型
     */
    public static final String PNG = "png";

    /**
     * 验证码 key前缀
     */
    public static final String CODE_PREFIX = "cmict.captcha.";

    /**
     * 手机验证码 key前缀
     */
    public static final String MOBILE_CODE_PREFIX = "cmict.mobile.";

    /**
     * 手机验证码重发 key前缀
     */
    public static final String MOBILE_CODE_REPEAT_PREFIX = "cmict.mobile.repeat";

    /**
     * 调用大喇叭接口请求头token
     */
    public static final String HORN_TOKEN_HEADER = "accessToken";

    /**
     * redis存储大喇叭token键值
     */
    public static final String HORN_TOKEN_KEY = "hornAccessToken";

    /**
     * token有效期(单位：秒)
     */
    public static final Long TOKEN_INVALID_TIME = 7200L;

    /**
     * 大喇叭文本媒资字符编码
     */
    public static final String CHARSET_GBK = "GBK";

}
