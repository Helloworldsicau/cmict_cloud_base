package com.cmict.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmict.entity.system.Event;
import com.cmict.server.system.vo.EventTypeStatisticsVO;
import com.cmict.server.system.vo.HighFrequencyIncidentPlaceVO;
import com.cmict.server.system.vo.RealTimeEventNotificationVO;
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
public interface EventMapper extends BaseMapper<Event> {
    /**
     * 高频事发地列表
     *
     * @param type      事件类型
     * @param areaId    片区ID
     * @return
     */
    List<HighFrequencyIncidentPlaceVO> highFrequencyIncidentPlaceList(@Param("type") String type, @Param("areaId") String areaId);

    /**
     * 发现事件设备数
     *
     * @return
     */
    int findEventDeviceCount(@Param("areaId") String areaId);

    /**
     * 当日发现事件设备数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    int todayFindEventDeviceCount(@Param("areaId") String areaId, @Param("startTime")String startTime, @Param("endTime") String endTime);

    /**
     * 实时事件通报
     *
     * @return
     */
    List<RealTimeEventNotificationVO> selectRealTimeEventNotificationList(@Param("areaId") String areaId, @Param("userId") String userId);

    IPage<Event> findEventListPage(Page<Event> page, @Param("event")Event event);

    /**
     * 地图高亮显示设备数
     *
     * @param userId     用户ID
     * @param deviceId   设备ID
     * @param yesterday  昨天的当前日期
     * @return
     */
    int brightCount(@Param("userId") String userId,
                    @Param("deviceId") String deviceId,
                    @Param("yesterday") String yesterday);

    List<Event> findEventList(@Param("event") Event event);

    List<EventTypeStatisticsVO> eventTypeStatistic(@Param("event")Event event);
}
