package com.cmict.handler;

import com.alibaba.fastjson.JSONObject;
import com.cmict.entity.CmictResponse;
import com.cmict.untils.CmictUtil;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: lichenxin
 * @Date: 2021/2/16
 * 处理所有资源服务器的异常  401
 */
public class CmictAuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        CmictResponse cmictResponse = new CmictResponse();

        CmictUtil.makeResponse(
                response, MediaType.APPLICATION_JSON_VALUE,
                HttpServletResponse.SC_UNAUTHORIZED, CmictResponse.failed("TOKEN 无效")
        );
    }
}
