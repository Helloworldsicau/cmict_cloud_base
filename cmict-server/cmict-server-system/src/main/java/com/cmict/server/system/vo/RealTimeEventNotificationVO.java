package com.cmict.server.system.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@ApiModel(value = "实时事件通报返回数据")
public class RealTimeEventNotificationVO {
    @ApiModelProperty("事件编号")
    private String caseNumber;

    @ApiModelProperty("视频编号")
    private String videoId;

    @ApiModelProperty("摄像头编号")
    private String deviceId;

    @ApiModelProperty("摄像头名称")
    private String deviceName;

    @ApiModelProperty("告警图URI")
    private String causeImageUri;

    @ApiModelProperty("核查图URI")
    private String checkImageUri;

    @ApiModelProperty("告警视频URI")
    private String causeVideoUri;

    @ApiModelProperty("告警时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @ApiModelProperty("告警地点")
    private String address;

    @ApiModelProperty("经度")
    private BigDecimal lon;

    @ApiModelProperty("纬度")
    private BigDecimal lat;

    @ApiModelProperty("告警类型")
    private String type;

    @ApiModelProperty("问题描述")
    private String description;

    @ApiModelProperty("是否上报")
    private Boolean report;

    @ApiModelProperty("事件所属区域ID")
    private String areaId;

    @ApiModelProperty("关注;0.未关注 1.已关注")
    private Boolean attention;


}
