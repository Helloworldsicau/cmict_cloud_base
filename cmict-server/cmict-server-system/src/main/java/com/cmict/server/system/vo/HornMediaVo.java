package com.cmict.server.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class HornMediaVo {

    @ApiModelProperty("媒资编号")
    private Integer mediaId;

    @ApiModelProperty("文本内容")
    private String content;
}
