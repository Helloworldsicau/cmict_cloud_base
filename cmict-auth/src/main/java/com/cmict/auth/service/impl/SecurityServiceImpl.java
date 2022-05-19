package com.cmict.auth.service.impl;

import com.cmict.auth.manager.UserManager;
import com.cmict.auth.service.SecurityService;
import com.cmict.entity.CmictConstant;
import com.cmict.entity.CmictResponse;
import com.cmict.entity.RegexpConstant;
import com.cmict.exception.CmictException;
import com.cmict.service.MasService;
import com.cmict.service.RedisService;
import com.cmict.untils.CmictUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.cmict.auth.exception.ExceptionEnum.MOBILE_ILLEGAL;
import static com.cmict.auth.exception.ExceptionEnum.PHONE_ERROR;

/**
 * @Author: lichenxin
 * @Date: 2021/5/27
 */
@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private UserManager userManager;
    @Autowired
    private RedisService redisService;
    @Autowired
    private MasService masService;
    @Override
    public CmictResponse sendCode(String mobile) {
        String regex = RegexpConstant.MOBILE_REG;
        if(!CmictUtil.match(regex,mobile)){
            throw new CmictException(MOBILE_ILLEGAL);
        }

        if( userManager.findByMobile(mobile) ==null){
            throw new CmictException(PHONE_ERROR);
        }
        Long expire = redisService.getExpire(CmictConstant.MOBILE_CODE_REPEAT_PREFIX + mobile);
        if(expire < 0L){
            redisService.set(CmictConstant.MOBILE_CODE_REPEAT_PREFIX + mobile,1,60L);
        }else{
            throw new CmictException("请"+expire+"秒后重试");
        }
        String code = CmictUtil.randomCode();
        //存储验证码  5分钟有效期
        redisService.set(CmictConstant.MOBILE_CODE_PREFIX + mobile,code,300L);


        //无账号
       // masService.send(mobile,code);

        return CmictResponse.ok(code);
    }
}
