package com.cmict.server.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cmict.annotation.group.usergroup.InsertGroup;
import com.cmict.annotation.group.usergroup.QueryGroup;
import com.cmict.annotation.group.usergroup.UpdateGroup;
import com.cmict.entity.system.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

import static com.cmict.entity.RegexpConstant.USERNAME_REG;

/**
 * @Author: lichenxin
 * @Date: 2021/5/28
 */
@Data
public class MenuDTO {

    @NotNull(message = "{required}",groups = {UpdateGroup.class})
    private Long menuId;

    /**
     * 上级菜单ID
     */
    @NotNull(message = "{required}",groups = {InsertGroup.class})
    private Long parentId;

    /**
     * 菜单/按钮名称
     */
    @NotBlank(message = "{required}",groups = {InsertGroup.class,UpdateGroup.class})
    @Size(max = 10, message = "{noMoreThan}",groups = {InsertGroup.class,UpdateGroup.class})
    @Pattern(regexp = USERNAME_REG, message ="{user.username.regexp}",groups = {InsertGroup.class,UpdateGroup.class})
    private String menuName;

    /**
     * 菜单URL
     */
    @Size(max = 100, message = "{noMoreThan}",groups = {InsertGroup.class,UpdateGroup.class})
    private String path;

    /**
     * 路由重定向
     */
    private String redirect;

    /**
     * 对应 Vue组件
     */
    @Size(max = 100, message = "{noMoreThan}",groups = {InsertGroup.class,UpdateGroup.class})
    private String component;

    /**
     * 权限标识
     */
    @Size(max = 50, message = "{noMoreThan}",groups = {InsertGroup.class})
    @NotBlank(message = "{required}",groups = {InsertGroup.class, UpdateGroup.class})
    private String perms;

    /**
     * 图标
     */
    @TableField("ICON")
    private String icon;

    /**
     * 类型 0菜单 1按钮
     */
    @NotBlank(message = "{required}",groups = {InsertGroup.class,UpdateGroup.class})
    @Range(min = 0,max = 2,message = "{input.illegal}",groups = {QueryGroup.class,InsertGroup.class,UpdateGroup.class})
    private String type;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 菜单对应的所有角色ID
     */
    private List<Role> roleList;

    private transient String createTimeFrom;

    private transient String createTimeTo;

}
