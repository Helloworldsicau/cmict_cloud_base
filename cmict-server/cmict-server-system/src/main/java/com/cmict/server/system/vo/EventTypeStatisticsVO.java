package com.cmict.server.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "事件类型统计数据")
public class EventTypeStatisticsVO {

    @ApiModelProperty("事件类型号")
    private String number;

    @ApiModelProperty("事件类型描述")
    private String description;

    @ApiModelProperty("事件数")
    private int count;

}
