package com.cmict.exception;

public class CmictInvokeException extends RuntimeException{

    private final Integer code;

    public CmictInvokeException(String message, Integer code){
        super(message);
        this.code = code;
    }

    public Integer getCode(){
        return this.code;
    }
}
