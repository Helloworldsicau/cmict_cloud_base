package com.cmict.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmict.entity.system.EventLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author luozd
 * @since 2021-03-17
 */
public interface EventLogMapper extends BaseMapper<EventLog> {


    /**
     * 操作描述统计
     *
     * @param areaId
     * @param operations
     * @param startTime
     * @param endTime
     * @return
     */
    int countByAreaIdAndOperation(@Param("areaId")String areaId,
                                  @Param("operations") List<String> operations,
                                  @Param("startTime")String startTime,
                                  @Param("endTime")String endTime);
}
