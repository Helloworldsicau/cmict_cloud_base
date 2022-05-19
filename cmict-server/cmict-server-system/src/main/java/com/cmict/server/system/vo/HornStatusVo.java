package com.cmict.server.system.vo;

import lombok.Data;

@Data
public class HornStatusVo {

    private String status;

    private Integer volume;

    private Integer deviceId;

    private Boolean isAttended;
}
