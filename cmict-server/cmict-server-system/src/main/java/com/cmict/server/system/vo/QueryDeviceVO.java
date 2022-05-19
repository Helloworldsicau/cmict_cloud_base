package com.cmict.server.system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class QueryDeviceVO {
    /**
     * 设备SN
     */
    @ApiModelProperty("设备ID")
    private String deviceId;

    /**
     * 绑定的设备SN
     */
    @ApiModelProperty("绑定设备")
    private String bindDevice;

    /**
     * 名称和地址
     */
    @ApiModelProperty("名称和地址")
    private String nameAndAddress;

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

}
