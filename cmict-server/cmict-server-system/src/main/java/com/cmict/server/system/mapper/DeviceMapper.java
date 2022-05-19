package com.cmict.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmict.entity.system.Device;
import com.cmict.server.system.vo.DevicePositionVO;
import com.cmict.server.system.vo.QueryCameraVO;
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
public interface DeviceMapper extends BaseMapper<Device> {

    List<DevicePositionVO> selectDevicePosition(@Param("areaId") String areaId);
    IPage<Device> findDeviceListPage(Page<Device> page, Boolean type, String deviceName);
    /**
     * 通过区域ID查询摄像头
     *
     * @param areaId
     * @return
     */
    List<QueryCameraVO> deviceListByAreaId(@Param("areaId") String areaId);
}
