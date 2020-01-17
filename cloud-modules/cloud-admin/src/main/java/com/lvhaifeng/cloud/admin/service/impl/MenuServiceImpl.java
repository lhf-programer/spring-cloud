package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.constant.ErrCodeConstant;
import com.lvhaifeng.cloud.admin.constant.ResourceTypeEnum;
import com.lvhaifeng.cloud.admin.entity.Button;
import com.lvhaifeng.cloud.admin.entity.Menu;
import com.lvhaifeng.cloud.admin.entity.RoleResource;
import com.lvhaifeng.cloud.admin.mapper.MenuMapper;
import com.lvhaifeng.cloud.admin.service.IButtonService;
import com.lvhaifeng.cloud.admin.service.IMenuService;
import com.lvhaifeng.cloud.admin.vo.response.ButtonInfo;
import com.lvhaifeng.cloud.admin.vo.response.MenuInfo;
import com.lvhaifeng.cloud.common.error.ErrCodeBaseConstant;
import com.lvhaifeng.cloud.common.exception.BusinessException;
import com.lvhaifeng.cloud.common.util.EntityUtils;
import com.lvhaifeng.cloud.common.util.SortUtils;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 菜单
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:30
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    private RoleResourceServiceImpl roleResourceService;
    @Resource
    private MenuMapper menuMapper;
    @Autowired
    private IButtonService buttonService;
    /**
     * 父id
     */
    private static final String PARENT_ID = "0";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<MenuInfo> findMenuPageList(Menu menu, Integer pageNo, Integer pageSize, String sortProp, String sortType) {
        Page<MenuInfo> page = new Page(pageNo, pageSize);
        page.setOrders(SortUtils.resolverSort(sortProp, sortType));
        if (StringUtils.isBlank(menu.getParentId())
                && StringUtils.isBlank(menu.getName())
                && StringUtils.isBlank(menu.getUrl())
                && StringUtils.isBlank(menu.getDescription())) {
            menu.setParentId(PARENT_ID);
        }

        IPage<MenuInfo> pageList = menuMapper.selectMenuPageList(page, menu);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createMenu(Menu menu) {
        EntityUtils.setDefaultValue(menu);
        if (StringUtils.isBlank(menu.getParentId())) {
            // 如果没有指定父菜单则默认为父菜单(0)
            menu.setParentId(PARENT_ID);
        }
        return super.save(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean alterMenuById(Menu menu) {
        Menu menuEntity = baseMapper.selectById(menu.getId());
        if(menuEntity == null) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        }else {
            BeanUtils.copyProperties(menu, menuEntity);
            if (StringUtils.isBlank(menu.getParentId())) {
                // 如果没有指定父菜单则默认为父菜单(0)
                menuEntity.setParentId(PARENT_ID);
            }
        }
        EntityUtils.setDefaultValue(menuEntity);
        return super.updateById(menuEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropMenuById(String id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id", id);
        List<Menu> menus = list(queryWrapper);
        if (!Collections.isEmpty(menus)) {
            throw new BusinessException(ErrCodeConstant.NO_CHILDREN_MENU_PERMIT_ERR);
        }

        // 删除关联资源 id
        roleResourceService.removeByResourceId(Arrays.asList(id));
        // 删除关联按钮
        buttonService.removeByMenuIds(Arrays.asList(id));
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropMenuBatch(String ids) {
        if(StringUtils.isBlank(ids)) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            List<String> menuIds = Arrays.asList(ids.split(","));

            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.in("parent_id", menuIds);
            List<Menu> menus = list(queryWrapper);
            if (!Collections.isEmpty(menus)) {
                throw new BusinessException(ErrCodeConstant.NO_CHILDREN_MENU_PERMIT_ERR);
            }
            // 删除关联资源 id
            roleResourceService.removeByResourceId(Arrays.asList(ids.split(",")));
            // 删除关联按钮
            buttonService.removeByMenuIds(Arrays.asList(ids.split(",")));
            return super.removeByIds(menuIds);
        }
    }

    @Override
    public MenuInfo findMenuById(String id) {
        Menu menu = super.getById(id);
        MenuInfo menuInfo = new MenuInfo();

        if (null == menu) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            BeanUtils.copyProperties(menu, menuInfo);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("resource_id", menu.getId());
            queryWrapper.eq("type", ResourceTypeEnum.MENU.getType());

            List<RoleResource> roleResources = roleResourceService.list(queryWrapper);
            if (!Collections.isEmpty(roleResources)) {
                String roleId = roleResources.get(0).getRoleId();
                menuInfo.setRoleId(roleId);
            }

            return menuInfo;
        }
    }

    /**
     * @Description 根据角色 id查询菜单
     * @Author haifeng.lv
     * @param: id 角色 id
     * @Date 2020/1/6 17:20
     * @return: java.util.List<com.lvhaifeng.cloud.admin.entity.Menu>
     */
    @Override
    public List<MenuInfo> getMenuByRoleId(String id) {
        return menuMapper.selectMenuByRoleId(id, PARENT_ID);
    }

    /**
     * @Description 查询所有菜单
     * @Author haifeng.lv
     * @Date 2020/1/14 14:31
     * @return: java.util.List<com.lvhaifeng.cloud.admin.vo.response.MenuInfo>
     */
    @Override
    public List<MenuInfo> getAllMenusByRoleId(String id) {
        List<Menu> menus = list();
        List<Menu> roleMenus = null;
        List<Button> roleButtons = null;
        List<MenuInfo> menuInfos = new ArrayList<>();

        // 查询角色相关资源
        if (StringUtils.isNotBlank(id)) {
            roleMenus = menuMapper.selectAllMenusByRoleId(id);
        }

        // 菜单
        for (Menu menu:menus) {
            MenuInfo menuInfo = new MenuInfo();
            menuInfo.setId(menu.getId());
            menuInfo.setName(menu.getName());
            if (!Collections.isEmpty(roleMenus)) {
                for (Menu roleMenu:roleMenus) {
                    if (menu.getId().equals(roleMenu.getId())) {
                        menuInfo.setCheck(true);
                    }
                }
            } else {
                menuInfo.setCheck(false);
            }

            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("menu_id", menu.getId());
            List<Button> buttons = buttonService.list(queryWrapper);
            List<ButtonInfo> buttonInfos = new ArrayList<>();

            // 按钮
            for (Button button:buttons) {
                ButtonInfo buttonInfo = new ButtonInfo();
                buttonInfo.setId(button.getId());
                buttonInfo.setName(button.getName());
                if (StringUtils.isNotBlank(id)) {
                    roleButtons = buttonService.findAllButtonsById(id, menu.getId());
                }

                if (!Collections.isEmpty(roleButtons)) {
                    for (Button roleButton:roleButtons) {
                        if (button.getId().equals(roleButton.getId())) {
                            buttonInfo.setCheck(true);
                        }
                    }
                } else {
                    buttonInfo.setCheck(false);
                }
                buttonInfos.add(buttonInfo);
            }
            menuInfo.setButtonList(buttonInfos);
            menuInfos.add(menuInfo);
        }

        return menuInfos;
    }

    /**
     * @Description 查询所有菜单
     * @Author haifeng.lv
     * @Date 2020/1/16 13:59
     * @return: java.util.List<com.lvhaifeng.cloud.admin.vo.response.MenuInfo>
     */
    @Override
    public List<MenuInfo> findAllMenus() {
        return menuMapper.selectAllMenus();
    }

}
