package com.cmict.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmict.constant.StringConstant;
import com.cmict.entity.CmictConstant;
import com.cmict.entity.CmictResponse;
import com.cmict.entity.CurrentUser;
import com.cmict.entity.QueryRequest;
import com.cmict.entity.system.Role;
import com.cmict.entity.system.SystemUser;
import com.cmict.entity.system.UserRole;
import com.cmict.exception.CmictException;
import com.cmict.exception.CmictExceptionConstant;
import com.cmict.server.system.dto.UserDTO;
import com.cmict.server.system.mapper.UserMapper;
import com.cmict.server.system.properties.RoleProperties;
import com.cmict.server.system.service.IRoleService;
import com.cmict.server.system.service.IUserRoleService;
import com.cmict.server.system.service.IUserService;
import com.cmict.untils.CmictUtil;
import com.cmict.untils.SortUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.cmict.entity.system.SystemUser.*;
import static com.cmict.exception.CmictExceptionConstant.OPERATION_NOT_SUPPORT_CODE;
import static com.cmict.exception.CmictExceptionConstant.OPERATION_NOT_SUPPORT_MESSAGE;
import static com.cmict.exception.ErrorEnum.NONSUPPORT;
import static com.cmict.server.system.exception.enums.BusinessEnum.*;
import static com.cmict.server.system.properties.RoleProperties.ADMIN_ROLE_ID;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, SystemUser> implements IUserService {

    @Autowired
    private IUserRoleService userRoleService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private RoleProperties roleProperties;
    @Override
    public SystemUser findByName(String username) {
        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUser::getUsername, username);
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<SystemUser> findUserDetailList(SystemUser user, QueryRequest request) {
        Page<SystemUser> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "userId", CmictConstant.ORDER_ASC, false);
        return this.baseMapper.findUserDetailPage(page, user);
    }

    @Override
    public SystemUser findUserDetail(String username) {
        SystemUser param = new SystemUser();
        param.setUsername(username);
        List<SystemUser> users = this.baseMapper.findUserDetail(param);
        return CollectionUtils.isNotEmpty(users) ? users.get(0) : null;
    }

    @Override
    public List<SystemUser> findUserLeaders(long deptId) {
        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUser::getDeptId, deptId);
        return this.baseMapper.selectList(queryWrapper);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLoginTime(String username) {
        SystemUser user = new SystemUser();
        user.setLastLoginTime(new Date());

        this.baseMapper.update(user, new LambdaQueryWrapper<SystemUser>().eq(SystemUser::getUsername, username));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CmictResponse createUser(SystemUser user) {
        if (findByName(user.getUsername()) != null) {
            throw new CmictException(USER_NAME_EXIST);
        }
        //保证系统内置角色 只能有一个超级管理员用户
        String[] roles = StringUtils.splitByWholeSeparatorPreserveAllTokens(user.getRoleId(), StringConstant.COMMA);
        for (String role : roles) {
            if(roleProperties.getIdList().contains(role) && ADMIN_ROLE_ID.equals(role)){
             throw new CmictException(ROLE_ERROR);
            }
        }
        // 创建用户
        user.setCreateTime(new Date());
        user.setAvatar(SystemUser.DEFAULT_AVATAR);
        user.setPassword(passwordEncoder.encode(SystemUser.DEFAULT_PASSWORD));
        save(user);
        // 保存用户角色
        List<String> ids = Arrays.asList(roles);
        Collection<Role> dbRoles = roleService.listByIds(ids);
        ArrayList<Role> roleList = new ArrayList<>(dbRoles);
        List<Long> collect = roleList.stream().map(role -> role.getRoleId()).collect(Collectors.toList());
        for (String roleId : roles) {
            if(collect.contains(Long.valueOf(roleId))){
                continue;
            }
            throw new CmictException(ROLE_NOT_EXIST);
        }
        setUserRoles(user, roles);
        // 保存用户数据权限关联关系
       /* String[] deptIds = StringUtils.splitByWholeSeparatorPreserveAllTokens(user.getDeptIds(), StringConstant.COMMA);
        setUserDataPermissions(user, deptIds);*/
        return CmictResponse.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CmictResponse updateUser(SystemUser user) {
        // 更新用户
        user.setPassword(null);
        user.setUsername(null);
        user.setCreateTime(null);
        //管理员不能修改自身权限
        CurrentUser currentUser = CmictUtil.getCurrentUser();
        String[] userIds = {String.valueOf(user.getUserId())};
        List<String> roleIds = userRoleService.findRoleIdsByUserId(userIds);
        for (String roleId : roleIds) {
            if(roleProperties.getIdList().contains(roleId) && ADMIN_ROLE_ID.equals(roleId)){
                throw  new CmictException(NONSUPPORT);
            }
        }

        //roleId 不存在进行报错   不能提升普通用户权限到超级管理员
        String[] roles = StringUtils.splitByWholeSeparatorPreserveAllTokens(user.getRoleId(), StringConstant.COMMA);
        for (String role : roles) {
            if(roleProperties.getIdList().contains(role) && ADMIN_ROLE_ID.equals(role)){
                throw new CmictException(ROLE_ERROR);
            }
        }

        List<Role> roleByIds = roleService.getRoleByIds(roles);
        if(roleByIds.size() != roles.length){
            throw  new CmictException(ROLE_NOT_EXIST);
        }
        user.setModifyTime(new Date());
        updateById(user);


        if(StringUtils.isNotEmpty(user.getRoleId())){
            userRoleService.deleteUserRolesByUserId(userIds);
            setUserRoles(user, roles);
        }
        return CmictResponse.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CmictResponse deleteUsers(String[] userIds) {
        List<String> idList = Arrays.asList(userIds);
        //不能删除系统预设ID
        List<String> userRoles = userRoleService.findRoleIdsByUserId(userIds);
        //获取传的用户的角色ID
        for (String id : userRoles) {
            if(roleProperties.getIdList().contains(id) && ADMIN_ROLE_ID.equals(id)){
                throw  new CmictException(NONSUPPORT);
            }
        }
        //先判断下用户ID存不存在
        Collection<SystemUser> systemUsers = listByIds(idList);
       if(systemUsers ==null ||systemUsers.size() == 0|| systemUsers.size() != idList.size()){
           throw  new CmictException(USER_NOT_EXIST);
       }
       removeByIds(idList);
        // 删除用户角色
        this.userRoleService.deleteUserRolesByUserId(userIds);
        return CmictResponse.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CmictResponse updateProfile(SystemUser user) {
        user.setPassword(null);
        user.setUsername(null);
        user.setStatus(null);
        SystemUser systemUser = this.getById(user.getUserId());
        if(systemUser == null){
            throw new CmictException(USER_NOT_EXIST);
        }
        if (isCurrentUser(user.getUserId())) {
            updateById(user);
        } else {
            throw new CmictException(NO_PERMISSION_UPDATE);
        }
        return CmictResponse.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(String avatar) {
        SystemUser user = new SystemUser();
        user.setAvatar(avatar);
        String currentUsername = CmictUtil.getCurrentUsername();
        this.baseMapper.update(user, new LambdaQueryWrapper<SystemUser>().eq(SystemUser::getUsername, currentUsername));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(UserDTO userDTO) {
        String currentUsername = CmictUtil.getCurrentUsername();
        SystemUser user = this.findByName(currentUsername);
        boolean result = user != null && passwordEncoder.matches(userDTO.getOldPassword(), user.getPassword());
        if(!result){
            throw new CmictException(PASSWORD_ERROR);
        }
        if(StringUtils.isEmpty(userDTO.getOldPassword())){
            throw new CmictException(PASSWORD_IS_NULL);
        }
        String password = userDTO.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        this.baseMapper.update(user, new LambdaQueryWrapper<SystemUser>().eq(SystemUser::getUsername, currentUsername));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(String[] usernames,String [] ids) {
        LambdaQueryWrapper<SystemUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<String> nameList = Arrays.asList(usernames);
        lambdaQueryWrapper.in(SystemUser::getUsername,nameList);
        List<SystemUser> dbUsers = this.list(lambdaQueryWrapper);
        if(CollectionUtils.isEmpty(dbUsers)|| dbUsers.size() !=nameList.size()){

        }
        List<String> userIds = Arrays.asList(ids);
        List<Long> collect = dbUsers.stream().map(SystemUser::getUserId).collect(Collectors.toList());
        //验证传的ID 是否和数据库的ID相符
          for (String userId : userIds) {
            if(collect.contains(Long.valueOf(userId))){
                continue;
               }
             throw  new CmictException(USER_NOT_EXIST);
            }
        SystemUser params = new SystemUser();
        params.setPassword(passwordEncoder.encode(SystemUser.DEFAULT_PASSWORD));
        //让最后登录时间为null,让用户变为首次登录，修改密码 --->CmictUserDetailServiceImpl.loadUserByUsername
        params.setLastLoginTime(null);
        List<String> list = Arrays.asList(usernames);
        this.baseMapper.update(params, new LambdaQueryWrapper<SystemUser>().in(SystemUser::getUsername, list));
    }

    private void setUserRoles(SystemUser user, String[] roles) {
        List<UserRole> userRoles = new ArrayList<>();
        Arrays.stream(roles).forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(Long.valueOf(roleId));
            userRoles.add(userRole);
        });
        userRoleService.saveBatch(userRoles);
    }

/*    private void setUserDataPermissions(SystemUser user, String[] deptIds) {
        List<UserDataPermission> userDataPermissions = new ArrayList<>();
        Arrays.stream(deptIds).forEach(deptId -> {
            UserDataPermission permission = new UserDataPermission();
            permission.setDeptId(Long.valueOf(deptId));
            permission.setUserId(user.getUserId());
            userDataPermissions.add(permission);
        });
        userDataPermissionService.saveBatch(userDataPermissions);
    }*/

    private boolean isCurrentUser(Long id) {
        CurrentUser currentUser = CmictUtil.getCurrentUser();
        return currentUser != null && id.equals(currentUser.getUserId());
    }



    public boolean checkSex(SystemUser systemUser){
        if (StringUtils.isNotEmpty(systemUser.getSex())) {
            String sex = systemUser.getSex();
            List<String> sexs = Arrays.asList(SEX_MALE, SEX_FEMALE, SEX_UNKNOW);
            if (!sexs.contains(sex)) {
                return false;
            }
        }
        return true;
    }

}
