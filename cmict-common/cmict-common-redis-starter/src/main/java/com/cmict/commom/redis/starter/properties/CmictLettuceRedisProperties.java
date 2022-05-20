package com.cmict.commom.redis.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wmh
 */
@ConfigurationProperties(prefix = "cmict.lettuce.redis")
public class CmictLettuceRedisProperties {

    /**
     * 是否开启Lettuce Redis
     */
    private Boolean enable = true;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "FebsLettuceRedisProperties{" +
                "enable=" + enable +
                '}';
    }
}
