package com.cmict.server.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {


    ONE("04-01", "无照经营游商"),
    TWO("04-05", "店外经营"),
    THREE("01-36", "乱堆物堆料"),
    FOUR("04-06", "占道经营"),
    FIVE("04-10", "非机动车乱停放"),
    SIX("04-09", "机动车乱停放"),
    SEVEN("03-11", "渣土车未密闭运输"),
    EIGHT("01-07", "沿街晾挂"),
    NINE("02-01", "非法小广告"),
    TEN("01-12", "道路不洁"),
    ELEVEN("01-10", "暴露垃圾"),
    TWELVE("01-98","垃圾满溢"),
    THIRTEEN("01-11", "积存垃圾渣土"),
    FOURTEEN("06-06", "公共设施损坏"),
    FIFTEEN("02-03", "违规户外广告"),
    SIXTEEN("01-01", "私搭乱建");
    private final String number;
    private final String description;

    public static String getDescByNo(String number){
        for (EventType status: values()){
            if (status.getNumber().equals(number) ){
                return status.description;
            }
        }
        return null;
    }

}
