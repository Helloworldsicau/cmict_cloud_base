package com.cmict.server.system.vo;

import lombok.Data;

@Data
public class HornVO {

    private String deviceId;

    private String deviceName;

    //为true代表已被绑定
    private Boolean disabled;
}
