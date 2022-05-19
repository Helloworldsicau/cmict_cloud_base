package com.cmict.auth.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cmict.auth.mapper.MenuMapper;
import com.cmict.auth.mapper.UserMapper;
import com.cmict.entity.system.Menu;
import com.cmict.entity.system.SystemUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
@Service
public class UserManager {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    public SystemUser findByName(String username) {
        return userMapper.findByName(username);
    }

    public SystemUser findByMobile(String mobile){
        LambdaQueryWrapper<SystemUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SystemUser::getMobile,mobile);
       return userMapper.selectOne(lambdaQueryWrapper);

    }


    public String findUserPermissions(String username) {
        List<Menu> userPermissions = menuMapper.findUserPermissions(username);

        List<String> perms = new ArrayList<>();
        for (Menu m: userPermissions){
            perms.add(m.getPerms());
        }
        return StringUtils.join(perms, ",");
    }
}
