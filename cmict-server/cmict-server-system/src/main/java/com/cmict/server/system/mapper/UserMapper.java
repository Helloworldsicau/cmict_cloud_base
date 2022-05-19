package com.cmict.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmict.entity.system.SystemUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
public interface UserMapper extends BaseMapper<SystemUser> {
    /**
     * 查找用户详细信息
     *
     * @param page 分页对象
     * @param user 用户对象，用于传递查询条件
     * @return Ipage
     */
    <T>IPage<SystemUser> findUserDetailPage(Page<T> page, @Param("user") SystemUser user);


    /**
     * 查找用户详细信息
     *
     * @param user 用户对象，用于传递查询条件
     * @return List<User>
     */
    List<SystemUser> findUserDetail(@Param("user") SystemUser user);
}
