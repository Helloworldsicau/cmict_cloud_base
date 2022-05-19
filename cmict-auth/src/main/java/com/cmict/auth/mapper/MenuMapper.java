package com.cmict.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmict.entity.system.Menu;

import java.util.List;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> findUserPermissions(String username);
}
