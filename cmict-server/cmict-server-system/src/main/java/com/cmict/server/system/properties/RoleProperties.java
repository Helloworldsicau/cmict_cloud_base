package com.cmict.server.system.properties;

import com.cmict.constant.StringConstant;
import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: lichenxin
 * @Date: 2021/6/18
 */

@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:role_user.properties"})
@ConfigurationProperties(prefix = "cmict.role")
public class RoleProperties {
     private String ids;
    //超级管理员的角色ID
     public static final String ADMIN_ROLE_ID = "1";
  public List<String>  getIdList(){
       String[] split = this.ids.split(StringConstant.COMMA);
       List<String> list = Arrays.asList(split);
       return new ArrayList<String>(list);
  }


}
