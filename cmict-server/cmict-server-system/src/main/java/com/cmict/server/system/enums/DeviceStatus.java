package com.cmict.server.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceStatus {

    PLAYING(1, "在播"),
    DOWNLOADING(3, "下载"),
    ONLINE(5, "在线"),
    UPGRADING(7, "升级"),
    LOCKED(9, "锁定"),
    BANNED(11, "禁用"),
    OFFLINE(13, "离线"),
    UNKNOWN(999, "未知");

    private final Integer number;
    private final String description;

    public static String getDescByNo(int number){
        for (DeviceStatus status: values()){
            if (status.number == number){
                return status.description;
            }
        }

        return UNKNOWN.description;
    }

}
