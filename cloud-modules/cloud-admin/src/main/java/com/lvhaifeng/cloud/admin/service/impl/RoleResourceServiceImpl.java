package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.constant.ErrCodeConstant;
import com.lvhaifeng.cloud.admin.constant.ResourceTypeEnum;
import com.lvhaifeng.cloud.admin.entity.Role;
import com.lvhaifeng.cloud.admin.entity.RoleResource;
import com.lvhaifeng.cloud.admin.mapper.RoleResourceMapper;
import com.lvhaifeng.cloud.admin.service.IRoleResourceService;
import com.lvhaifeng.cloud.admin.service.IRoleService;
import com.lvhaifeng.cloud.admin.vo.request.AddRoleResource;
import com.lvhaifeng.cloud.common.error.ErrCodeBaseConstant;
import com.lvhaifeng.cloud.common.exception.BusinessException;
import com.lvhaifeng.cloud.common.util.EntityUtils;
import com.lvhaifeng.mybatis.query.QueryHelper;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @Description: 角色资源
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:26
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {
    @Autowired
    private IRoleService roleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<RoleResource> findRoleResourcePageList(RoleResource roleResource, Integer pageNo, Integer pageSize, String sortProp, String sortType) {
        QueryWrapper<RoleResource> queryWrapper = QueryHelper.initQueryWrapper(roleResource, sortProp, sortType);
        Page<RoleResource> page = new Page<>(pageNo, pageSize);
        IPage<RoleResource> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createRoleResource(RoleResource roleResource) {
        EntityUtils.setDefaultValue(roleResource);
        return super.save(roleResource);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void alterRoleResourceByRoleId(AddRoleResource request) {
        // 角色 id
        String roleId = request.getId();
        Role role = roleService.findRoleById(roleId);
        if (null == role) {
            throw new BusinessException(ErrCodeConstant.NO_ROLE_ERR);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id", roleId);
        baseMapper.delete(queryWrapper);

        // 角色菜单
        List<String> menuIds = request.getMenuIds();
        // 角色按钮
        List<String> buttonIds = request.getButtonIds();
        List<RoleResource> roleResources = new ArrayList<>();

        if (!Collections.isEmpty(menuIds)) {
            menuIds.forEach(menuId -> {
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(menuId);
                roleResource.setType(ResourceTypeEnum.MENU.getType());
                EntityUtils.setDefaultValue(roleResource);
                roleResources.add(roleResource);
            });
            if (!Collections.isEmpty(buttonIds)) {
                buttonIds.forEach(buttonId -> {
                    RoleResource roleResource = new RoleResource();
                    roleResource.setRoleId(roleId);
                    roleResource.setResourceId(buttonId);
                    roleResource.setType(ResourceTypeEnum.BUTTON.getType());
                    EntityUtils.setDefaultValue(roleResource);
                    roleResources.add(roleResource);
                });
            }
        }

        // 批量插入
        baseMapper.insertBatch(roleResources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropRoleResourceById(String id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropRoleResourceBatch(String ids) {
        if(StringUtils.isBlank(ids)) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return super.removeByIds(Arrays.asList(ids.split(",")));
        }
    }

    @Override
    public RoleResource findRoleResourceById(String id) {
        RoleResource roleResource = super.getById(id);
        if (null == roleResource) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return roleResource;
        }
    }

    /**
     * @Description 删除通过资源 id
     * @Author haifeng.lv
     * @param: ids
     * @Date 2020/1/17 15:16
     */
    @Override
    public void removeByResourceId(List<String> ids) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("resource_id", ids);
        remove(queryWrapper);
    }
}
