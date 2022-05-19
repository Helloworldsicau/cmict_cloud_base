
package com.cmict.exception;
/**
 * @Author: lichenxin
 * @date 2020/12/16
 * 基础接口 自定义异常 实现此接口
 */
public interface ExceptionEnumInterface extends EnumInterface {
    /**
     *   获取错误码
     * @param code 自定义异常的code
     * @return  枚举类
     */
    static ExceptionEnumInterface getErrorEnum(int code) {
        return ErrorEnum.getErrorEnum(code);
    }
}
