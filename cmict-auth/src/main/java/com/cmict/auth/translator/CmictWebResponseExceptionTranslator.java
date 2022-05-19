package com.cmict.auth.translator;

import com.cmict.entity.CmictResponse;
import com.cmict.exception.CmictException;
import com.cmict.exception.CmictExceptionConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

import static com.cmict.auth.exception.ExceptionEnum.*;

/**
 * @Author: lichenxin
 * @Date: 2021/2/16
 * 覆盖默认的认证异常信息  如Bad credentials
 * 需要在 AuthorizationServerConfigure配置生效
 */
@Slf4j
@Component
public class CmictWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        ResponseEntity.BodyBuilder status = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        log.info(e.getMessage());
        if (e instanceof UnsupportedGrantTypeException) {
            return status.body(CmictResponse.failed(NOT_SUPPORTED_AUTH_TYPE));
        }
        if (e instanceof InvalidTokenException
                && StringUtils.containsIgnoreCase(e.getMessage(), "Invalid refresh token (expired)")) {
            return status.body(CmictResponse.failed(REFRESH_TOKEN_EXPIRE));
        }
        if (e instanceof InvalidScopeException) {
            return status.body(CmictResponse.failed(SCOPE_INVALID));
        }
        if (e instanceof RedirectMismatchException) {
            return status.body(CmictResponse.failed(REDIRECT_URI_ERROR));
        }
        if (e instanceof BadClientCredentialsException) {
            return status.body(CmictResponse.failed(CLIENT_INVALID));
        }

        if (e instanceof InvalidGrantException) {
            if (StringUtils.containsIgnoreCase(e.getMessage(), "Invalid refresh token")) {
                return status.body(CmictResponse.failed(REFRESH_TOKEN_INVALID));
            }
            if (StringUtils.containsIgnoreCase(e.getMessage(), "locked")) {
                return status.body(CmictResponse.failed(USER_LOCK));
            }
            if (StringUtils.containsIgnoreCase(e.getMessage(), "Invalid authorization code")) {
                String code = StringUtils.substringAfterLast(e.getMessage(), ": ");
               String message = "授权码" + code + "不合法";
                return status.body(CmictResponse.failed(message));
            }
            return status.body(CmictResponse.failed(USERNAME_PASSWORD_ERROR));
        }
        if(StringUtils.isNotEmpty(e.getMessage())){
            return status.body(CmictResponse.failed(e.getMessage()));
        }
        return status.body(CmictResponse.failed(AUTH_FAILED));
    }
}
