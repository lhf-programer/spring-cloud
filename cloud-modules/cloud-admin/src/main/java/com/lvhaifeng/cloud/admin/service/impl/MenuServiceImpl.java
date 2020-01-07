package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.constant.ResourceTypeEnum;
import com.lvhaifeng.cloud.admin.entity.Menu;
import com.lvhaifeng.cloud.admin.entity.RoleResource;
import com.lvhaifeng.cloud.admin.mapper.MenuMapper;
import com.lvhaifeng.cloud.admin.service.IMenuService;
import com.lvhaifeng.cloud.admin.vo.request.ResourceInfo;
import com.lvhaifeng.cloud.common.error.ErrCodeBaseConstant;
import com.lvhaifeng.cloud.common.exception.BusinessException;
import com.lvhaifeng.cloud.common.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import shaded.org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 菜单
 * @Author: haifeng.lv
 * @Date: 2020-01-06 14:22
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    private RoleResourceServiceImpl roleResourceService;
    @Resource
    private MenuMapper menuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<Menu> pageMenuList(Menu menu, Integer pageNo, Integer pageSize) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        Page<Menu> page = new Page<>(pageNo, pageSize);
        IPage<Menu> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveMenu(ResourceInfo resourceInfo) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(resourceInfo, menu);
        EntityUtils.setDefaultValue(menu);
        if (StringUtils.isBlank(resourceInfo.getParent())) {
            // 如果没有指定父菜单则默认为父菜单(0)
            menu.setParent("0");
        }

        if (super.save(menu)) {
            RoleResource roleResource = new RoleResource();
            EntityUtils.setDefaultValue(roleResource);
            roleResource.setDescription(resourceInfo.getDescription());
            // 资源类型
            roleResource.setType(ResourceTypeEnum.MENU.getType());
            // 角色 id
            roleResource.setRoleId(resourceInfo.getRoleId());
            roleResource.setResourceId(menu.getId());

            return roleResourceService.save(roleResource);
        } else {
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByMenuId(ResourceInfo resourceInfo) {
        Menu menuEntity = baseMapper.selectById(resourceInfo.getId());
        if(menuEntity == null) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        }else {
            BeanUtils.copyProperties(resourceInfo, menuEntity);
            if (StringUtils.isBlank(resourceInfo.getParent())) {
                // 如果没有指定父菜单则默认为父菜单(0)
                menuEntity.setParent("0");
            }
        }
        EntityUtils.setDefaultValue(menuEntity);
        return super.updateById(menuEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByMenuId(Serializable id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("resource_id", id);
        roleResourceService.remove(queryWrapper);
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByMenuIds(Collection<? extends Serializable> ids) {
        if(ids.isEmpty()) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.in("resource_id", ids);
            roleResourceService.remove(queryWrapper);
            return super.removeByIds(ids);
        }
    }

    @Override
    public Menu getByMenuId(Serializable id) {
        Menu menu = super.getById(id);
        if (null == menu) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return menu;
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
    public List<Menu> getMenuByRoleId(String id) {
        return menuMapper.selectMenuByRoleId(id, "0");
    }
}
