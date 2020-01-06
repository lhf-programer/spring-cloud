package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.constant.ResourceTypeEnum;
import com.lvhaifeng.cloud.admin.entity.Menu;
import com.lvhaifeng.cloud.admin.entity.RoleResource;
import com.lvhaifeng.cloud.admin.mapper.MenuMapper;
import com.lvhaifeng.cloud.admin.service.IMenuService;
import com.lvhaifeng.cloud.admin.vo.request.MenuInfo;
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

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 菜单
 * @Author: haifeng.lv
 * @Date: 2020-01-06 14:22
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    private RoleResourceServiceImpl roleResourceService;

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
    public boolean saveMenu(MenuInfo menuInfo) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuInfo, menu);
        EntityUtils.setDefaultValue(menu);

        if (super.save(menu)) {
            RoleResource roleResource = new RoleResource();
            EntityUtils.setDefaultValue(roleResource);
            roleResource.setDescription(menuInfo.getDescription());
            // 资源类型
            roleResource.setType(ResourceTypeEnum.MENU.getType());
            // 角色 id
            roleResource.setRoleId(menuInfo.getRoleId());
            roleResource.setResourceId(menu.getId());

            return roleResourceService.save(roleResource);
        } else {
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByMenuId(MenuInfo menuInfo) {
        Menu menuEntity = baseMapper.selectById(menuInfo.getId());
        if(menuEntity == null) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        }else {
            BeanUtils.copyProperties(menuInfo, menuEntity);
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
        }else {
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
}
