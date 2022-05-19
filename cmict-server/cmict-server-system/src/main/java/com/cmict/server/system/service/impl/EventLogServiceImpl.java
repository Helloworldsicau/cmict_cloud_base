package com.cmict.server.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmict.entity.system.EventLog;
import com.cmict.server.system.mapper.EventLogMapper;
import com.cmict.server.system.service.IEventLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author luozd
 * @since 2021-03-17
 */
@Service
public class EventLogServiceImpl extends ServiceImpl<EventLogMapper, EventLog> implements IEventLogService {

}
