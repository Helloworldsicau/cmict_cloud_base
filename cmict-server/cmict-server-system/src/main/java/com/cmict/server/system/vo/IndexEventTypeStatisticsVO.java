package com.cmict.server.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "首页事件类型统计返回数据")
public class IndexEventTypeStatisticsVO {

    @ApiModelProperty("累计事件")
    private int totalEvent;

    @ApiModelProperty("今日事件")
    private int todayEvent;

    @ApiModelProperty("累计喊话")
    private int totalShouting;

    @ApiModelProperty("今日喊话")
    private int todayShouting;

    @ApiModelProperty("累计无照经营游商")
    private int totalBusinessWithoutLicense;

    @ApiModelProperty("今日无照经营游商")
    private int todayBusinessWithoutLicense;

    @ApiModelProperty("累计店外经营")
    private int totalOutOfStoreOperation;

    @ApiModelProperty("今日店外经营")
    private int todayOutOfStoreOperation;

    @ApiModelProperty("累计占道经营")
    private int totalRoadsideBooths;

    @ApiModelProperty("今日占道经营")
    private int todayRoadsideBooths;

    @ApiModelProperty("累计乱堆物堆料")
    private int totalLittering;

    @ApiModelProperty("今日乱堆物堆料")
    private int todayLittering;

    @ApiModelProperty("累计非机动车乱停放")
    private int totalRandomParkingOfNonMotorVehicles;

    @ApiModelProperty("今日非机动车乱停放")
    private int todayRandomParkingOfNonMotorVehicles;

    @ApiModelProperty("累计机动车乱停放")
    private int totalRandomParkingOfMotorVehicles;

    @ApiModelProperty("今日机动车乱停放")
    private int todayRandomParkingOfMotorVehicles;

    @ApiModelProperty("累计渣土车未密闭运输")
    private int totalResidueTruckIsNotTransportedInClosedCondition;

    @ApiModelProperty("今日渣土车未密闭运输")
    private int todayResidueTruckIsNotTransportedInClosedCondition;

    @ApiModelProperty("累计沿街晾挂")
    private int totalHangThingAlongStreet;

    @ApiModelProperty("今日沿街晾挂")
    private int todayHangThingAlongStreet;

    @ApiModelProperty("累计非法小广告")
    private int totalIllegalAdvertisement;

    @ApiModelProperty("今日非法小广告")
    private int todayIllegalAdvertisement;

    @ApiModelProperty("累计道路不洁")
    private int totalRoadIsNotClean;

    @ApiModelProperty("今日道路不洁")
    private int todayRoadIsNotClean;

    @ApiModelProperty("累计垃圾满溢")
    private int totalGarbageOverflowed;

    @ApiModelProperty("今日垃圾满溢")
    private int todayGarbageOverflowed;

    @ApiModelProperty("累计暴露垃圾")
    private int totalExposedGarbage;

    @ApiModelProperty("今日暴露垃圾")
    private int todayExposedGarbage;

    @ApiModelProperty("累计积存垃圾渣土")
    private int totalAccumulationOfWasteResidue;

    @ApiModelProperty("今日积存垃圾渣土")
    private int todayAccumulationOfWasteResidue;

    @ApiModelProperty("累计公共设施损坏")
    private int totalDamageToPublicFacilities;

    @ApiModelProperty("今日公共设施损坏")
    private int todayDamageToPublicFacilities;

    @ApiModelProperty("累计违规户外广告")
    private int totalIllegalOutdoorAdvertising;

    @ApiModelProperty("今日违规户外广告")
    private int todayIllegalOutdoorAdvertising;

    @ApiModelProperty("累计私搭乱建")
    private int totalBuildingWithoutPermission;

    @ApiModelProperty("今日私搭乱建")
    private int todayBuildingWithoutPermission;
}
