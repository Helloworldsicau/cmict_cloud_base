package com.cmict.auth.configure.mobile;

import com.cmict.auth.service.CmictUserDetailService;
import com.cmict.entity.CmictConstant;
import com.cmict.exception.CmictException;
import com.cmict.exception.ValidateCodeException;
import com.cmict.service.RedisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.cmict.auth.exception.ExceptionEnum.PHONE_ERROR;

/**
 * @Author: lichenxin
 * @Date: 2021/5/26
 * 增加授权方式
 */
@Component
public class MobileAuthenticationProvider  implements AuthenticationProvider {

    @Autowired
    private CmictUserDetailService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisService redisService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken authenticationToken = (MobileAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        String mobileCode = (String) authenticationToken.getCredentials();
        String key = authenticationToken.getKey();
        String code = authenticationToken.getCode();
        UserDetails user = userDetailsService.loadUserByMobile(mobile);
        if (user == null) {
            throw new CmictException(PHONE_ERROR);
        }
        //进行验证
        check(key,code,mobile,mobileCode);
        MobileAuthenticationToken authenticationResult = new MobileAuthenticationToken(user, mobileCode, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void check(String key, String code,String mobile,String mobileCode)  {
        Object codeInRedis = redisService.get(CmictConstant.CODE_PREFIX + key);
        if (StringUtils.isBlank(code)) {
            throw new CmictException("请输入验证码");
        }
        if (codeInRedis == null) {
            throw new CmictException("验证码已过期");
        }
        if (!StringUtils.equalsIgnoreCase(code, String.valueOf(codeInRedis))) {
            throw new CmictException("验证码不正确");
        }
        Object redisMobileCode = redisService.get(CmictConstant.MOBILE_CODE_PREFIX + mobile);
        if(StringUtils.isBlank(mobileCode)){
            throw  new CmictException("请输出手机验证码");
        }
        if (redisMobileCode == null) {
        throw new CmictException("手机验证码已过期");
    }
        if (!StringUtils.equalsIgnoreCase(mobileCode, String.valueOf(redisMobileCode))) {
        throw new CmictException("手机验证码不正确");
    }
    }
}
