package com.cmict.activiti.feign;


import com.cmict.activiti.feign.fallback.UserClientFallBack;
import com.cmict.entity.CmictResponse;
import com.cmict.entity.system.ProvineUser;
import com.cmict.entity.system.SystemUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.cmict.entity.CmictServerConstant.CMICT_SERVER_SYSTEM;


/**
 * @Author: lichenxin
 * @Date: 2021/8/4
 * 调用system/user下的接口
 */
@FeignClient(value = CMICT_SERVER_SYSTEM, contextId = "userClient", fallbackFactory = UserClientFallBack.class/*, configuration = FeignConfiguration.class*/)
public interface UserClient {

    String JSON = "application/json";


    @PostMapping(value = "/user/userDetail", consumes = JSON)
    public CmictResponse getUserDetail(@RequestParam("username") String username);

    @PostMapping(value = "/user/systemUser", consumes = JSON)
    public SystemUser getSystemUser(@RequestParam("username") String username);

    @PostMapping(value = "/user/userLeaders", consumes = JSON)
    public CmictResponse getUserLeaders(@RequestParam("deptId") long deptId);

    @PostMapping(value = "/user/getLeaders", consumes = JSON)
    public List<ProvineUser> getUserByRoleId(@RequestParam("roleId") long roleId);

    @PostMapping(value = "/user/getProvinceUsers", consumes = JSON)
    public List<ProvineUser> getProvinceUser(@RequestParam("users") String users);
    @PostMapping(value = "/user/getOneUserRole", consumes = JSON)
    public List<String> getUserRole(@RequestParam("userId") String  userId);
}
