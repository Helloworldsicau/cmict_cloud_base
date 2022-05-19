package com.cmict.server.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "通过区域ID查询摄像头返回数据")
public class QueryCameraVO {

    @ApiModelProperty("摄像头ID")
    private String deviceId;

    @ApiModelProperty("摄像头名称")
    private String deviceName;
}
