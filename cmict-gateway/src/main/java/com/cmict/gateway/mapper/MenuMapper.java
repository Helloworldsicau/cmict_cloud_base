package com.cmict.gateway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmict.entity.system.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lichenxin
 * @Date: 2021/2/21
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> findUserRolePath();
}
