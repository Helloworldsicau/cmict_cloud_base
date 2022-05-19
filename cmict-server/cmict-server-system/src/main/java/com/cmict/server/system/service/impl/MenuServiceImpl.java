package com.cmict.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmict.constant.PageConstant;
import com.cmict.constant.StringConstant;
import com.cmict.entity.CmictResponse;
import com.cmict.entity.MenuTree;
import com.cmict.entity.Tree;
import com.cmict.entity.router.RouterMeta;
import com.cmict.entity.router.VueRouter;
import com.cmict.entity.system.Menu;
import com.cmict.exception.CmictException;
import com.cmict.server.system.mapper.MenuMapper;
import com.cmict.server.system.service.IMenuService;
import com.cmict.untils.CmictUtil;
import com.cmict.untils.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

import static com.cmict.exception.ErrorEnum.INVALID_PARAMS;
import static com.cmict.server.system.exception.enums.BusinessEnum.*;

/**
 * @Author: lichenxin
 * @Date: 2021/2/22
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Override
    public String findUserPermissions(String username) {
        checkUser(username);
        List<Menu> userPermissions = this.baseMapper.findUserPermissions(username);
        return userPermissions.stream().map(Menu::getPerms).collect(Collectors.joining(StringConstant.COMMA));
    }

    @Override
    public List<Menu> findUserMenus(String username) {
        checkUser(username);
        return this.baseMapper.findUserMenus(username);
    }
    //加一个只要按钮
    @Override
    public Map<String, Object> findMenus() {
        Map<String, Object> result = new HashMap<>(4);
        try {
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.orderByAsc(Menu::getOrderNum);
            List<Menu> menus = baseMapper.selectList(queryWrapper);
            ArrayList<MenuTree> menuTrees = new ArrayList<>();
            buildTrees(menuTrees, menus);
            List<? extends Tree<?>> menuTree = TreeUtil.build(menuTrees);
            result.put(PageConstant.ROWS, menuTree);
            result.put("total", menus.size());
        } catch (NumberFormatException e) {
            log.error("查询菜单失败", e);
            result.put(PageConstant.ROWS, null);
            result.put(PageConstant.TOTAL, 0);
        }
        return result;
    }

    @Override
    public List<VueRouter<Menu>> getUserRouters(String username) {
        checkUser(username);
        List<VueRouter<Menu>> routes = new ArrayList<>();
        List<Menu> menus = this.findUserMenus(username);
        menus.forEach(menu -> {
            VueRouter<Menu> route = new VueRouter<>();
            route.setId(menu.getMenuId().toString());
            route.setParentId(menu.getParentId().toString());
            route.setPath(menu.getPath());
            route.setRedirect(menu.getRedirect());
            route.setComponent(menu.getComponent());
            route.setName(menu.getMenuName());
            route.setMeta(new RouterMeta(menu.getMenuName(), menu.getIcon(), true));
            routes.add(route);
        });
        return TreeUtil.buildVueRouter(routes);
    }

    @Override
    public List<Menu> findMenuList(Menu menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(menu.getMenuName())) {
            queryWrapper.like(Menu::getMenuName, menu.getMenuName());
        }
        queryWrapper.orderByAsc(Menu::getMenuId);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createMenu(Menu menu) {
        //parentId 对应组件ID
        Menu dbMenu = baseMapper.selectById(menu.getParentId());
        if(dbMenu ==null ){
            throw new CmictException(MENU_PARENT_NOT_EXIST);
        }
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getMenuName,menu.getMenuName());
        List<Menu> menus = this.baseMapper.selectList(queryWrapper);
        if(menus !=null && menus.size() > 0){
            throw  new CmictException(MENU_EXIST);
        }
        menu.setCreateTime(new Date());
        setMenu(menu);
        this.save(menu);
        return menu.getMenuId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(Menu menu) {
        String component = menu.getComponent();
        String type = menu.getType();
        String path = menu.getPath();
        if("0".equals(type) ){
            if(StringUtils.isEmpty(component)){
                throw new CmictException(COMPONENT_NULL);
            }
            if(StringUtils.isEmpty(path)){
                throw new CmictException(PATH_NULL);
            }
        }
        menu.setModifyTime(new Date());
        setMenu(menu);
        baseMapper.updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMeuns(String[] menuIds) {
        List<String> list = Arrays.asList(menuIds);
        List<Menu> menus = baseMapper.selectBatchIds(list);
        if(menus == null || (list.size()!= menus.size()) ){
            throw new CmictException(MENU_NOT_EXIST);
        }
        this.delete(Arrays.asList(menuIds));
    }

    private void checkUser(String username) {
        String currentUsername = CmictUtil.getCurrentUsername();
        if (StringUtils.isNotBlank(currentUsername)
                && !StringUtils.equalsIgnoreCase(currentUsername, username)) {
            throw new CmictException("无权获取别的用户数据");
        }
    }
    private void buildTrees(List<MenuTree> trees, List<Menu> menus) {
        menus.forEach(menu -> {
            MenuTree tree = new MenuTree();
            tree.setId(menu.getMenuId().toString());
            tree.setParentId(menu.getParentId().toString());
            tree.setLabel(menu.getMenuName());
            tree.setComponent(menu.getComponent());
            tree.setIcon(menu.getIcon());
            tree.setOrderNum(menu.getOrderNum());
            tree.setPath(menu.getPath());
            tree.setType(menu.getType());
            tree.setPerms(menu.getPerms());
            trees.add(tree);
        });
    }

    private void setMenu(Menu menu) {
        if (menu.getParentId() == null) {
            menu.setParentId(Menu.TOP_MENU_ID);
        }
        if (Menu.TYPE_BUTTON.equals(menu.getType())) {
            menu.setPath(null);
            menu.setIcon(null);
            menu.setComponent(null);
            menu.setOrderNum(null);
        }
    }

    private void delete(List<String> menuIds) {
        removeByIds(menuIds);

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Menu::getParentId, menuIds);
        List<Menu> menus = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(menus)) {
            List<String> menuIdList = new ArrayList<>();
            menus.forEach(m -> menuIdList.add(String.valueOf(m.getMenuId())));
            this.delete(menuIdList);
        }
    }
}
