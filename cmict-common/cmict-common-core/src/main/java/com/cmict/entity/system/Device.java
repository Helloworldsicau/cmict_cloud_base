package com.cmict.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author luozd
 * @since 2021-03-17
 */
@ApiModel
@TableName("t_device")
@Data
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("设备编号")
    @NotBlank(message = "不能为空")
    @TableId("DEVICE_ID")
    private String deviceId;

    @ApiModelProperty("设备名称")
    @TableField("DEVICE_NAME")
    private String deviceName;

    @ApiModelProperty("设备唯一编码")
    @TableField("INDEX_CODE")
    private String indexCode;

    @ApiModelProperty("设备型号")
    @TableField("DEVICE_TYPE")
    private String deviceType;

    @ApiModelProperty("设备类型(false:摄像头, true:大喇叭)")
    @TableField("TYPE")
    private Boolean type;

    @ApiModelProperty("备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty("设备所属片区ID")
    @TableField("AREA_ID")
    private String areaId;

    @ApiModelProperty("经度")
    @TableField("LON")
    private BigDecimal lon;

    @ApiModelProperty("纬度")
    @TableField("LAT")
    private BigDecimal lat;

    @ApiModelProperty("地址")
    @TableField("ADDRESS")
    private String address;

    @ApiModelProperty("绑定设备编号")
    @TableField("BIND_DEVICE")
    private String bindDevice;

    @ApiModelProperty("设备状态")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty("绑定时间")
    @TableField("BIND_TIME")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime bindTime;

    @ApiModelProperty("修改时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField("MODIFY_TIME")
    private LocalDateTime modifyTime;

    @ApiModelProperty("是否关注")
    @TableField(exist = false)
    private Boolean attention;


}
