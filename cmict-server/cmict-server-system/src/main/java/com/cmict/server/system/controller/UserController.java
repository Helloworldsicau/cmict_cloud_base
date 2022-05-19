package com.cmict.server.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cmict.annotation.group.usergroup.BatchGroup;
import com.cmict.annotation.group.usergroup.InsertGroup;
import com.cmict.annotation.group.usergroup.SingletonGroup;
import com.cmict.annotation.group.usergroup.UpdateGroup;
import com.cmict.constant.StringConstant;
import com.cmict.entity.CmictResponse;
import com.cmict.entity.QueryRequest;
import com.cmict.entity.system.ProvineUser;
import com.cmict.entity.system.Role;
import com.cmict.entity.system.SystemUser;
import com.cmict.entity.system.UserRole;
import com.cmict.exception.CmictException;
import com.cmict.server.system.dto.UserDTO;
import com.cmict.server.system.service.IRoleService;
import com.cmict.server.system.service.IUserRoleService;
import com.cmict.server.system.service.IUserService;
import com.cmict.untils.CmictUtil;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cmict.exception.ErrorEnum.INVALID_PARAMS;
import static com.cmict.server.system.exception.enums.BusinessEnum.PASSWORD_ERROR;
import static com.cmict.server.system.exception.enums.BusinessEnum.PASSWORD_IS_NULL;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Api(value = "用户",tags = "用户管理")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleService roleService;


    @ApiOperation("修改用户登录事件，登录后调用")
   /* @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "页面大小",required = true,paramType = "int"),
            @ApiImplicitParam(name = "pageNum",value = "第几页",required = true,paramType = "int")
    })*/
    @GetMapping("success")
    public CmictResponse loginSuccess(HttpServletRequest request) {
        String currentUsername = CmictUtil.getCurrentUsername();
        // update last login time
        this.userService.updateLoginTime(currentUsername);
        return CmictResponse.SUCCESS;
        // save login log
    }


    @ApiOperation("分页获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "页面大小",required = true,paramType = "int"),
            @ApiImplicitParam(name = "pageNum",value = "第几页",required = true,paramType = "int")
    })
    @PostMapping("/view")
    @PreAuthorize("hasAuthority('user:view')")
    public CmictResponse userList(@Validated @RequestBody UserDTO userDTO) {
        QueryRequest queryRequest = userDTO.getQueryRequest();
        SystemUser user = new SystemUser();
        BeanUtils.copyProperties(userDTO,user);
        Map<String, Object> dataTable = CmictUtil.getDataTable(userService.findUserDetailList(user, queryRequest));
        return CmictResponse.ok(dataTable);
    }

    @PostMapping("/userDetail")
    public CmictResponse userDetail(String username){
        return CmictResponse.ok(userService.findUserDetail(username));
    }


    @PostMapping("/systemUser")
    public SystemUser systemUser(String username){
        return userService.findUserDetail(username);
    }


    @PostMapping("/userLeaders")
    public CmictResponse userLeaders(long deptId){
        return CmictResponse.ok(userService.findUserLeaders(deptId));
    }

    @ApiOperation("检查用户名是否存在")
    @GetMapping("check/{username}")
    public CmictResponse checkUserName(@NotBlank(message = "{required}") @ApiParam(required = true) @PathVariable String username) {
        return  CmictResponse.ok(this.userService.findByName(username) == null);
    }


    @ApiOperation("管理员增加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",required = true,paramType = "String"),
            @ApiImplicitParam(name = "email",value = "邮箱",required = true,paramType = "String"),
            @ApiImplicitParam(name = "mobile",value = "电话",required = true,paramType = "String"),
            @ApiImplicitParam(name = "status",value = "状态:0 锁定;1 有效",required = true,paramType = "String"),
            @ApiImplicitParam(name = "sex",value = "性别：0 男; 1 女 ; 2 保密",required = true,paramType = "String"),
            @ApiImplicitParam(name = "description",value = "描述",required = true,paramType = "String"),
            @ApiImplicitParam(name = "roleId",value = "角色ID",required = true,paramType = "String"),
    })
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('user:add')")
    public CmictResponse addUser( @RequestBody @ApiIgnore  @Validated(InsertGroup.class) UserDTO userDTO) {
        SystemUser systemUser = new SystemUser();
        BeanUtils.copyProperties(userDTO,systemUser);
        return this.userService.createUser(systemUser);
    }
    @ApiOperation(value = "管理员修改用户",notes = "password,username 无法更改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id",required = true,paramType = "String"),
            @ApiImplicitParam(name = "email",value = "邮箱 非必要",required = false,paramType = "String"),
            @ApiImplicitParam(name = "mobile",value = "电话 非必要",required = false,paramType = "String"),
            @ApiImplicitParam(name = "status",value = "状态:0 锁定;1 有效  非必要",required = false,paramType = "String"),
            @ApiImplicitParam(name = "sex",value = "性别：0 男; 1 女 ; 2 保密  非必要",required = false,paramType = "String"),
            @ApiImplicitParam(name = "description",value = "描述",required = false,paramType = "String"),
            @ApiImplicitParam(name = "roleId",value = "角色ID 必传",required = true,paramType = "String"),
    })
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('user:update')")
    public CmictResponse updateUser( @RequestBody @ApiIgnore @Validated(UpdateGroup.class) UserDTO userDTO) {
        SystemUser systemUser = new SystemUser();
        BeanUtils.copyProperties(userDTO,systemUser);
        return  userService.updateUser(systemUser);
    }

    @ApiOperation("管理员删除用户")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('user:delete')")
    public CmictResponse deleteUsers(@RequestBody @Validated UserDTO userDTO) {
        if(userDTO.getUserIds() == null){
            throw new CmictException(INVALID_PARAMS);
        }
        String userIds = userDTO.getUserIds();
        String[] ids = userIds.split(StringConstant.COMMA);
        return this.userService.deleteUsers(ids);
    }

    /**
     * 修改个人信息
     * @param userDTO
     */
    @ApiOperation("用户修改个人信息")
    @PostMapping("/profile")
    public CmictResponse updateProfile( @RequestBody @Validated(UpdateGroup.class) UserDTO userDTO)  {
        SystemUser user = new SystemUser();
        BeanUtils.copyProperties(userDTO,user);
        return  userService.updateProfile(user);
    }

    @PostMapping("/avatar")
    public CmictResponse updateAvatar(@NotBlank(message = "{required}") @RequestBody String avatar) {
        this.userService.updateAvatar(avatar);
        return CmictResponse.SUCCESS;
    }

    @PostMapping("password/check")
    public CmictResponse checkPassword(@Validated(SingletonGroup.class)  @RequestBody UserDTO userDTO) {
        String password = userDTO.getPassword();
        String currentUsername = CmictUtil.getCurrentUsername();
        SystemUser user = userService.findByName(currentUsername);
        boolean result = user != null && passwordEncoder.matches(password, user.getPassword());
        return CmictResponse.ok(result);
    //    return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    @PostMapping("password")
    public CmictResponse updatePassword(@Validated(SingletonGroup.class) @RequestBody UserDTO userDTO) {
        userService.updatePassword(userDTO);
        return CmictResponse.SUCCESS;
    }

    @PostMapping("password/reset")
    @PreAuthorize("hasAuthority('user:password:reset')")
    public CmictResponse resetPassword(@Validated(BatchGroup.class) @RequestBody UserDTO userDTO) {
        String[] usernameArr = userDTO.getUsernames().split(StringConstant.COMMA);
        String[] userIds = userDTO.getUserIds().split(StringConstant.COMMA);
        this.userService.resetPassword(usernameArr,userIds);
        return CmictResponse.SUCCESS;
    }

    @PostMapping("getProvinceUsers")
    public List<ProvineUser> getProvinceUsers(String users) {
        String[] split = users.split(",");
        List<String>  ids = Arrays.asList(split);
        LambdaQueryWrapper<SystemUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SystemUser::getUserId,ids);
        List<SystemUser> systemUsers = userService.list(lambdaQueryWrapper);
        ArrayList<ProvineUser> provineUsers = new ArrayList<>();
        for (SystemUser systemUser : systemUsers) {
            ProvineUser provineUser = new ProvineUser();
            provineUser.setUserId(systemUser.getUserId());
            provineUser.setUserName(systemUser.getUsername());
            provineUsers.add(provineUser);
        }
        return provineUsers;

    }
    @PostMapping("getLeaders")
    public List<ProvineUser> getLeaders(Long roleId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getRoleId,roleId);
        List<UserRole> userRoles = userRoleService.list(queryWrapper);
        List<Long> userIds = userRoles.stream().map(UserRole::getUserId).collect(Collectors.toList());
        LambdaQueryWrapper<SystemUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SystemUser::getUserId,userIds);
        List<SystemUser> list = userService.list(lambdaQueryWrapper);
        ArrayList<ProvineUser> provineUsers = new ArrayList<>();
        for (SystemUser user : list) {
            ProvineUser provineUser = new ProvineUser();
            provineUser.setUserId(user.getUserId());
            provineUser.setUserName(user.getUsername());
            provineUsers.add(provineUser);
        }
        return provineUsers;
    }
    //只查询一个
    @PostMapping("getOneUserRole")
    public List<String> getOneUserRole(String userId) {
        String[] userIds = {String.valueOf(userId)};
        List<String> roleIdsByUserId = userRoleService.findRoleIdsByUserId(userIds);
        String[] roleId = {String.valueOf(roleIdsByUserId.get(0))};
        List<Role> roles = roleService.getRoleByIds(roleId);
        List<String> collect = roles.stream().map(Role::getRoleName).collect(Collectors.toList());
        return collect;
    }
}
