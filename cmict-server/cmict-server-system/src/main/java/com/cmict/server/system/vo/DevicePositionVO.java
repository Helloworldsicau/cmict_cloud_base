package com.cmict.server.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "设备经纬度")
public class DevicePositionVO {
    /**
     * 设备ID
     */
    @ApiModelProperty("设备ID")
    private String deviceId;
    /**
     * 经度
     */
    @ApiModelProperty("经度")
    private String lon;
    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    private String lat;
}
