package com.cmict.server.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cmict.entity.CmictResponse;
import com.cmict.entity.QueryRequest;
import com.cmict.entity.system.SystemUser;
import com.cmict.server.system.dto.UserDTO;

import java.util.List;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
public interface IUserService extends IService<SystemUser> {

    /**
     * 通过用户名查找用户
     *
     * @param username 用户名
     * @return 用户
     */
    SystemUser findByName(String username);

    /**
     * 查找用户详细信息
     *
     * @param request request
     * @param user    用户对象，用于传递查询条件
     * @return IPage
     */
    IPage<SystemUser> findUserDetailList(SystemUser user, QueryRequest request);

    /**
     * 通过用户名查找用户详细信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    SystemUser findUserDetail(String username);

    /**
     * 更新用户登录时间
     *
     * @param username username
     */
    void updateLoginTime(String username);

    /**
     * 新增用户
     *
     * @param user user
     */
    CmictResponse createUser(SystemUser user);

    /**
     * 修改用户
     *
     * @param user user
     */
    CmictResponse updateUser(SystemUser user);

    /**
     * 删除用户
     *
     * @param userIds 用户 id数组
     */
    CmictResponse deleteUsers(String[] userIds);

    /**
     * 更新个人信息
     *
     * @param user 个人信息
     */
    CmictResponse updateProfile(SystemUser user) ;


    /**查找用户id对应的领导**/

   List<SystemUser> findUserLeaders(long deptId);
    /**
     * 更新用户头像
     *
     * @param avatar 用户头像
     */
    void updateAvatar(String avatar);

    /**
     * 更新用户密码
     *
     * @param userDTO
     */
    void updatePassword(UserDTO userDTO);

    /**
     * 重置密码
     *
     * @param usernames 用户集合
     */
    void resetPassword(String[] usernames,String [] ids);

}
