package com.cmict.server.system.controller;

import com.cmict.annotation.group.usergroup.BatchGroup;
import com.cmict.annotation.group.usergroup.InsertGroup;
import com.cmict.annotation.group.usergroup.QueryGroup;
import com.cmict.annotation.group.usergroup.UpdateGroup;
import com.cmict.constant.StringConstant;
import com.cmict.entity.CmictResponse;
import com.cmict.entity.QueryRequest;
import com.cmict.entity.system.Role;
import com.cmict.server.system.dto.RoleDTO;
import com.cmict.server.system.service.IRoleService;
import com.cmict.untils.CmictUtil;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @Author: lichenxin
 * @Date: 2021/2/24
 */

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Api(value = "角色",tags = "角色操作")
@RequestMapping("role")
public class RoleController {
    @Autowired
    private  IRoleService roleService;



    @ApiOperation("获取角色数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "页面大小",required = true,paramType = "int"),
            @ApiImplicitParam(name = "pageNum",value = "第几页",required = true,paramType = "int")
    })
    @PostMapping("view")
    public CmictResponse roleList( @RequestBody @Validated(QueryGroup.class) RoleDTO roleDTO) {
         QueryRequest queryRequest = roleDTO.getQueryRequest();
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO,role);
        Map<String, Object> dataTable = CmictUtil.getDataTable(roleService.findRoles(role, queryRequest));
        return CmictResponse.ok(dataTable);
    }
    @ApiOperation("获取所有角色")
    @GetMapping("options")
    public CmictResponse roles() {
        List<Role> allRoles = roleService.findAllRoles();
        return CmictResponse.ok(allRoles);
    }
    @ApiOperation("检查角色名是否重复")
    @GetMapping("check/{roleName}")
    public CmictResponse checkRoleName(@NotBlank(message = "{required}") @PathVariable @ApiParam(required = true) String roleName) {
        Role result = this.roleService.findByName(roleName);
        return CmictResponse.ok(result==null);
    }
    @ApiOperation("增加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName",value = "角色名",required = true,paramType = "String"),
            @ApiImplicitParam(name = "remark",value = "描述 ",required = true,paramType = "String"),
            @ApiImplicitParam(name = "menuIds",value = "角色可以访问的菜单ID,多个ID用逗号分割 ",required = true,paramType = "String")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('role:add')")
    public CmictResponse addRole(@Validated(InsertGroup.class) @RequestBody RoleDTO roleDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO,role);
        roleService.createRole(role);
        return CmictResponse.SUCCESS;
    }

    @PostMapping("/delete")
    @ApiOperation("删除角色")
    @PreAuthorize("hasAuthority('role:delete')")
    public CmictResponse deleteRoles(@RequestBody @Validated(BatchGroup.class) RoleDTO roleDTO) {
        String roleIds = roleDTO.getRoleIds();
        String[] ids = roleIds.split(StringConstant.COMMA);
        this.roleService.deleteRoles(ids);
        return CmictResponse.SUCCESS;
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新角色",notes = "根据ID更改，角色名无法更改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId",value = "角色ID",required = true,paramType = "String"),
            @ApiImplicitParam(name = "remark",value = "描述 ",required = false,paramType = "String"),
            @ApiImplicitParam(name = "menuIds",value = "角色可以访问的菜单ID,多个ID用逗号分割 ",required = false,paramType = "String")
    })
    @PreAuthorize("hasAuthority('role:update')")
    public CmictResponse updateRole(@Validated(UpdateGroup.class) @RequestBody RoleDTO roleDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO,role);
        return  this.roleService.updateRole(role);
    }

    @GetMapping("/userRole")
    public CmictResponse userRole(){
        return CmictResponse.ok(roleService.getOneRoleName());
    }
}
