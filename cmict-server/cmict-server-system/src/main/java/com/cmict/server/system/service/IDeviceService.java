package com.cmict.server.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cmict.entity.QueryRequest;
import com.cmict.entity.system.Device;
import com.cmict.server.system.vo.HornVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author luozd
 * @since 2021-03-17
 */
public interface IDeviceService extends IService<Device> {

    IPage<Device> getDeviceList(QueryRequest queryRequest, Boolean type, String deviceName);

    List<HornVO> getAllHorn();

    void updateRemark(Device device);

    void bindHorn(Device device);

    void attendCamera(Device device);

    void cancelAttendCamera(Device device);

    List<Device> getDeviceListByType(Integer type);

    String getBindDevice(String cameraId);

    boolean isAttended(String cameraId);
}
