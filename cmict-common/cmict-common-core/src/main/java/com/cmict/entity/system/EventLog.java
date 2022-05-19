package com.cmict.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
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
@TableName("t_event_log")
@Data
public class EventLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("日志编号")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("所属事件")
    @TableField("CASE_NUMBER")
    private String caseNumber;

    @ApiModelProperty("操作描述")
    @TableField("OPERATION")
    private String operation;

    @ApiModelProperty("操作人用户名")
    @TableField("OPERATOR")
    private String operator;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("操作时间")
    @TableField("OPERATION_TIME")
    private LocalDateTime operationTime;

}
