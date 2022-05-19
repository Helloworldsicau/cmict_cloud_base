
package com.cmict.exception;
/**
 * @Author: lichenxin
 * @Date: 2020/12/16
 */
public enum ErrorEnum implements ExceptionEnumInterface {
    /**
    * 调用成功
     */
    SUCCESS(0, "成功"),

    UNKNOWN_ERROR(1,  "未知异常"),
    /**
     * 参数异常
     */
    INVALID_PARAMS(2,  "参数错误"),
    /**
     *系统不支持的操作
     */
    NONSUPPORT(3,  "不支持的操作"),
    /**
     * 开启加密，用户未加密访问或异常访问
     */
    REJECT(4,  "没有权限访问该资源"),

    /**
     * 用户未认证
     */
    UNAUTHORIZED(5,  "认证失败"),

    /**
     * 调用第三方接口出错
     */
    API_ERROR(6,"调用第三方接口出错"),

    /**
     * 调用第三方接口出错
     */
    NUMBER_FORMAT_ERROR(7,"类型不正确"),

    /**
     * 大喇叭相关错误
     */
    DEVICE_NOT_FIND(100, "未绑定设备"),

    HORN_NOT_AVAILABLE(101, "大喇叭设备占线中"),

    CALL_INTERRUPTED(102, "喊话断线,请重试"),

    SAVE_AUDIO_FAILED(103, "服务器异常,可能影响通信质量");

    private Integer code;
    private String message;

    private ErrorEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorEnum getErrorEnum(int code) {
        for (ErrorEnum error : ErrorEnum.values()) {
            if (error.getCode() == code) {
                return error;
            }
        }
        throw new IllegalArgumentException("Param code mismatch.");
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