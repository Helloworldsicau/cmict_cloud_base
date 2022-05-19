package com.cmict.exception;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
public class ValidateCodeException extends Exception {
    private static final long serialVersionUID = 7514854456967620043L;

    public ValidateCodeException(String message){
        super(message);
    }
}
