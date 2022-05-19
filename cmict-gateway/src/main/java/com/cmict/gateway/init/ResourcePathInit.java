package com.cmict.gateway.init;

import com.cmict.entity.system.Menu;
import com.cmict.entity.system.Role;
import com.cmict.gateway.mapper.MenuMapper;
import com.cmict.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @Author: lichenxin
 * @Date: 2021/2/20
 * @desc 初始化 资源和角色对应关系
 */

@Service
public class ResourcePathInit {

    @Autowired
    private RedisService redisService;
    @Autowired
    private MenuMapper menuMapper;

    @PostConstruct
    public void initData() {
        TreeMap resourceRolesMap = new TreeMap<>();
        List<Menu> userRolePath = menuMapper.findUserRolePath();
        for (Menu menu : userRolePath) {
            List<Role> roleList = menu.getRoleList();
            List<Long> roles =new ArrayList<>();

            for (Role role : roleList) {
                roles.add(role.getRoleId());
            }
            if(menu.getPath() !=null){
                resourceRolesMap.put(menu.getPath(),roles);
            }
        }
        redisService.hsetAll("Path_Role",resourceRolesMap);
      /*  resourceRolesMap.put("/urban-demo/user/currentUser", CollUtil.toList("ADMIN", "TEST"));
        resourceRolesMap = new TreeMap<>();
        resourceRolesMap.put("admin-app", CollUtil.toList("urban-admin", "urban-order"));
        redisTemplate.opsForHash().putAll(AuthConstant.RESOURCE_ROLES_MAP_KEY, resourceRolesMap);*/
    }
}
