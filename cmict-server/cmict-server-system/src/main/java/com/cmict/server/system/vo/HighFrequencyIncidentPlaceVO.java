package com.cmict.server.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "高频事发地返回数据")
public class HighFrequencyIncidentPlaceVO {
    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String address;
    /**
     * 数量
     */
    @ApiModelProperty("数量")
    private int count;
}
