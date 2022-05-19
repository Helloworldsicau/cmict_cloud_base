package com.cmict.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author: lichenxin
 * @Date: 2021/5/26
 */
public interface CmictUserDetailService  extends UserDetailsService {

    /**
     * 电话验证登录
     * @param mobile
     * @return
     */
    UserDetails loadUserByMobile(String mobile);
}
