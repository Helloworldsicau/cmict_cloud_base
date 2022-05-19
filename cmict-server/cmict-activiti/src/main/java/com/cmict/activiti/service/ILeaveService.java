package com.cmict.activiti.service;

import com.cmict.activiti.entity.LeaveApply;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.List;


public interface ILeaveService {
    ProcessInstance startWorkflow(LeaveApply apply);

    //部门领导审批
    int getalldepttask(String userId);

    List<LeaveApply> getpagedepttask(String userId);

    //人事审批
    int getallhrtask(String username);

    List<LeaveApply> getpagehrtask(String username);


    //获取销假列表
    List<LeaveApply> getpageXJtask(String username);

    int getallXJtask(String username);

    //销假
    void completereportback(String taskId, String realStartTime, String realEndTime);

    //调整申请
    List<LeaveApply> getUpdateApplyTask(String username);

    int getAllUpdateApplyTask(String username);

    void updatecomplete(String taskid, LeaveApply leave, String reappply);

    //查询流程进度
    List<LeaveApply> getPageByApplyer(String username);

    int getAllByApplyer(String username);
    List<String> getHighLightedFlows(ProcessDefinitionEntity deployedProcessDefinition, List<HistoricActivityInstance> historicActivityInstances);

}

