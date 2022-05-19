package com.cmict.handler;

import com.cmict.entity.CmictResponse;
import com.cmict.untils.CmictUtil;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cmict.exception.ErrorEnum.REJECT;

/**
 * @Author: lichenxin
 * @Date: 2021/2/16
 * 处理权限异常 403
 */
public class CmictAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        CmictResponse cmictResponse = new CmictResponse();
        CmictUtil.makeResponse(
                response, MediaType.APPLICATION_JSON_VALUE,
                HttpServletResponse.SC_FORBIDDEN, CmictResponse.failed(REJECT));
    }
}
