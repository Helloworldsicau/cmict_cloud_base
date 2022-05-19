package com.cmict.server.system.service.impl;

import com.alibaba.excel.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cmict.entity.system.*;
import com.cmict.server.system.enums.DeviceStatus;
import com.cmict.server.system.enums.EventType;
import com.cmict.server.system.mapper.*;
import com.cmict.server.system.service.IndexService;
import com.cmict.server.system.vo.*;
import com.cmict.untils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class IndexServiceImpl implements IndexService {
    private static String NOW_DAY = "";
    private static String YESTERDAY = "";
    private static String START_TIME = "";
    private static String END_TIME = "";
    /**
     * 日志类型-喊话
     */
    private static final String EVENT_LOG_TYPE_SHOUTING = "实时喊话";
    /**
     * 日志类型-喊话
     */
    private static final String EVENT_LOG_TYPE_VOICE_SHOUTING = "语音播报";
    /**
     * 日志类型-上报
     */
    private static final String EVENT_LOG_TYPE_REPORT = "上报";
    /**
     * 日志类型-查看视频
     */
    private static final String EVENT_LOG_TYPE_LOOK_VEDIO = "查看视频";
    /**
     * 设备类型-大喇叭
     */
    private static final int DEVICE_TYPE_HORN = 1;
    /**
     * 设备类型-摄像头
     */
    private static final int DEVICE_TYPE_CAMERA = 0;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventLogMapper eventLogMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private EventUserMapper eventUserMapper;
    @Autowired
    private AttentionMapper attentionMapper;

    /**
     * 刷新日期时间
     */
    public static void refreshDate() {
        NOW_DAY = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
        YESTERDAY = DateUtil.getDateFormat(new Date(new Date().getTime() - 86400000L), "yyyy-MM-dd HH:mm:ss");
        START_TIME = NOW_DAY + " 00:00:00";
        END_TIME = NOW_DAY + " 23:59:59";
    }

    /**
     * 计算时间差
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    private String times(Date startTime, Date endTime) {
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        String result = "";
        // 获得两个时间的毫秒时间差异
        diff = endTime.getTime() - startTime.getTime();
        day = diff / nd;// 计算差多少天
        hour = diff % nd / nh + day * 24;// 计算差多少小时
        min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
        sec = diff % nd % nh % nm / ns;// 计算差多少秒
        if (day > 0) {
            result = day + "天" + (hour - day * 24) + "小时" + (min - day * 24 * 60) + "分钟前";
        } else if (hour > 0) {
            result = (hour - day * 24) + "小时" + (min - day * 24 * 60) + "分钟前";
        } else {
            result = (min - day * 24 * 60) + "分钟前";
        }
        return result;
    }

    @Override
    public IndexEventTypeStatisticsVO realTimeEventStatistics(String areaId) throws Exception {
        IndexEventTypeStatisticsVO result = new IndexEventTypeStatisticsVO();
        if (StringUtils.isEmpty(areaId)) {

            result.setTotalEvent(eventMapper.selectCount(new QueryWrapper<Event>()));
            result.setTodayEvent(eventMapper.selectCount(new QueryWrapper<Event>().between("CREATE_TIME", START_TIME, END_TIME)));

            result.setTotalBusinessWithoutLicense(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE",EventType.ONE.getNumber())));
            result.setTodayBusinessWithoutLicense(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE",EventType.ONE.getNumber()).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalOutOfStoreOperation(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TWO.getNumber())));
            result.setTodayOutOfStoreOperation(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TWO.getNumber()).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalRoadsideBooths(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FOUR.getNumber())));
            result.setTodayRoadsideBooths(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FOUR.getNumber()).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalLittering(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.THREE.getNumber())));
            result.setTodayLittering(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.THREE.getNumber()).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalRandomParkingOfNonMotorVehicles(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FIVE.getNumber())));
            result.setTodayRandomParkingOfNonMotorVehicles(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FIVE.getNumber()).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalRandomParkingOfMotorVehicles(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SIX.getNumber())));
            result.setTodayRandomParkingOfMotorVehicles(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SIX.getNumber()).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalResidueTruckIsNotTransportedInClosedCondition(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SEVEN.getNumber())));
            result.setTodayResidueTruckIsNotTransportedInClosedCondition(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SEVEN.getNumber()).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalHangThingAlongStreet(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.EIGHT.getNumber())));
            result.setTodayHangThingAlongStreet(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.EIGHT.getNumber()).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalIllegalAdvertisement(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.NINE.getNumber())));
            result.setTodayIllegalAdvertisement(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.NINE.getNumber()).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalRoadIsNotClean(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TEN.getNumber())));
            result.setTodayRoadIsNotClean(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TEN.getNumber()).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalExposedGarbage(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.ELEVEN.getNumber())));
            result.setTodayExposedGarbage(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.ELEVEN.getNumber()).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalAccumulationOfWasteResidue(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.THIRTEEN.getNumber())));
            result.setTodayAccumulationOfWasteResidue(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.THIRTEEN.getNumber()).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalGarbageOverflowed(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TWELVE.getNumber())));
            result.setTodayGarbageOverflowed(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TWELVE.getNumber()).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalDamageToPublicFacilities(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FOURTEEN.getNumber())));
            result.setTodayDamageToPublicFacilities(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FOURTEEN.getNumber()).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalIllegalOutdoorAdvertising(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FIFTEEN.getNumber())));
            result.setTodayIllegalOutdoorAdvertising(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FIFTEEN.getNumber()).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalBuildingWithoutPermission(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SIXTEEN.getNumber())));
            result.setTodayBuildingWithoutPermission(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SIXTEEN.getNumber()).between("CREATE_TIME", START_TIME,END_TIME)));
        } else{
            result.setTotalEvent(eventMapper.selectCount(new QueryWrapper<Event>().eq("AREA_ID",areaId)));
            result.setTodayEvent(eventMapper.selectCount(new QueryWrapper<Event>().between("CREATE_TIME", START_TIME, END_TIME).eq("AREA_ID",areaId)));

            result.setTotalBusinessWithoutLicense(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE",EventType.ONE.getNumber()).eq("AREA_ID",areaId)));
            result.setTodayBusinessWithoutLicense(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE",EventType.ONE.getNumber()).eq("AREA_ID",areaId).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalOutOfStoreOperation(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TWO.getNumber()).eq("AREA_ID",areaId)));
            result.setTodayOutOfStoreOperation(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TWO.getNumber()).eq("AREA_ID",areaId).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalRoadsideBooths(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FOUR.getNumber()).eq("AREA_ID",areaId)));
            result.setTodayRoadsideBooths(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FOUR.getNumber()).eq("AREA_ID",areaId).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalLittering(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.THREE.getNumber()).eq("AREA_ID",areaId)));
            result.setTodayLittering(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.THREE.getNumber()).eq("AREA_ID",areaId).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalRandomParkingOfNonMotorVehicles(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FIVE.getNumber()).eq("AREA_ID",areaId)));
            result.setTodayRandomParkingOfNonMotorVehicles(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FIVE.getNumber()).eq("AREA_ID",areaId).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalRandomParkingOfMotorVehicles(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SIX.getNumber()).eq("AREA_ID",areaId)));
            result.setTodayRandomParkingOfMotorVehicles(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SIX.getNumber()).eq("AREA_ID",areaId).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalResidueTruckIsNotTransportedInClosedCondition(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SEVEN.getNumber()).eq("AREA_ID",areaId)));
            result.setTodayResidueTruckIsNotTransportedInClosedCondition(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SEVEN.getNumber()).eq("AREA_ID",areaId).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalHangThingAlongStreet(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.EIGHT.getNumber()).eq("AREA_ID",areaId)));
            result.setTodayHangThingAlongStreet(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.EIGHT.getNumber()).eq("AREA_ID",areaId).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalIllegalAdvertisement(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.NINE.getNumber()).eq("AREA_ID",areaId)));
            result.setTodayIllegalAdvertisement(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.NINE.getNumber()).eq("AREA_ID",areaId).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalRoadIsNotClean(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TEN.getNumber()).eq("AREA_ID",areaId)));
            result.setTodayRoadIsNotClean(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TEN.getNumber()).eq("AREA_ID",areaId).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalExposedGarbage(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.ELEVEN.getNumber()).eq("AREA_ID",areaId)));
            result.setTodayExposedGarbage(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.ELEVEN.getNumber()).eq("AREA_ID",areaId).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalGarbageOverflowed(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TWELVE.getNumber())));
            result.setTodayGarbageOverflowed(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TWELVE.getNumber()).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalAccumulationOfWasteResidue(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.THIRTEEN.getNumber()).eq("AREA_ID",areaId)));
            result.setTodayAccumulationOfWasteResidue(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.THIRTEEN.getNumber()).eq("AREA_ID",areaId).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalDamageToPublicFacilities(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FOURTEEN.getNumber()).eq("AREA_ID",areaId)));
            result.setTodayDamageToPublicFacilities(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FOURTEEN.getNumber()).eq("AREA_ID",areaId).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalIllegalOutdoorAdvertising(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FIFTEEN.getNumber()).eq("AREA_ID",areaId)));
            result.setTodayIllegalOutdoorAdvertising(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FIFTEEN.getNumber()).eq("AREA_ID",areaId).between("CREATE_TIME", START_TIME,END_TIME)));

            result.setTotalBuildingWithoutPermission(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SIXTEEN.getNumber()).eq("AREA_ID",areaId)));
            result.setTodayBuildingWithoutPermission(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SIXTEEN.getNumber()).eq("AREA_ID",areaId).between("CREATE_TIME", START_TIME,END_TIME)));
        }
        List<String> operations = new ArrayList<>();
        operations.add(EVENT_LOG_TYPE_SHOUTING);
        operations.add(EVENT_LOG_TYPE_VOICE_SHOUTING);
        result.setTotalShouting(eventLogMapper.countByAreaIdAndOperation(areaId, operations,"",""));
        result.setTodayShouting(eventLogMapper.countByAreaIdAndOperation(areaId, operations, START_TIME, END_TIME));
        return result;
    }

    @Override
    public List<HighFrequencyIncidentPlaceVO> highFrequencyIncidentPlaceList(String type,String areaId) throws Exception{
        return eventMapper.highFrequencyIncidentPlaceList(type,areaId);
    }

    @Override
    public Map deviceStatistics(String areaId) throws Exception {
        int totalDevice = 0;
        int todayOnlineDevice = 0;
        int totalFindEventDevice = 0;
        int todayFindEventDevice = 0;
        if (StringUtils.isEmpty(areaId)) {
            totalDevice = deviceMapper.selectCount(new QueryWrapper<Device>().eq("TYPE", 0));
            todayOnlineDevice = deviceMapper.selectCount(new QueryWrapper<Device>().eq("TYPE", DEVICE_TYPE_CAMERA).eq("STATUS", DeviceStatus.ONLINE.getDescription()));
        } else {
            totalDevice = deviceMapper.selectCount(new QueryWrapper<Device>().eq("AREA_ID", areaId).eq("TYPE", 0));
            todayOnlineDevice = deviceMapper.selectCount(new QueryWrapper<Device>().eq("AREA_ID", areaId).eq("TYPE", DEVICE_TYPE_CAMERA).eq("STATUS",DeviceStatus.ONLINE.getDescription()));
        }
        totalFindEventDevice = eventMapper.findEventDeviceCount(areaId);
        todayFindEventDevice = eventMapper.todayFindEventDeviceCount(areaId, START_TIME, END_TIME);

        Map map = new LinkedHashMap();
        map.put("totalDevice", totalDevice);
        map.put("todayOnlineDevice", todayOnlineDevice);
        map.put("totalFindEventDevice", totalFindEventDevice);
        map.put("todayFindEventDevice", todayFindEventDevice);
        return map;
    }

    @Override
    public List<RealTimeEventNotificationVO> realTimeEventNotification(String areaId, String userId) throws Exception {
        return eventMapper.selectRealTimeEventNotificationList(areaId, userId);
    }

    @Override
    public IndexEventTypeStatisticsByDeviceIdVO realTimeEventStatisticsByDeviceId(String deviceId, String userId) throws Exception {
        refreshDate();
        IndexEventTypeStatisticsByDeviceIdVO result = new IndexEventTypeStatisticsByDeviceIdVO();
        Device device = deviceMapper.selectOne(new QueryWrapper<Device>().eq("DEVICE_ID", deviceId));
        if (device != null) {
            result.setDeviceName(device.getDeviceName());
            result.setHornId(device.getBindDevice());
            result.setLon(device.getLon());
            result.setLat(device.getLat());
            result.setAddress(device.getAddress());
            result.setStatus(device.getStatus());
        }
        result.setAttention(attentionMapper.selectCount(new QueryWrapper<Attention>().eq("DEVICE_ID", deviceId).eq("USER_ID", userId)) > 0 ? true : false);
        Event event = eventMapper.selectOne(new QueryWrapper<Event>().eq("DEVICE_ID", deviceId).orderByDesc("CREATE_TIME").last("LIMIT 0,1"));
        if (event != null) {
            result.setCaseNumber(event.getCaseNumber());
            result.setEventType(event.getType());
            result.setDescription(event.getDescription());
            result.setTime(times(event.getCreateTime(), new Date()));
        }
        result.setTotalEvent(eventMapper.selectCount(new QueryWrapper<Event>().eq("DEVICE_ID", deviceId)));
        result.setTodayEvent(eventMapper.selectCount(new QueryWrapper<Event>().between("CREATE_TIME", START_TIME, END_TIME).eq("DEVICE_ID", deviceId)));

        result.setTotalBusinessWithoutLicense(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE",EventType.ONE.getNumber()).eq("DEVICE_ID", deviceId)));
        result.setTodayBusinessWithoutLicense(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE",EventType.ONE.getNumber()).eq("DEVICE_ID", deviceId).between("CREATE_TIME", START_TIME,END_TIME)));

        result.setTotalOutOfStoreOperation(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TWO.getNumber()).eq("DEVICE_ID", deviceId)));
        result.setTodayOutOfStoreOperation(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TWO.getNumber()).eq("DEVICE_ID", deviceId).between("CREATE_TIME", START_TIME,END_TIME)));

        result.setTotalRoadsideBooths(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FOUR.getNumber()).eq("DEVICE_ID", deviceId)));
        result.setTodayRoadsideBooths(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FOUR.getNumber()).eq("DEVICE_ID", deviceId).between("CREATE_TIME", START_TIME,END_TIME)));

        result.setTotalLittering(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.THREE.getNumber()).eq("DEVICE_ID", deviceId)));
        result.setTodayLittering(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.THREE.getNumber()).eq("DEVICE_ID", deviceId).between("CREATE_TIME", START_TIME,END_TIME)));

        result.setTotalRandomParkingOfNonMotorVehicles(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FIVE.getNumber()).eq("DEVICE_ID", deviceId)));
        result.setTodayRandomParkingOfNonMotorVehicles(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FIVE.getNumber()).eq("DEVICE_ID", deviceId).between("CREATE_TIME", START_TIME,END_TIME)));

        result.setTotalRandomParkingOfMotorVehicles(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SIX.getNumber()).eq("DEVICE_ID", deviceId)));
        result.setTodayRandomParkingOfMotorVehicles(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SIX.getNumber()).eq("DEVICE_ID", deviceId).between("CREATE_TIME", START_TIME,END_TIME)));

        result.setTotalResidueTruckIsNotTransportedInClosedCondition(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SEVEN.getNumber()).eq("DEVICE_ID", deviceId)));
        result.setTodayResidueTruckIsNotTransportedInClosedCondition(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SEVEN.getNumber()).eq("DEVICE_ID", deviceId).between("CREATE_TIME", START_TIME,END_TIME)));

        result.setTotalHangThingAlongStreet(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.EIGHT.getNumber()).eq("DEVICE_ID", deviceId)));
        result.setTodayHangThingAlongStreet(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.EIGHT.getNumber()).eq("DEVICE_ID", deviceId).between("CREATE_TIME", START_TIME,END_TIME)));

        result.setTotalIllegalAdvertisement(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.NINE.getNumber()).eq("DEVICE_ID", deviceId)));
        result.setTodayIllegalAdvertisement(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.NINE.getNumber()).eq("DEVICE_ID", deviceId).between("CREATE_TIME", START_TIME,END_TIME)));

        result.setTotalRoadIsNotClean(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TEN.getNumber()).eq("DEVICE_ID", deviceId)));
        result.setTodayRoadIsNotClean(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TEN.getNumber()).eq("DEVICE_ID", deviceId).between("CREATE_TIME", START_TIME,END_TIME)));

        result.setTotalExposedGarbage(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.ELEVEN.getNumber()).eq("DEVICE_ID", deviceId)));
        result.setTodayExposedGarbage(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.ELEVEN.getNumber()).eq("DEVICE_ID", deviceId).between("CREATE_TIME", START_TIME,END_TIME)));

        result.setTotalGarbageOverflowed(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TWELVE.getNumber()).eq("DEVICE_ID", deviceId)));
        result.setTodayGarbageOverflowed(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.TWELVE.getNumber()).eq("DEVICE_ID", deviceId).between("CREATE_TIME", START_TIME,END_TIME)));

        result.setTotalAccumulationOfWasteResidue(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.THIRTEEN.getNumber()).eq("DEVICE_ID", deviceId)));
        result.setTodayAccumulationOfWasteResidue(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.THIRTEEN.getNumber()).eq("DEVICE_ID", deviceId).between("CREATE_TIME", START_TIME,END_TIME)));

        result.setTotalDamageToPublicFacilities(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FOURTEEN.getNumber()).eq("DEVICE_ID", deviceId)));
        result.setTodayDamageToPublicFacilities(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FOURTEEN.getNumber()).eq("DEVICE_ID", deviceId).between("CREATE_TIME", START_TIME,END_TIME)));

        result.setTotalIllegalOutdoorAdvertising(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FIFTEEN.getNumber()).eq("DEVICE_ID", deviceId)));
        result.setTodayIllegalOutdoorAdvertising(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.FIFTEEN.getNumber()).eq("DEVICE_ID", deviceId).between("CREATE_TIME", START_TIME,END_TIME)));

        result.setTotalBuildingWithoutPermission(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SIXTEEN.getNumber()).eq("DEVICE_ID", deviceId)));
        result.setTodayBuildingWithoutPermission(eventMapper.selectCount(new QueryWrapper<Event>().eq("TYPE", EventType.SIXTEEN.getNumber()).eq("DEVICE_ID", deviceId).between("CREATE_TIME", START_TIME,END_TIME)));
        return result;
    }

    @Override
    public List<QueryAreaDeviceVO> queryDevice(String userId) throws Exception {
        List<QueryAreaDeviceVO> queryAreaDeviceVOList = new ArrayList<>();
        List<Area> areaList = areaMapper.selectListByUserId(userId);
        if (areaList != null && areaList.size() > 0) {
            for (Area area:areaList) {
                List<Device> deviceList = deviceMapper.selectList(new QueryWrapper<Device>().eq("AREA_ID", area.getId()).eq("TYPE",0));
                List<QueryDeviceVO> queryDeviceVOList = new ArrayList<>();
                for (Device device:deviceList) {
                    QueryDeviceVO queryDeviceVO = new QueryDeviceVO();
                    queryDeviceVO.setDeviceId(device.getDeviceId());
                    queryDeviceVO.setBindDevice(device.getBindDevice());
                    queryDeviceVO.setNameAndAddress(device.getDeviceName() + "-" + device.getAddress());
                    queryDeviceVO.setLon(device.getLon());
                    queryDeviceVO.setLat(device.getLat());
                    queryDeviceVOList.add(queryDeviceVO);
                }
                QueryAreaDeviceVO queryAreaDeviceVO = new QueryAreaDeviceVO();
                queryAreaDeviceVO.setArea(area);
                queryAreaDeviceVO.setDeviceList(queryDeviceVOList);
                queryAreaDeviceVOList.add(queryAreaDeviceVO);
            }
        }
        return queryAreaDeviceVOList;
    }

    @Override
    public int noCheckEventCount(String userId) {
        return eventUserMapper.selectCount(new QueryWrapper<EventUser>().eq("USER_ID", userId).eq("LOOK",0));
    }

    @Override
    public List<Event> messagePush(String userId) {
        refreshDate();
        List<Event> result = new ArrayList<>();
        List<Attention> attentions = attentionMapper.selectList(new QueryWrapper<Attention>().eq("USER_ID", userId));
        List<String> deviceList = new ArrayList<>();
        attentions.forEach(item -> {
            deviceList.add(item.getDeviceId());
        });
        List<EventUser> eventUsers = eventUserMapper.selectList(new QueryWrapper<EventUser>().eq("USER_ID", userId).eq("LOOK",0));
        List<String> caseNumberList = new ArrayList<>();
        eventUsers.forEach(item -> {
            caseNumberList.add(item.getCaseNumber());
        });
        if (CollectionUtils.isEmpty(caseNumberList)) {
            return result;
        }

        QueryWrapper<Event> qw = new QueryWrapper<Event>();
        if (deviceList != null && deviceList.size() > 0) {
            qw.in("DEVICE_ID", deviceList);
        }
        qw.in("CASE_NUMBER", caseNumberList);
        qw.eq("REPORT", 0).ge("CREATE_TIME", YESTERDAY);
        result = eventMapper.selectList(qw);

        return result;
    }

    @Override
    public Map indexStatistics(String areaId, String type, String userId) throws Exception {
        refreshDate();
        Map map = new LinkedHashMap();
        map.put("realTimeEventStatistics",realTimeEventStatistics(areaId));
        map.put("highFrequencyIncidentPlaceList",highFrequencyIncidentPlaceList(type, areaId));
        map.put("deviceStatistics",deviceStatistics(areaId));
        map.put("realTimeEventNotification",realTimeEventNotification(areaId, userId));
        map.put("devicePosition",devicePosition(areaId));
        return map;
    }

    @Override
    public List<DevicePositionVO> devicePosition(String areaId) {
        return deviceMapper.selectDevicePosition(areaId);
    }

    @Override
    public List<Area> areaList() {
        return areaMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public List<QueryCameraVO> deviceListByAreaId(String areaId) {
        return deviceMapper.deviceListByAreaId(areaId);
    }

    @Override
    public List<MapIsBrightVO> mapIsBright(String userId, String areaId) {
        refreshDate();
        List<MapIsBrightVO> result = new ArrayList<>();
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("type",0);
        if (!StringUtils.isEmpty(areaId)) {
            qw.eq("AREA_ID", areaId);
        }
        List<Device> deviceList = deviceMapper.selectList(qw);
        if (deviceList != null && deviceList.size() > 0) {
            for (Device device: deviceList) {
                MapIsBrightVO mibVO = new MapIsBrightVO();
                mibVO.setDeviceId(device.getDeviceId());
                mibVO.setLat(device.getLat());
                mibVO.setLon(device.getLon());
                mibVO.setAreaId(device.getAreaId());
                mibVO.setBright(eventMapper.brightCount(userId, device.getDeviceId(), YESTERDAY) > 0 ? true : false);
                result.add(mibVO);
            }
        }
        return result;
    }

}
