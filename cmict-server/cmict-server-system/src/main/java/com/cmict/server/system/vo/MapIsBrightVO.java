package com.cmict.server.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "地图点是否高亮显示")
public class MapIsBrightVO {

    /**
     * 设备Id
     */
    @ApiModelProperty("设备Id")
    private String deviceId;
    /**
     * 经度
     */
    @ApiModelProperty("经度")
    private BigDecimal lon;

    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    private BigDecimal lat;

    /**
     * 是否高亮
     */
    @ApiModelProperty("是否高亮")
    private boolean isBright;

    /**
     * 区域ID
     */
    @ApiModelProperty("区域ID")
    private String areaId;
}
