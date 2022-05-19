package com.cmict.handler;

import com.cmict.entity.CmictResponse;
import com.cmict.exception.CmictAuthException;
import com.cmict.exception.CmictException;
import com.cmict.exception.CmictInvokeException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;

import static com.cmict.exception.CmictExceptionConstant.*;
import static com.cmict.exception.ErrorEnum.*;

/**
 * @Author: lichenxin
 * @Date: 2021/2/16
 * 通用异常类
 */
@Slf4j
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CmictResponse handleException(Exception e) {
        log.error(e.getMessage(), e);
        return CmictResponse.FAIL;
    }

    @ExceptionHandler(value = CmictAuthException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CmictResponse handleAuthException(CmictAuthException e) {
        log.error("认证失败", e);
        return new CmictResponse(UNAUTHORIZED);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CmictResponse handleAccessDeniedException() {
        return new CmictResponse(NONSUPPORT);
    }

    @ExceptionHandler(value = CmictException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CmictResponse handleException(CmictException e) {
        log.error("系统内部错误", e);
        if(e.getErrorCode().getCode() !=null){
            return new CmictResponse(e);
        }
        return new CmictResponse(UNKNOWN_ERROR);
    }

    @ExceptionHandler(value = CmictInvokeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CmictResponse handleException(CmictInvokeException e) {
        log.error("调用第三方接口出错", e);
        return new CmictResponse(API_ERROR);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CmictResponse handleInvalidFormatException(InvalidFormatException e) {
        return new CmictResponse(INVALID_PARAMS);
    }

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CmictResponse handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), ".");
            message.append(pathArr[1]).append(violation.getMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return new CmictResponse(INVALID_PARAMS,message.toString());
    }

    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CmictResponse handleBindException(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return new CmictResponse(INVALID_PARAMS,message.toString());
    }

    /**
     * 对实体中有Object参数进行非空校验
     *
     * @param e BindException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CmictResponse handleNotValidException(MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return new CmictResponse(INVALID_PARAMS,message.toString());
    }

    /**
     * 类型转换异常
     * @param e
     * @return
     */
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CmictResponse numberFormatException(NumberFormatException e) {
        return new CmictResponse(NUMBER_FORMAT_ERROR);
    }
}
