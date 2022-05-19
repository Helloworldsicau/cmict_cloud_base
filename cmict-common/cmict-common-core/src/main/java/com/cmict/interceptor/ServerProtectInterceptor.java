package com.cmict.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.cmict.entity.CmictConstant;
import com.cmict.entity.CmictResponse;
import com.cmict.properties.AnonProperties;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang3.CharSet;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 * 服务拦截器 校验请求是否有getway请求头 防止绕过getway
 */
public class ServerProtectInterceptor  implements HandlerInterceptor {

    private AntPathMatcher pathMatcher = new AntPathMatcher();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
         //允许不通过网关访问
        String[] allowUri = StringUtils.splitByWholeSeparatorPreserveAllTokens(AnonProperties.url, ",");
        String requestURI = request.getRequestURI();
        boolean shouldAllow = false;
         if (allowUri != null && ArrayUtils.isNotEmpty(allowUri)) {
            for (String u : allowUri) {
                if (pathMatcher.match(u, requestURI)) {
                  return  true;
                }
            }
        }
        String token = request.getHeader(CmictConstant.ZUUL_TOKEN_HEADER);
        String zuulToken = new String(Base64Utils.encode(CmictConstant.ZUUL_TOKEN_VALUE.getBytes()));
        if (StringUtils.equals(zuulToken, token)) {
            return true;
        } else {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setCharacterEncoding(CharEncoding.UTF_8);
            response.getWriter().write(JSONObject.toJSONString(CmictResponse.failed("请通过网关获取资源")));
            return false;
        }
    }
}
