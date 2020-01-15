package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.constant.ErrCodeConstant;
import com.lvhaifeng.cloud.admin.constant.ResourceTypeEnum;
import com.lvhaifeng.cloud.admin.entity.Menu;
import com.lvhaifeng.cloud.admin.entity.RoleResource;
import com.lvhaifeng.cloud.admin.mapper.MenuMapper;
import com.lvhaifeng.cloud.admin.service.IMenuService;
import com.lvhaifeng.cloud.admin.vo.request.ResourceInfo;
import com.lvhaifeng.cloud.admin.vo.response.MenuInfo;
import com.lvhaifeng.cloud.common.error.ErrCodeBaseConstant;
import com.lvhaifeng.cloud.common.exception.BusinessException;
import com.lvhaifeng.cloud.common.util.EntityUtils;
import com.lvhaifeng.cloud.common.util.SortUtils;
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
import java.io.Serializable;
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
    public boolean createMenu(ResourceInfo resourceInfo) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(resourceInfo, menu);
        EntityUtils.setDefaultValue(menu);
        if (StringUtils.isBlank(resourceInfo.getParentId())) {
            // 如果没有指定父菜单则默认为父菜单(0)
            menu.setParentId(PARENT_ID);
        }
        return super.save(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean alterMenuById(ResourceInfo resourceInfo) {
        Menu menuEntity = baseMapper.selectById(resourceInfo.getId());
        if(menuEntity == null) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        }else {
            BeanUtils.copyProperties(resourceInfo, menuEntity);
            if (StringUtils.isBlank(resourceInfo.getParentId())) {
                // 如果没有指定父菜单则默认为父菜单(0)
                menuEntity.setParentId(PARENT_ID);
            }
        }
        EntityUtils.setDefaultValue(menuEntity);
        return super.updateById(menuEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropMenuById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropMenuBatch(String ids) {
        if(StringUtils.isBlank(ids)) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return super.removeByIds(Arrays.asList(ids.split(",")));
        }
    }

    @Override
    public MenuInfo findMenuById(Serializable id) {
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
            if (roleResources.isEmpty()) {
                throw new BusinessException(ErrCodeConstant.NO_RESOURCE_ERR);
            }
            String roleId = roleResources.get(0).getRoleId();
            menuInfo.setRoleId(roleId);

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
    public List<MenuInfo> findAllMenus() {
        return menuMapper.selectAllMenus();
    }
}