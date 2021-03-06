package com.cmict.server.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cmict.annotation.group.usergroup.BatchGroup;
import com.cmict.annotation.group.usergroup.InsertGroup;
import com.cmict.annotation.group.usergroup.SingletonGroup;
import com.cmict.annotation.group.usergroup.UpdateGroup;
import com.cmict.constant.StringConstant;
import com.cmict.entity.CmictResponse;
import com.cmict.entity.QueryRequest;
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
@Api(value = "??????",tags = "????????????")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleService roleService;


    @ApiOperation("??????????????????????????????????????????")
   /* @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "????????????",required = true,paramType = "int"),
            @ApiImplicitParam(name = "pageNum",value = "?????????",required = true,paramType = "int")
    })*/
    @GetMapping("success")
    public CmictResponse loginSuccess(HttpServletRequest request) {
        String currentUsername = CmictUtil.getCurrentUsername();
        // update last login time
        this.userService.updateLoginTime(currentUsername);
        return CmictResponse.SUCCESS;
        // save login log
    }


    @ApiOperation("????????????????????????")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize",value = "????????????",required = true,paramType = "int"),
            @ApiImplicitParam(name = "pageNum",value = "?????????",required = true,paramType = "int")
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

    @ApiOperation("???????????????????????????")
    @GetMapping("check/{username}")
    public CmictResponse checkUserName(@NotBlank(message = "{required}") @ApiParam(required = true) @PathVariable String username) {
        return  CmictResponse.ok(this.userService.findByName(username) == null);
    }


    @ApiOperation("?????????????????????")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "?????????",required = true,paramType = "String"),
            @ApiImplicitParam(name = "email",value = "??????",required = true,paramType = "String"),
            @ApiImplicitParam(name = "mobile",value = "??????",required = true,paramType = "String"),
            @ApiImplicitParam(name = "status",value = "??????:0 ??????;1 ??????",required = true,paramType = "String"),
            @ApiImplicitParam(name = "sex",value = "?????????0 ???; 1 ??? ; 2 ??????",required = true,paramType = "String"),
            @ApiImplicitParam(name = "description",value = "??????",required = true,paramType = "String"),
            @ApiImplicitParam(name = "roleId",value = "??????ID",required = true,paramType = "String"),
    })
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('user:add')")
    public CmictResponse addUser( @RequestBody @ApiIgnore  @Validated(InsertGroup.class) UserDTO userDTO) {
        SystemUser systemUser = new SystemUser();
        BeanUtils.copyProperties(userDTO,systemUser);
        return this.userService.createUser(systemUser);
    }
    @ApiOperation(value = "?????????????????????",notes = "password,username ????????????")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "??????Id",required = true,paramType = "String"),
            @ApiImplicitParam(name = "email",value = "?????? ?????????",required = false,paramType = "String"),
            @ApiImplicitParam(name = "mobile",value = "?????? ?????????",required = false,paramType = "String"),
            @ApiImplicitParam(name = "status",value = "??????:0 ??????;1 ??????  ?????????",required = false,paramType = "String"),
            @ApiImplicitParam(name = "sex",value = "?????????0 ???; 1 ??? ; 2 ??????  ?????????",required = false,paramType = "String"),
            @ApiImplicitParam(name = "description",value = "??????",required = false,paramType = "String"),
            @ApiImplicitParam(name = "roleId",value = "??????ID ??????",required = true,paramType = "String"),
    })
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('user:update')")
    public CmictResponse updateUser( @RequestBody @ApiIgnore @Validated(UpdateGroup.class) UserDTO userDTO) {
        SystemUser systemUser = new SystemUser();
        BeanUtils.copyProperties(userDTO,systemUser);
        return  userService.updateUser(systemUser);
    }

    @ApiOperation("?????????????????????")
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
     * ??????????????????
     * @param userDTO
     */
    @ApiOperation("????????????????????????")
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

}
