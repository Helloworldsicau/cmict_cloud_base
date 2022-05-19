package com.cmict.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
@Data
@TableName("t_menu")
@ApiModel(value = "菜单对象")
public class Menu implements Serializable {
    private static final long serialVersionUID = 7187628714679791771L;

    /**
     * 菜单
     */
    public static final String TYPE_MENU = "0";
    /**
     * 按钮
     */
    public static final String TYPE_BUTTON = "1";

    /**
     * 为前端新加的值   仅仅返回按钮
     */
    public static final String TYPE_BUTTON_ONLY = "2";
    public static final Long TOP_MENU_ID = 0L;

    /**
     * 菜单/按钮ID
     */
    @TableId(value = "MENU_ID", type = IdType.AUTO)
    private Long menuId;

    /**
     * 上级菜单ID
     */
    @TableField("PARENT_ID")
    @ApiModelProperty("上级菜单ID")
    @NotBlank(message = "{required}")
    private Long parentId;

    /**
     * 菜单/按钮名称
     */
    @TableField("MENU_NAME")
    @NotBlank(message = "{required}")
    @Size(max = 10, message = "{noMoreThan}")
    @ApiModelProperty("菜单/按钮名称")
    private String menuName;

    /**
     * 菜单URL
     */
    @TableField("PATH")
    @Size(max = 100, message = "{noMoreThan}")
    @ApiModelProperty(value = "菜单URL,对用浏览器URL",example = "/system/test")
    private String path;

    /**
     * 路由重定向
     */
    @TableField("REDIRECT")
    @ApiModelProperty(value = "路由重定向")
    private String redirect;

    /**
     * 对应 Vue组件
     */
    @TableField("COMPONENT")
    @Size(max = 100, message = "{noMoreThan}")
    @ApiModelProperty("对应 Vue组件")
    private String component;

    /**
     * 权限标识
     */
    @TableField("PERMS")
    @Size(max = 50, message = "{noMoreThan}")
    @ApiModelProperty("权限")
    private String perms;

    /**
     * 图标
     */
    @TableField("ICON")
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 类型 0菜单 1按钮
     */
    @TableField("TYPE")
    @NotBlank(message = "{required}")
    @ApiModelProperty("类型 0菜单 1按钮")
    private String type;

    /**
     * 排序
     */
    @TableField("ORDER_NUM")
    @ApiModelProperty(value = "排序",dataType = "int")
    private Integer orderNum;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    @ApiModelProperty(hidden = true)
    private Date modifyTime;

    /**
     * 菜单对应的所有角色ID
     */
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private List<Role> roleList;

    @ApiModelProperty(hidden = true)
    private transient String createTimeFrom;
    @ApiModelProperty(hidden = true)
    private transient String createTimeTo;
}
