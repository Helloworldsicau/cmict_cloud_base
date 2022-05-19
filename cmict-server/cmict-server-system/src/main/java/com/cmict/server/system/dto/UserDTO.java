package com.cmict.server.system.dto;

import com.cmict.annotation.IsMobile;
import com.cmict.annotation.group.usergroup.BatchGroup;
import com.cmict.annotation.group.usergroup.InsertGroup;
import com.cmict.annotation.group.usergroup.SingletonGroup;
import com.cmict.annotation.group.usergroup.UpdateGroup;
import com.cmict.entity.QueryRequest;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;

import static com.cmict.entity.RegexpConstant.*;

/**
 * @Author: lichenxin
 * @Date: 2021/5/20
 * 用户模块 DTO
 */

@Data
public class UserDTO  {


    /**
     * 用户ID
     */
    @NotNull(groups = {UpdateGroup.class},message = "{required}")
    private Long userId;

    @NotBlank(message = "{required}",groups = {BatchGroup.class})
    private String userIds;
    /**
     * 用户名
     */
    @NotBlank(message = "{required}",groups = {InsertGroup.class})
    @Pattern(regexp =USERNAME_REG, message ="{user.username.regexp}",groups ={InsertGroup.class})
    private String username;


    /**
     *  用户名集合 逗号分隔
     */
    @NotBlank(message = "{required}",groups = {BatchGroup.class})
    private String usernames;

    /**
     * 密码
     */
    @Pattern(regexp = PASSWORD_REG,groups = {UpdateGroup.class, SingletonGroup.class}
    ,message = "{user.password.regexp}")
    private String password;

    private String oldPassword;

    /**
     * 邮箱
     */
    @Size(max = 50, message = "{noMoreThan}",groups = {InsertGroup.class,UpdateGroup.class})
    @Email(message = "{email}",groups = {InsertGroup.class,UpdateGroup.class})
    private String email;

    /**
     * 联系电话
     */
    @IsMobile(message = "{mobile}",groups = {InsertGroup.class})
    private String mobile;

    /**
     * 状态 0锁定 1有效
     */
    @NotBlank(message = "{required}",groups = {InsertGroup.class})
    @Range(min = 0,max = 1,message = "{input.illegal}",groups = {InsertGroup.class,UpdateGroup.class})
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 最近访问时间
     */
    private Date lastLoginTime;

    /**
     * 性别 0男 1女 2 保密
     */
    @Range(min = 0,max = 2,message = "{input.illegal}",groups = {InsertGroup.class,UpdateGroup.class})
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 描述
     */
    @Size(max = 100, message = "{noMoreThan}",groups = {InsertGroup.class,UpdateGroup.class})
    private String description;

    /**
     * 开始时间
     */
    private String createTimeFrom;

    /**
     * 结束时间
     */
    private String createTimeTo;
    /**
     * 角色 ID
     */
    @NotBlank(message = "{required}",groups = {InsertGroup.class,UpdateGroup.class})
    private String roleId;

    /**
     * 分页查询条件
     */
    @Valid
    private QueryRequest queryRequest;
}
