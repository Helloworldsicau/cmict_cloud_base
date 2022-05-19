package com.cmict.activiti.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.activiti.engine.task.Task;

import java.io.Serializable;

/**
 * @author luffy
 * @date 2021/8/17
 */

@TableName("t_leaveapply")
@Data
@ApiModel("请假表")
public class LeaveApply implements Serializable {

    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    Integer id;

    @ApiModelProperty("流程实例id")
    @TableField("process_instance_id")
    String processInstanceId;

    @ApiModelProperty("用户名")
    @TableField("user_id")
    String userId;

    @ApiModelProperty("请假起始时间")
    @TableField("start_time")
    String startTime;

    @ApiModelProperty("请假结束时间")
    @TableField("end_time")
    String endTime;

    @ApiModelProperty("请假类型")
    @TableField("leave_type")
    String leaveType;

    @ApiModelProperty("请假原因")
    String reason;

    @ApiModelProperty("申请时间")
    @TableField("apply_time")
    String applyTime;

    @ApiModelProperty("实际请假起始时间")
    String reality_start_time;

    @ApiModelProperty("实际请假结束时间")
    String reality_end_time;

    @ApiModelProperty("请假审批人")
    @TableField("deptleader")
    String deptLeader;

    @ApiModelProperty("当前流程节点")
    String node;

    Task task;

    String executionid;

    String activityid;

    String state;




}
