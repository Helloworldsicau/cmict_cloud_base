package com.cmict.server.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "事件类型枚举返回数据")
public class EventTypeVO {

    @ApiModelProperty("事件类型号")
    private String type;

    @ApiModelProperty("事件类型描述")
    private String description;

    @ApiModelProperty("事件类型英文描述")
    private String label;
}
