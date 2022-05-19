package com.cmict.auth.service;

import com.cmict.entity.CmictResponse;

/**
 * @Author: lichenxin
 * @Date: 2021/5/27
 */
public interface SecurityService {

    public CmictResponse sendCode(String mobile);

}
