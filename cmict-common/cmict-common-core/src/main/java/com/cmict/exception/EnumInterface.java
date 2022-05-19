
package com.cmict.exception;
/**
 * @Author: lichenxin
 * @Date: 2020/12/16
 */
public interface EnumInterface {


    /**
     * 获取枚举中定义的值
     *
     * @return 枚举代码
     */
    Integer getCode();

    /**
     * 获取枚举中定义的备注信息
     *
     * @return 枚举代码描述
     */
    String getMessage();

}
