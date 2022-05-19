package com.cmict.server.system.service;

import com.cmict.entity.system.Area;
import com.cmict.entity.system.Event;
import com.cmict.server.system.vo.*;

import java.util.List;
import java.util.Map;

public interface IndexService {
    /**
     * 实时事件统计
     *
     * @param areaId 区域ID
     * @return
     * @throws Exception
     */
    IndexEventTypeStatisticsVO realTimeEventStatistics(String areaId) throws Exception;

    /**
     * 高频事发地
     *
     * @param type 事件类型
     * @param areaId 片区ID
     * @return
     */
    List<HighFrequencyIncidentPlaceVO> highFrequencyIncidentPlaceList(String type,String areaId) throws Exception;

    /**
     * 设备统计
     */
    Map deviceStatistics(String areaId) throws Exception;

    /**
     * 实时事件通报
     */
    List<RealTimeEventNotificationVO> realTimeEventNotification(String areaId, String userId) throws Exception;

    /**
     * 按设备ID 实时事件统计
     *
     * @param deviceId 设备ID
     * @param userId   用户ID
     * @return
     * @throws Exception
     */
    IndexEventTypeStatisticsByDeviceIdVO realTimeEventStatisticsByDeviceId(String deviceId, String userId) throws Exception;

    /**
     * 查询设备
     *
     * @param userId    用户ID
     * @return
     * @throws Exception
     */
    List<QueryAreaDeviceVO> queryDevice(String userId) throws Exception;

    /**
     * 用户未查看事件数
     *
     * @param userId
     * @return
     */
    int noCheckEventCount(String userId);

    /**
     * 关注的设备新事件消息推送
     *
     * @param userId
     * @return
     */
    List<Event> messagePush(String userId);

    /**
     * 首页各类统计数据
     *
     * @param areaId    片区ID
     * @param type      事件类型
     * @param userId    用户ID
     * @return
     */
    Map indexStatistics(String areaId, String type, String userId) throws Exception;

    /**
     * 设备经纬度数据
     *
     * @param areaId 区域ID
     * @return
     * @throws Exception
     */
    List<DevicePositionVO> devicePosition(String areaId) throws Exception;
    /**
     * 片区列表
     *
     * @return
     */
    List<Area> areaList();

    /**
     * 通过区域ID查询摄像头
     *
     * @param areaId
     * @return
     */
    List<QueryCameraVO> deviceListByAreaId(String areaId);

    /**
     * 地图点是否高亮显示
     *
     * @param userId    用户ID
     * @param areaId    区域ID
     * @return
     */
    List<MapIsBrightVO> mapIsBright(String userId, String areaId);
}
