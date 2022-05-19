package com.cmict.activiti.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmict.activiti.entity.LeaveApply;
import com.cmict.activiti.entity.Task;

import java.util.List;

public interface LeaveApplyMapper extends BaseMapper<LeaveApply> {
    void save(LeaveApply apply);

    int updateByPrimaryKey(LeaveApply record);

    LeaveApply getLeaveApply(int id);

    List<LeaveApply> listLeaveApplyByApplyer(String applyer);

    Task getTaskName(String processInstanceId);
}
