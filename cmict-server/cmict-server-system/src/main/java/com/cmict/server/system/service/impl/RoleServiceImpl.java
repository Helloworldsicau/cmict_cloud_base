package com.cmict.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmict.constant.StringConstant;
import com.cmict.entity.CmictConstant;
import com.cmict.entity.CmictResponse;
import com.cmict.entity.QueryRequest;
import com.cmict.entity.system.Menu;
import com.cmict.entity.system.Role;
import com.cmict.entity.system.RoleMenu;
import com.cmict.entity.system.UserRole;
import com.cmict.exception.CmictException;
import com.cmict.exception.CmictExceptionConstant;
import com.cmict.server.system.mapper.IRoleMenuService;
import com.cmict.server.system.mapper.MenuMapper;
import com.cmict.server.system.mapper.RoleMapper;
import com.cmict.server.system.properties.RoleProperties;
import com.cmict.server.system.service.IRoleService;
import com.cmict.server.system.service.IUserRoleService;
import com.cmict.untils.CmictUtil;
import com.cmict.untils.SortUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.cmict.entity.system.SystemUser.CHECK_NAME;
import static com.cmict.exception.CmictExceptionConstant.*;
import static com.cmict.exception.ErrorEnum.NONSUPPORT;
import static com.cmict.server.system.exception.enums.BusinessEnum.*;

/**
 * @Author: lichenxin
 * @Date: 2021/2/24
 */
@Slf4j
@Service("roleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private  IRoleMenuService roleMenuService;
    @Autowired
    private  IUserRoleService userRoleService;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleProperties roleProperties;
    @Override
    public IPage<Role> findRoles(Role role, QueryRequest request) {
        Page<Role> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", CmictConstant.ORDER_DESC, false);
        return this.baseMapper.findRolePage(page, role);
    }

    @Override
    public List<Role> findUserRole(String userName) {
        return baseMapper.findUserRole(userName);
    }

    @Override
    public List<Role> findAllRoles() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Role::getRoleId);
        return this.baseMapper.selectList(queryWrapper);
    }
    @Override
    public Role findByName(String roleName) {
        return baseMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getRoleName, roleName));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CmictResponse createRole(Role role) {
        //检查角色名是否重复
        checkRoleName(role);
        role.setCreateTime(new Date());
        this.save(role);
        //检查菜单ID 是否存在合法
        if (StringUtils.isNotBlank(role.getMenuIds())) {
            String[] menuIds = StringUtils.splitByWholeSeparatorPreserveAllTokens(role.getMenuIds(), StringConstant.COMMA);
            List<String> ids = Arrays.asList(menuIds);
            List<Menu> menus = menuMapper.selectBatchIds(ids);
            if(menuIds.length != menus.size()){
            //    throw  new CmictException(MENU_NOT_EXIST_CODE,MENU_NOT_EXIST_MESSAGE);
            }
            setRoleMenus(role, menuIds);
        }
        return CmictResponse.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoles(String[] roleIds) {
        List<String> list = Arrays.asList(roleIds);
        for (String s : list) {
            if(roleProperties.getIdList().contains(s)){
                throw  new CmictException(NONSUPPORT);
            }
        }
        List<Role> roles = baseMapper.selectBatchIds(list);
        if(roles ==null || (roles.size() != list.size())){
            throw new CmictException(ROLE_NOT_EXIST);
        }

        baseMapper.deleteBatchIds(list);
        this.roleMenuService.deleteRoleMenusByRoleId(roleIds);
        this.userRoleService.deleteUserRolesByRoleId(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CmictResponse updateRole(Role role) {
        if(roleProperties.getIdList().contains(String.valueOf(role.getRoleId()))){
            throw  new CmictException(NONSUPPORT);
        }
        if(role.getRoleId() == null || baseMapper.selectById(role.getRoleId()) ==null){
            throw  new CmictException(ROLE_NOT_EXIST);
        }
        if(StringUtils.isEmpty(role.getMenuIds()) ){
            throw new CmictException(MENU_NOT_EXIST);
        }
        role.setRoleName(null);
        role.setModifyTime(new Date());
        //检查更新的菜单是否存在
        String[] menuIds = StringUtils.splitByWholeSeparatorPreserveAllTokens(role.getMenuIds(), StringConstant.COMMA);
        List<Menu> menus = menuMapper.selectBatchIds(Arrays.asList(menuIds));
        List<Long> dbIds = menus.stream().map(menu -> menu.getMenuId()).collect(Collectors.toList());
        for (String menuId : menuIds) {
            if(dbIds.contains(Long.valueOf(menuId))){
                continue;
            }
            throw new CmictException(MENU_NOT_EXIST);
        }
        baseMapper.updateById(role);
        if (StringUtils.isNotBlank(role.getMenuIds())) {
            roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, role.getRoleId()));
            menuIds = StringUtils.splitByWholeSeparatorPreserveAllTokens(role.getMenuIds(), StringConstant.COMMA);
            setRoleMenus(role, menuIds);
        }
        return CmictResponse.SUCCESS;
    }

    @Override
    public List<Role> getRoleByIds(String[] roles) {
        List<String> ids = Arrays.asList(roles);
        return baseMapper.selectBatchIds(ids);
    }

    private void setRoleMenus(Role role, String[] menuIds) {
        List<RoleMenu> roleMenus = new ArrayList<>();
        Arrays.stream(menuIds).forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            if (StringUtils.isNotBlank(menuId)) {
                roleMenu.setMenuId(Long.valueOf(menuId));
            }
            roleMenu.setRoleId(role.getRoleId());
            roleMenus.add(roleMenu);
        });
        this.roleMenuService.saveBatch(roleMenus);
    }

    public void checkRoleName(Role role){
        Pattern pattern = Pattern.compile(CHECK_NAME);
        if(findByName(role.getRoleName()) !=null){
            throw new CmictException(USER_NAME_EXIST);
        }
    }

    @Override
    public String getOneRoleName() {
        Long userId = CmictUtil.getCurrentUser().getUserId();
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        UserRole one = userRoleService.getOne(wrapper);
        Role role = getById(one.getRoleId());
        return role.getRoleName();
    }
}
