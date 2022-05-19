package com.cmict.server.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cmict.entity.QueryRequest;
import com.cmict.entity.system.Event;
import com.cmict.entity.system.EventLog;
import com.cmict.server.system.vo.EventTypeStatisticsVO;
import com.cmict.server.system.vo.EventTypeVO;
import com.cmict.server.system.vo.excel.EventExcelVo;

import java.util.List;

/**
 * @author luozd
 * @since 2021-03-17
 */
public interface IEventService extends IService<Event> {

    IPage<Event> findEventListPage(Event event, QueryRequest queryRequest);

    void lookEvent(String caseNumber);

    void changeAttention(String caseNumber, Boolean b);

    List<EventLog> getLogList(String caseNumber);

    List<EventTypeStatisticsVO> eventTypeStatistics(Event event);

    void saveEvent(Event event);

    List<EventTypeVO> eventType();

    List<EventExcelVo> getEventExcelData(Event event);
}
