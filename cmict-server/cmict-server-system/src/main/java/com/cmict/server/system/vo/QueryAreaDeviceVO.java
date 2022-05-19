package com.cmict.server.system.vo;

import com.cmict.entity.system.Area;
import com.cmict.entity.system.Device;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@ApiModel(value = "区域设备返回数据")
public class QueryAreaDeviceVO {
    /**
     * 区域
     */
    @ApiModelProperty("区域")
    private Area area;
    /**
     * 设备列表
     */
    @ApiModelProperty("设备列表")
    private List<QueryDeviceVO> deviceList;
}
