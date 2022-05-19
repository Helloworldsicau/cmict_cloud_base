package com.cmict.entity.system;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: lichenxin
 * @Date: 2021/3/18
 */
@Data
public class Location {

    /**
     * 经度
     */
   private BigDecimal lon;
    /**
     * 纬度
     */
    private  BigDecimal lat;
}
