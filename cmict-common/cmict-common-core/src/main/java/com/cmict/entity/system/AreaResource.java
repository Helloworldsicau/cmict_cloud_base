package com.cmict.entity.system;

import lombok.Data;

/**
 * @Author: lichenxin
 * @Date: 2021/4/15
 * 视频平台资源类 用户JSON转换
 */
@Data
public class AreaResource {
    /**
     *区域编号
     */
    private String indexCode;
    /**
     *区域名称
     */
    private String name;
    /**
     *区域完整路径，含本节点，@进行分割，上级节点在前
     */
    private String regionPath;
    /**
     *父区域唯一标识码
     */
    private String parentIndexCode;
    /**
     *用于标识区域节点是否有权限操作，true：有权限 false：无权限
     */
    private Boolean available;
    /**
     *  true:是叶子节点，表示该区域下面未挂区域 false:不是叶子节点，表示该区域下面挂有区域
     */
    private Boolean leaf;
    /**
     *级联平台标识，多个级联编号以@分隔，本级区域默认值“0”
     */
    private String cascadeCode;
    /**
     *区域标识
     */
    private String cascadeType;
    /**
     * 区域类型
     */
    private String catalogType;
    /**
     *
     */
    private String externalIndexCode;
    /**
     *同级区域顺序
     */
    private Integer sort;
    /**
     *  	创建时间
     */
    private String createTime;
    /**
     *
     */
    private String updateTime;

}
