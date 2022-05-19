package com.cmict.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cmict.entity.system.UserRole;

import java.util.List;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
public interface IUserRoleService extends IService<UserRole> {
    /**
     * 删除角色用户管理关系
     *
     * @param roleIds 角色id数组
     */
    void deleteUserRolesByRoleId(String[] roleIds);

    /**
     * 删除角色用户管理关系
     *
     * @param userIds 用户id数组
     */
    void deleteUserRolesByUserId(String[] userIds);

    /**
     * 通过角色id查找对应的用户id
     *
     * @param roleIds 角色id
     * @return 用户id集
     */
    List<String> findUserIdsByRoleId(String[] roleIds);

    /**
     * 通过用户ID 查找对应的角色ID
     * @param userIds
     * @return
     */
    List<String> findRoleIdsByUserId(String[] userIds);
}
