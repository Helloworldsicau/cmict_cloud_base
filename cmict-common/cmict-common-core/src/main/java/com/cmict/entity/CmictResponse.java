package com.cmict.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.cmict.exception.CmictException;
import com.cmict.exception.EnumInterface;
import com.cmict.exception.ErrorEnum;
import lombok.Data;

/**
 * @Author: lichenxin
 * @Date: 2021/2/15
 */
@Data
public class CmictResponse<T>{


    public static final CmictResponse<Object> SUCCESS = new CmictResponse<>(ErrorEnum.SUCCESS);
    public static final CmictResponse<Object> FAIL    = new CmictResponse<>(ErrorEnum.UNKNOWN_ERROR);
    @JSONField(ordinal = 1)
    private Integer  code;
    @JSONField(ordinal = 2)
    private String  message;
    @JSONField(ordinal = 3)
    private T  data;

    public CmictResponse(){}

    public CmictResponse(T data) {
        this(ErrorEnum.SUCCESS, data);
    }

    public CmictResponse(CmictException ex) {
        this(ex.getErrorCode().getCode(), ex.getMessage(), null);
    }

    public CmictResponse(EnumInterface error) {
        this(error.getCode(), error.getMessage(), null);
    }

    public CmictResponse(EnumInterface error, String message) {
        this(error.getCode(), message, null);
    }

    public CmictResponse(EnumInterface error, T data) {
        this(error.getCode(), error.getMessage(), data);
    }

    public CmictResponse( int code, String message, T data) {
        super();
        this.code = code;
        this.message   = message;
        this.data      = data;
    }

    public static <T> CmictResponse<T> ok(T data) {
        return new CmictResponse<>(data);
    }

    public static <T> CmictResponse<T> failed(String message) {
        return new CmictResponse<>(ErrorEnum.UNKNOWN_ERROR, message);
    }

    public static <T> CmictResponse<T> failed(EnumInterface errorCode) {
        return new CmictResponse<>( errorCode);
    }

}
