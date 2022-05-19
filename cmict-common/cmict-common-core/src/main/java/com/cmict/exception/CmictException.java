package com.cmict.exception;

import lombok.Data;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 * 系统通用业务异常
 */
@Data
public class CmictException  extends RuntimeException {

    private ExceptionEnumInterface exception;

    public CmictException(ExceptionEnumInterface exception) {
        super(exception.getMessage());
        this.exception = exception;
    }

    public CmictException(ExceptionEnumInterface exception, String message) {
        super(message);
        this.exception = exception;
    }

    public CmictException(String message) {
        super(message);
        this.exception = ErrorEnum.UNKNOWN_ERROR;
    }

    public CmictException(String message, Throwable cause) {
        super(message, cause);
        this.exception = ErrorEnum.UNKNOWN_ERROR;
    }

    public EnumInterface getErrorCode() {
        return exception;
    }
}
