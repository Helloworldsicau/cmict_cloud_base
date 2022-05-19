package com.cmict.server.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.cmict.annotation.group.usergroup.InsertGroup;
import com.cmict.annotation.group.usergroup.QueryGroup;
import com.cmict.annotation.group.usergroup.UpdateGroup;
import com.cmict.constant.StringConstant;
import com.cmict.entity.CmictResponse;
import com.cmict.entity.CurrentUser;
import com.cmict.entity.router.VueRouter;
import com.cmict.entity.system.Menu;
import com.cmict.server.system.dto.MenuDTO;
import com.cmict.server.system.service.IMenuService;
import com.cmict.untils.CmictUtil;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpoint;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lichenxin
 * @Date: 2021/2/22
 * 菜单相关操作
 */


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
@Api(value = "菜单",tags = "菜单管理")
public class MenuController {

    @Autowired
    private IMenuService  menuService;

    @ApiOperation("获取用户菜单路由")
    @GetMapping("/user")
    public CmictResponse getUserRouters(){
        CurrentUser currentUser = CmictUtil.getCurrentUser();
        String username = currentUser.getUsername();
        HashMap<String, Object> result = new HashMap<>(4);
        List<VueRouter<Menu>> userRouters = menuService.getUserRouters(username);
        String userPermissions = menuService.findUserPermissions(username);
        String[] permissionArray = new String[0];
        if (StringUtils.isNoneBlank(userPermissions)) {
            permissionArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(userPermissions, StringConstant.COMMA);
        }
        result.put("routes", userRouters);
        result.put("permissions", permissionArray);
        return CmictResponse.ok(result);
    }

    @GetMapping
    @ApiOperation("获取菜单详细信息")
    public CmictResponse menuList() {
        Menu menu = new Menu();
        Map<String, Object> menus = this.menuService.findMenus();
        return CmictResponse.ok(menus);
    }
    @ApiOperation("获取用户权限")
    @GetMapping("/permissions")
    public CmictResponse<String> findUserPermissions() {
        CurrentUser currentUser = CmictUtil.getCurrentUser();
        String username = currentUser.getUsername();
        return CmictResponse.ok(menuService.findUserPermissions(username));
    }

    /**
     * 增加菜单
     * @param menuDTO
     */
    @ApiOperation("增加菜单")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('menu:add')")
    public CmictResponse addMenu(@Validated(InsertGroup.class) @RequestBody MenuDTO menuDTO) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDTO,menu);
        Long menuId = this.menuService.createMenu(menu);
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("menuId",menuId);
        return CmictResponse.ok(jsonObject);
    }

    /**
     * 删除菜单
     * @param menuIds
     */
    @ApiOperation("删除菜单")
    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('menu:delete')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuIds",value = "菜单ID,多个ID用逗号分隔",required = true,paramType = "String"),
    })
    public CmictResponse deleteMenus(@NotBlank(message = "{required}") @RequestParam("menuIds") String menuIds) {
        String[] ids = menuIds.split(StringConstant.COMMA);
        this.menuService.deleteMeuns(ids);
        return CmictResponse.SUCCESS;
    }

    /**
     * 更新
     * @param menuDTO
     */
    @ApiOperation("更新菜单")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('menu:update')")
    public CmictResponse updateMenu(@RequestBody @Validated(UpdateGroup.class) MenuDTO menuDTO) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDTO,menu);
        this.menuService.updateMenu(menu);
        return CmictResponse.SUCCESS;
    }
}
