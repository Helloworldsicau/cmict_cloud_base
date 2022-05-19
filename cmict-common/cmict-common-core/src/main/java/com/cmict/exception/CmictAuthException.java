package com.cmict.exception;

/**
 * @Author: lichenxin
 * @Date: 2021/2/15
 */
public class CmictAuthException  extends Exception {
    private static final long serialVersionUID = -6916154462432027437L;

    public CmictAuthException(String message){
        super(message);
    }
}
