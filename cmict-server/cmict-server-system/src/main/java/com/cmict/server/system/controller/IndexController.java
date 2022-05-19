package com.cmict.server.system.controller;

import com.cmict.entity.CmictResponse;
import com.cmict.server.system.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/index")
@Api(value = "首页",tags = "首页管理")
public class IndexController {

    @Autowired
    private IndexService indexService;

    @ApiOperation("首页各类统计数据")
    @PostMapping("/indexStatistics")
    public CmictResponse indexStatistics(@RequestParam(value = "areaId",required = false) String areaId,
                                         @RequestParam(value="type",required = false) String type,
                                         @NotBlank(message = "{required}") @RequestParam(value="userId") String userId) throws Exception {
        return CmictResponse.ok(indexService.indexStatistics(areaId, type, userId));
    }

    @ApiOperation("按设备ID实时事件统计")
    @PostMapping("/realTimeEventStatisticsByDeviceId")
    public CmictResponse realTimeEventStatisticsByDeviceId(@NotBlank(message = "{required}") @RequestParam(value = "deviceId") String deviceId,
                                                           @NotBlank(message = "{required}") @RequestParam(value = "userId") String userId) throws Exception {
        return CmictResponse.ok(indexService.realTimeEventStatisticsByDeviceId(deviceId, userId));
    }

    @ApiOperation("查询设备")
    @PostMapping("/queryDevice")
    public CmictResponse queryDevice(@NotBlank(message = "{required}") @RequestParam(value = "userId") String userId) throws Exception {
        return CmictResponse.ok(indexService.queryDevice(userId));
    }

    @ApiOperation("关注的设备新事件消息推送")
    @PostMapping("/messagePush")
    public CmictResponse messagePush(@NotBlank(message = "{required}") @RequestParam(value = "userId") String userId) throws Exception {
        return CmictResponse.ok(indexService.messagePush(userId));
    }

    @ApiOperation("用户未查看事件数")
    @PostMapping("/noCheckEventCount")
    public CmictResponse noCheckEventCount(@NotBlank(message = "{required}") @RequestParam(value = "userId") String userId) throws Exception {
        return CmictResponse.ok(indexService.noCheckEventCount(userId));
    }

    @ApiOperation("片区下拉菜单列表")
    @PostMapping("/areaList")
    public CmictResponse areaList() throws Exception {
        return CmictResponse.ok(indexService.areaList());
    }

    @ApiOperation("通过区域ID查询摄像头")
    @PostMapping("/deviceListByAreaId")
    public CmictResponse deviceListByAreaId(@RequestParam(value = "areaId",required = false) String areaId) throws Exception {
        return CmictResponse.ok(indexService.deviceListByAreaId(areaId));
    }

    @ApiOperation("地图点设备是否高亮显示")
    @PostMapping("/mapIsBright")
    public CmictResponse mapIsBright(@NotBlank(message = "{required}") @RequestParam(value = "userId") String userId,
                                     @RequestParam(value = "areaId",required = false) String areaId) throws Exception {
        return CmictResponse.ok(indexService.mapIsBright(userId, areaId));
    }

}
