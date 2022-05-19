package com.cmict.auth.controller;

import com.cmict.auth.manager.UserManager;
import com.cmict.auth.service.SecurityService;
import com.cmict.auth.service.ValidateCodeService;
import com.cmict.entity.CmictResponse;
import com.cmict.entity.CurrentUser;
import com.cmict.entity.RegexpConstant;
import com.cmict.entity.system.SystemUser;
import com.cmict.exception.CmictAuthException;
import com.cmict.exception.CmictException;
import com.cmict.exception.ValidateCodeException;
import com.cmict.service.MasService;
import com.cmict.service.RedisService;
import com.cmict.untils.CmictUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.security.Principal;

import static com.cmict.auth.exception.ExceptionEnum.MOBILE_ILLEGAL;

/**
 * @Author: lichenxin
 * @Date: 2021/2/15
 */
@RestController
@Validated
public class SecurityController {
    @Autowired
    private ConsumerTokenServices consumerTokenServices;
    @Autowired
    private ValidateCodeService validateCodeService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SecurityService securityService;
    //ouath收到security保护 无法通过令牌获取资源
    @GetMapping("oauth/test")
    public String testOauth() {
        return "oauth";
    }

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        CurrentUser currentUser = CmictUtil.getCurrentUser();

        return principal;
    }

    @DeleteMapping("signout")
    public CmictResponse signout(HttpServletRequest request) throws CmictAuthException {
        String authorization = request.getHeader("Authorization");
        String token = StringUtils.replace(authorization, "Bearer ", "");
        CmictResponse febsResponse = new CmictResponse();
        if (!consumerTokenServices.revokeToken(token)) {
            throw new CmictException("退出登录失败");
        }
        return CmictResponse.SUCCESS;
    }

    @GetMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException, ValidateCodeException {
        validateCodeService.create(request, response);
    }

    /**
     * 发送手机验证码
     * @param mobile
     * @return
     */
    @GetMapping("mobile/code")
    public CmictResponse sendMobileCode(@NotBlank(message = "不能为空") String mobile){

        return securityService.sendCode(mobile);
    }
}
