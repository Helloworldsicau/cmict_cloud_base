package com.cmict.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmict.entity.system.SystemUser;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
public interface UserMapper extends BaseMapper<SystemUser> {
    SystemUser findByName(String username);
}
