package com.cmict.entity.system;

import lombok.Data;

/**
 * @Author: lichenxin
 * @Date: 2021/4/19
 */
@Data
public class CameraResource {

    public static final String ONLINE = "在线";
    public static final String OFFLINE = "离线";
    public static final String UNKNOWN = "未检测";
    /**
     * 设备名称
     */
    private String cn;

    /**
     * 监控点唯一标识
     */
    private String cameraIndexCode;
    /**
     * 等于cameraIndexCode  用户其他接口返回字段不一样 接收使用
     */
    private String indexCode;
    /**
     * 监控点名称
     */
    private String cameraName;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 所属编码设备唯一标识
     */
    private String encodeDevIndexCode;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 所属区域唯一标识
     */
    private String regionIndexCode;

    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 设备在线状态  0 离线 1 在线
     */
    private Integer online;


    public String getState() {
        if (this.online == null || this.online.intValue() == 0) {
            return OFFLINE;
        } else if (this.online.intValue() == 1) {
            return ONLINE;
        }
        return UNKNOWN;
    }
}
