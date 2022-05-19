package com.cmict.server.system.log;

import com.cmict.entity.system.EventLog;
import com.cmict.server.system.service.IEventLogService;
import com.cmict.untils.CmictUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author chentianshu
 * @since 2021/3/23
 * 记录事件处理日志切面
 */
@Component
@Aspect
public class EventLogAspect {

    public static final String PARAM_PREFIX = "param";

    @Autowired
    IEventLogService service;


    @AfterReturning(pointcut = "execution(public * com.cmict.server.system.controller.*.*(..)) && @annotation(log)")
    public void saveEventLog(JoinPoint joinPoint, EventLogger log){
        String caseNumber = getCaseNumber(joinPoint, log);
        if (caseNumber == null){
            return ;
        }
        String operation = log.operation();
        String operator = CmictUtil.getCurrentUsername();
        LocalDateTime now = LocalDateTime.now();

        EventLog  newLog = new EventLog();
        newLog.setCaseNumber(caseNumber);
        newLog.setOperation(operation);
        newLog.setOperator(operator);
        newLog.setOperationTime(now);
        service.save(newLog);

    }

    private String getCaseNumber(JoinPoint joinPoint, EventLogger log){
        String param = log.targetParam();
        if ("".equals(param)){
            return null;
        }
        String[] split = param.split("\\.");
        int index = Integer.parseInt(split[0].replace(PARAM_PREFIX, "")) - 1;
        Object[] args = joinPoint.getArgs();
        if (split.length == 1){
            return Objects.toString(args[index]);
        }
        else{
            return Objects.toString(CmictUtil.parseSpringEl(args[index], split[1]));
        }

    }


}
