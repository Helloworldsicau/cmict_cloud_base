package com.cmict.entity.system;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author luozd
 * @since 2021-03-17
 */
@TableName("t_event")
@Data
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("事件编号")
    @TableId("CASE_NUMBER")
    private String caseNumber;

    @ApiModelProperty("视频编号")
    @TableField("VIDEO_ID")
    private String videoId;

    @ApiModelProperty("摄像头编号")
    @TableField("DEVICE_ID")
    private String deviceId;

    @ApiModelProperty("摄像头名称")
    @TableField("DEVICE_NAME")
    private String deviceName;

    @ApiModelProperty("告警图URI")
    @TableField("CASE_IMAGE_URI")
    private String causeImageUri;

    @ApiModelProperty("核查图URI")
    @TableField("CHECK_IMAGE_URI")
    private String checkImageUri;

    @ApiModelProperty("告警视频URI")
    @TableField("CASE_VIDEO_URI")
    private String causeVideoUri;

    @ApiModelProperty("告警时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @ApiModelProperty("告警地点")
    @TableField("ADDRESS")
    private String address;

    @ApiModelProperty("经度")
    @TableField("LON")
    private BigDecimal lon;

    @ApiModelProperty("纬度")
    @TableField("LAT")
    private BigDecimal lat;

    /**
     * 接收经纬度的实体类
     */
    @NotNull(message = "{required}")
    @TableField(exist = false)
    private Location location;

    @ApiModelProperty("告警类型")
    @TableField("TYPE")
    private String type;

    @ApiModelProperty("问题描述")
    @TableField("DESCRIPTION")
    private String description;

    @ApiModelProperty("是否上报")
    @TableField("REPORT")
    private Boolean report;

    @ApiModelProperty("事件所属区域ID")
    @TableField("AREA_ID")
    private String areaId;

    @ApiModelProperty("开始时间")
    @TableField(exist = false)
    private String createTimeFrom;

    @ApiModelProperty("结束时间")
    @TableField(exist = false)
    private String createTimeTo;

    @ApiModelProperty("用户ID")
    @TableField(exist = false)
    private Long userId;

    @ApiModelProperty("是否查看")
    @TableField(exist = false)
    private Boolean look;

    @ApiModelProperty("是否关注")
    @TableField(exist = false)
    private Boolean attention;

}



